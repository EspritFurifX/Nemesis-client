package com.minecraft.launcher.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Gestionnaire de téléchargement des fichiers Minecraft.
 * 
 * Cette classe est responsable du téléchargement de tous les fichiers
 * nécessaires pour lancer une version de Minecraft :
 * - Le JAR client principal
 * - Les bibliothèques (libraries)
 * - Les assets (sons, textures, etc.)
 * 
 * Tous les téléchargements se font depuis les serveurs officiels Mojang.
 */
public class DownloadManager {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadManager.class);
    
    private final Path minecraftDir;
    private final Path versionsDir;
    private final Path librariesDir;
    private final Path assetsDir;
    private final Path nativesDir;
    
    /**
     * Constructeur.
     * 
     * @param minecraftDir Chemin du dossier .minecraft personnalisé
     */
    public DownloadManager(Path minecraftDir) {
        this.minecraftDir = minecraftDir;
        this.versionsDir = minecraftDir.resolve("versions");
        this.librariesDir = minecraftDir.resolve("libraries");
        this.assetsDir = minecraftDir.resolve("assets");
        this.nativesDir = minecraftDir.resolve("natives");
        
        // Création des dossiers s'ils n'existent pas
        createDirectories();
    }
    
    /**
     * Crée la structure de dossiers nécessaire.
     */
    private void createDirectories() {
        try {
            Files.createDirectories(versionsDir);
            Files.createDirectories(librariesDir);
            Files.createDirectories(assetsDir);
            Files.createDirectories(nativesDir);
            LOGGER.info("Structure de dossiers créée dans : {}", minecraftDir);
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la création des dossiers", e);
        }
    }
    
    /**
     * Télécharge le JSON de version détaillé d'une version Minecraft.
     * 
     * @param version La version à télécharger
     * @return Le contenu JSON parsé, ou null en cas d'erreur
     */
    public JsonObject downloadVersionJson(MinecraftVersion version) {
        LOGGER.info("Téléchargement du JSON de la version {}...", version.getId());
        
        try {
            String jsonContent = downloadString(version.getUrl());
            if (jsonContent == null) {
                return null;
            }
            
            // Sauvegarde du JSON dans versions/<id>/<id>.json
            Path versionDir = versionsDir.resolve(version.getId());
            Files.createDirectories(versionDir);
            
            Path jsonFile = versionDir.resolve(version.getId() + ".json");
            Files.writeString(jsonFile, jsonContent);
            
            LOGGER.info("JSON sauvegardé : {}", jsonFile);
            return JsonParser.parseString(jsonContent).getAsJsonObject();
            
        } catch (IOException e) {
            LOGGER.error("Erreur lors du téléchargement du JSON", e);
            return null;
        }
    }
    
    /**
     * Télécharge le JAR client d'une version Minecraft.
     * 
     * @param version La version à télécharger
     * @param versionJson Le JSON détaillé de la version
     * @return true si le téléchargement a réussi
     */
    public boolean downloadClientJar(MinecraftVersion version, JsonObject versionJson) {
        LOGGER.info("Téléchargement du client JAR de {}...", version.getId());
        
        try {
            // Récupération de l'URL du client depuis le JSON
            JsonObject downloads = versionJson.getAsJsonObject("downloads");
            JsonObject client = downloads.getAsJsonObject("client");
            String url = client.get("url").getAsString();
            int size = client.get("size").getAsInt();
            
            // Chemin de destination
            Path versionDir = versionsDir.resolve(version.getId());
            Path jarFile = versionDir.resolve(version.getId() + ".jar");
            
            // Vérification si le fichier existe déjà
            if (Files.exists(jarFile) && Files.size(jarFile) == size) {
                LOGGER.info("Le JAR existe déjà et est valide");
                return true;
            }
            
            // Téléchargement
            LOGGER.info("Téléchargement depuis : {}", url);
            LOGGER.info("Taille : {} octets", size);
            
            boolean success = downloadFile(url, jarFile);
            
            if (success) {
                LOGGER.info("JAR téléchargé : {}", jarFile);
            }
            
            return success;
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du téléchargement du JAR", e);
            return false;
        }
    }
    
    /**
     * Télécharge un fichier depuis une URL.
     * 
     * @param urlString URL du fichier
     * @param destination Chemin de destination
     * @return true si le téléchargement a réussi
     */
    private boolean downloadFile(String urlString, Path destination) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(30000);
            
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                LOGGER.error("Erreur HTTP {} pour {}", responseCode, urlString);
                return false;
            }
            
            // Création du dossier parent si nécessaire
            Files.createDirectories(destination.getParent());
            
            // Téléchargement avec suivi de la progression
            try (InputStream in = connection.getInputStream()) {
                long totalSize = connection.getContentLengthLong();
                long downloaded = 0;
                
                try (FileOutputStream out = new FileOutputStream(destination.toFile())) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        downloaded += bytesRead;
                        
                        // Affichage de la progression tous les 5%
                        if (totalSize > 0) {
                            int progress = (int) ((downloaded * 100) / totalSize);
                            if (progress % 5 == 0) {
                                LOGGER.info("Progression : {}%", progress);
                            }
                        }
                    }
                }
            }
            
            return true;
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du téléchargement de {}", urlString, e);
            return false;
        }
    }
    
    /**
     * Télécharge une URL en tant que String.
     */
    private String downloadString(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            if (connection.getResponseCode() != 200) {
                return null;
            }
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            return response.toString();
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du téléchargement de {}", urlString, e);
            return null;
        }
    }
    
    /**
     * Télécharge toutes les libraries nécessaires pour une version.
     * 
     * Les libraries sont des dépendances Java (JARs) dont Minecraft a besoin.
     * Elles incluent : LWJGL, Gson, Apache Commons, etc.
     * 
     * @param versionJson Le JSON détaillé de la version
     * @return Liste des chemins des libraries téléchargées (pour le classpath)
     */
    public List<Path> downloadLibraries(JsonObject versionJson) {
        LOGGER.info("Téléchargement des libraries...");
        List<Path> libraryPaths = new ArrayList<>();
        
        if (!versionJson.has("libraries")) {
            LOGGER.warn("Aucune library trouvée dans le JSON");
            return libraryPaths;
        }
        
        JsonArray libraries = versionJson.getAsJsonArray("libraries");
        int total = libraries.size();
        int current = 0;
        
        for (JsonElement element : libraries) {
            current++;
            JsonObject library = element.getAsJsonObject();
            
            // 1. Vérifier les règles (OS compatibility)
            if (!shouldDownloadLibrary(library)) {
                LOGGER.debug("Library ignorée (règles OS) : {}", library.get("name").getAsString());
                continue;
            }
            
            // 2. Télécharger l'artifact principal
            if (library.has("downloads")) {
                JsonObject downloads = library.getAsJsonObject("downloads");
                
                // 2.1 Artifact standard
                if (downloads.has("artifact")) {
                    Path jarPath = downloadLibraryArtifact(
                        downloads.getAsJsonObject("artifact")
                    );
                    if (jarPath != null) {
                        libraryPaths.add(jarPath);
                    }
                }
                
                // 2.2 Natives (bibliothèques OS-spécifiques)
                if (downloads.has("classifiers")) {
                    Path nativePath = downloadNativeLibrary(
                        library,
                        downloads.getAsJsonObject("classifiers")
                    );
                    if (nativePath != null) {
                        // Les natives sont extraites, pas ajoutées au classpath
                        LOGGER.debug("Native extraite : {}", nativePath);
                    }
                }
            }
            
            LOGGER.info("Progression libraries : {}/{}", current, total);
        }
        
        LOGGER.info("Téléchargement terminé : {} libraries", libraryPaths.size());
        return libraryPaths;
    }
    
    /**
     * Vérifie si une library doit être téléchargée selon les règles OS.
     * 
     * Exemple de règle : certaines libraries sont uniquement pour Windows.
     */
    private boolean shouldDownloadLibrary(JsonObject library) {
        if (!library.has("rules")) {
            return true; // Pas de règles = compatible partout
        }
        
        JsonArray rules = library.getAsJsonArray("rules");
        boolean allowed = false;
        
        String osName = System.getProperty("os.name").toLowerCase();
        String osType = getOSType(osName);
        
        for (JsonElement element : rules) {
            JsonObject rule = element.getAsJsonObject();
            String action = rule.get("action").getAsString();
            
            // Vérifier si la règle s'applique à notre OS
            if (rule.has("os")) {
                JsonObject os = rule.getAsJsonObject("os");
                if (os.has("name")) {
                    String ruleName = os.get("name").getAsString();
                    if (ruleName.equals(osType)) {
                        allowed = action.equals("allow");
                    }
                }
            } else {
                // Pas de restriction OS
                allowed = action.equals("allow");
            }
        }
        
        return allowed;
    }
    
    /**
     * Détermine le type d'OS pour Minecraft (windows, osx, linux).
     */
    private String getOSType(String osName) {
        if (osName.contains("win")) return "windows";
        if (osName.contains("mac") || osName.contains("osx")) return "osx";
        return "linux";
    }
    
    /**
     * Télécharge un artifact de library (JAR standard).
     */
    private Path downloadLibraryArtifact(JsonObject artifact) {
        try {
            String path = artifact.get("path").getAsString();
            String url = artifact.get("url").getAsString();
            int size = artifact.get("size").getAsInt();
            
            Path destination = librariesDir.resolve(path);
            
            // Vérifier si déjà téléchargé
            if (Files.exists(destination) && Files.size(destination) == size) {
                LOGGER.debug("Library déjà présente : {}", path);
                return destination;
            }
            
            // Téléchargement
            LOGGER.info("Téléchargement : {}", path);
            if (downloadFile(url, destination)) {
                return destination;
            }
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du téléchargement d'une library", e);
        }
        
        return null;
    }
    
    /**
     * Télécharge et extrait une library native (OS-specific).
     * 
     * Les natives contiennent des bibliothèques système (.dll, .so, .dylib)
     * nécessaires pour OpenGL, OpenAL, etc.
     */
    private Path downloadNativeLibrary(JsonObject library, JsonObject classifiers) {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String osType = getOSType(osName);
            
            // Déterminer le classifier natif pour cet OS
            String nativeClassifier = null;
            if (library.has("natives")) {
                JsonObject natives = library.getAsJsonObject("natives");
                if (natives.has(osType)) {
                    nativeClassifier = natives.get(osType).getAsString();
                    // Remplacer ${arch} par l'architecture système
                    nativeClassifier = nativeClassifier.replace(
                        "${arch}",
                        System.getProperty("os.arch").contains("64") ? "64" : "32"
                    );
                }
            }
            
            if (nativeClassifier == null || !classifiers.has(nativeClassifier)) {
                return null; // Pas de native pour cet OS
            }
            
            JsonObject nativeArtifact = classifiers.getAsJsonObject(nativeClassifier);
            String path = nativeArtifact.get("path").getAsString();
            String url = nativeArtifact.get("url").getAsString();
            
            Path zipPath = librariesDir.resolve(path);
            
            // Télécharger le ZIP des natives
            LOGGER.info("Téléchargement native : {}", path);
            if (!downloadFile(url, zipPath)) {
                return null;
            }
            
            // Extraire les natives
            extractNatives(zipPath, nativesDir);
            
            return nativesDir;
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du téléchargement des natives", e);
            return null;
        }
    }
    
    /**
     * Extrait les fichiers natifs d'un ZIP.
     */
    private void extractNatives(Path zipPath, Path destination) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath.toFile()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                // Ignorer les META-INF
                if (entry.getName().startsWith("META-INF")) {
                    continue;
                }
                
                Path outputPath = destination.resolve(entry.getName());
                
                if (entry.isDirectory()) {
                    Files.createDirectories(outputPath);
                } else {
                    Files.createDirectories(outputPath.getParent());
                    Files.copy(zis, outputPath, StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.debug("Extrait : {}", entry.getName());
                }
                
                zis.closeEntry();
            }
            
            LOGGER.info("Natives extraites dans : {}", destination);
            
        } catch (IOException e) {
            LOGGER.error("Erreur lors de l'extraction des natives", e);
        }
    }
    
    /**
     * Télécharge les assets (ressources du jeu : sons, textures, etc.).
     * 
     * Le processus est :
     * 1. Télécharger l'index des assets (JSON listant tous les fichiers)
     * 2. Pour chaque asset, télécharger depuis resources.download.minecraft.net
     * 
     * @param versionJson Le JSON détaillé de la version
     * @return true si le téléchargement a réussi
     */
    public boolean downloadAssets(JsonObject versionJson) {
        LOGGER.info("Téléchargement des assets...");
        
        if (!versionJson.has("assetIndex")) {
            LOGGER.warn("Pas d'assetIndex dans le JSON");
            return false;
        }
        
        try {
            // 1. Télécharger l'index des assets
            JsonObject assetIndex = versionJson.getAsJsonObject("assetIndex");
            String indexId = assetIndex.get("id").getAsString();
            String indexUrl = assetIndex.get("url").getAsString();
            
            LOGGER.info("Téléchargement de l'index : {}", indexId);
            String indexContent = downloadString(indexUrl);
            if (indexContent == null) {
                return false;
            }
            
            // Sauvegarde de l'index
            Path indexesDir = assetsDir.resolve("indexes");
            Files.createDirectories(indexesDir);
            Path indexFile = indexesDir.resolve(indexId + ".json");
            Files.writeString(indexFile, indexContent);
            
            // 2. Parser l'index et télécharger les assets
            JsonObject indexJson = JsonParser.parseString(indexContent).getAsJsonObject();
            JsonObject objects = indexJson.getAsJsonObject("objects");
            
            int total = objects.size();
            int current = 0;
            int skipped = 0;
            
            LOGGER.info("Téléchargement de {} assets...", total);
            
            Path objectsDir = assetsDir.resolve("objects");
            
            for (String key : objects.keySet()) {
                current++;
                
                JsonObject assetObj = objects.getAsJsonObject(key);
                String hash = assetObj.get("hash").getAsString();
                int size = assetObj.get("size").getAsInt();
                
                // Structure : objects/ab/abcdef123...
                String hashPrefix = hash.substring(0, 2);
                Path assetDir = objectsDir.resolve(hashPrefix);
                Path assetFile = assetDir.resolve(hash);
                
                // Vérifier si déjà téléchargé
                if (Files.exists(assetFile) && Files.size(assetFile) == size) {
                    skipped++;
                    continue;
                }
                
                // URL de téléchargement
                String assetUrl = "https://resources.download.minecraft.net/" + hashPrefix + "/" + hash;
                
                // Téléchargement
                if (!downloadFile(assetUrl, assetFile)) {
                    LOGGER.warn("Échec du téléchargement : {}", key);
                }
                
                // Log de progression tous les 10%
                if (current % Math.max(1, total / 10) == 0) {
                    LOGGER.info("Progression assets : {}/{} ({}%)", current, total, (current * 100) / total);
                }
            }
            
            LOGGER.info("Assets téléchargés : {} nouveaux, {} déjà présents", current - skipped, skipped);
            return true;
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du téléchargement des assets", e);
            return false;
        }
    }
    
    public Path getMinecraftDir() {
        return minecraftDir;
    }
    
    public Path getVersionsDir() {
        return versionsDir;
    }
    
    public Path getNativesDir() {
        return nativesDir;
    }
}
