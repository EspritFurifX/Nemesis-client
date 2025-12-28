package com.minecraft.launcher.minecraft;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestionnaire de lancement de Minecraft.
 * 
 * Cette classe est responsable de la construction de la ligne de commande
 * et du lancement effectif du jeu Minecraft via ProcessBuilder.
 * 
 * Elle gère :
 * - La détection du Java installé
 * - La construction des arguments JVM
 * - La construction des arguments du jeu
 * - Le lancement et l'affichage des logs en temps réel
 */
public class LaunchManager {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchManager.class);
    
    private final DownloadManager downloadManager;
    private Process gameProcess;
    
    /**
     * Constructeur.
     * 
     * @param downloadManager Gestionnaire de téléchargement pour accéder aux chemins
     */
    public LaunchManager(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }
    
    /**
     * Lance une version de Minecraft.
     * 
     * @param version La version à lancer
     * @param versionJson Le JSON détaillé de la version
     * @param libraryPaths Liste des chemins des libraries (pour le classpath)
     * @param username Nom d'utilisateur (mode hors ligne)
     * @param ramMb Quantité de RAM allouée en Mo (ex: 2048)
     * @return true si le lancement a réussi
     */
    public boolean launchGame(MinecraftVersion version, JsonObject versionJson,
                             List<Path> libraryPaths, String username, int ramMb) {
        
        LOGGER.info("Préparation du lancement de Minecraft {}...", version.getId());
        LOGGER.info("Joueur : {}", username);
        LOGGER.info("RAM allouée : {} Mo", ramMb);
        
        try {
            // 1. Construction de la commande
            List<String> command = buildLaunchCommand(version, versionJson, libraryPaths, username, ramMb);
            
            // 2. Affichage de la commande (debug)
            LOGGER.info("Commande de lancement :");
            LOGGER.info("{}", String.join(" ", command));
            
            // 3. Lancement du processus
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(downloadManager.getMinecraftDir().toFile());
            processBuilder.redirectErrorStream(true); // Combine stdout et stderr
            
            gameProcess = processBuilder.start();
            LOGGER.info("Minecraft démarré ! PID : {}", gameProcess.pid());
            
            // 4. Lecture des logs en temps réel dans un thread séparé
            startLogReader();
            
            return true;
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du lancement du jeu", e);
            return false;
        }
    }
    
    /**
     * Construit la ligne de commande complète pour lancer Minecraft.
     * 
     * C'est ici que toute la magie opère !
     * La commande finale ressemble à :
     * 
     * java -Xmx2G -Djava.library.path=natives -cp libraries/*:client.jar \
     *      net.minecraft.client.main.Main --username Player --version 1.20.4 ...
     */
    private List<String> buildLaunchCommand(MinecraftVersion version, JsonObject versionJson,
                                           List<Path> libraryPaths, String username, int ramMb) {
        
        List<String> command = new ArrayList<>();
        
        // 1. Exécutable Java
        String javaPath = getJavaExecutable();
        command.add(javaPath);
        
        // 2. Arguments JVM
        command.add("-Xmx" + ramMb + "M"); // RAM maximale
        command.add("-Xms" + (ramMb / 2) + "M"); // RAM initiale
        
        // Arguments JVM recommandés par Mojang
        command.add("-XX:+UnlockExperimentalVMOptions");
        command.add("-XX:+UseG1GC");
        command.add("-XX:G1NewSizePercent=20");
        command.add("-XX:G1ReservePercent=20");
        command.add("-XX:MaxGCPauseMillis=50");
        command.add("-XX:G1HeapRegionSize=32M");
        
        // Dossiers Minecraft
        Path gameDir = downloadManager.getMinecraftDir();
        Path versionsDir = downloadManager.getVersionsDir();
        Path versionDir = versionsDir.resolve(version.getId());
        Path jarPath = versionDir.resolve(version.getId() + ".jar");
        
        // 3. Chemin des natives (bibliothèques OS-spécifiques)
        // Très important pour LWJGL/OpenGL/OpenAL
        Path nativesDir = downloadManager.getNativesDir();
        command.add("-Djava.library.path=" + nativesDir.toAbsolutePath().toString());
        
        // 4. Classpath COMPLET (le secret d'un launcher qui marche !)
        // Le classpath doit contenir :
        // - Toutes les libraries
        // - Le JAR client
        command.add("-cp");
        
        // Construction du classpath
        String separator = System.getProperty("path.separator"); // : sur Unix, ; sur Windows
        StringBuilder classpath = new StringBuilder();
        
        // 4.1 Ajouter toutes les libraries
        for (Path libPath : libraryPaths) {
            classpath.append(libPath.toAbsolutePath().toString());
            classpath.append(separator);
        }
        
        // 4.2 Ajouter le JAR client en dernier
        classpath.append(jarPath.toAbsolutePath().toString());
        
        command.add(classpath.toString());
        
        LOGGER.info("Classpath : {} entrées", libraryPaths.size() + 1);
        
        // 5. Classe principale (varie selon les versions)
        String mainClass = versionJson.get("mainClass").getAsString();
        command.add(mainClass);
        
        // 6. Arguments du jeu (game arguments)
        command.add("--username");
        command.add(username);
        
        command.add("--version");
        command.add(version.getId());
        
        command.add("--gameDir");
        command.add(gameDir.toString());
        
        command.add("--assetsDir");
        command.add(gameDir.resolve("assets").toString());
        
        // Index des assets (depuis le JSON)
        if (versionJson.has("assetIndex")) {
            JsonObject assetIndex = versionJson.getAsJsonObject("assetIndex");
            command.add("--assetIndex");
            command.add(assetIndex.get("id").getAsString());
        }
        
        // UUID (généré aléatoirement pour le mode hors ligne)
        command.add("--uuid");
        command.add(generateOfflineUUID(username));
        
        // Type d'accès (hors ligne)
        command.add("--accessToken");
        command.add("0");
        
        command.add("--userType");
        command.add("legacy");
        
        command.add("--versionType");
        command.add(version.getType());
        
        return command;
    }
    
    /**
     * Récupère le chemin de l'exécutable Java.
     * 
     * Utilise le Java de la variable d'environnement JAVA_HOME,
     * ou le java dans le PATH par défaut.
     */
    private String getJavaExecutable() {
        String javaHome = System.getenv("JAVA_HOME");
        
        if (javaHome != null && !javaHome.isEmpty()) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                return javaHome + "\\bin\\java.exe";
            } else {
                return javaHome + "/bin/java";
            }
        }
        
        // Par défaut, utilise "java" du PATH
        return "java";
    }
    
    /**
     * Génère un UUID pour le mode hors ligne.
     * 
     * Note : Ceci est une version simplifiée. Dans un vrai launcher,
     * l'UUID serait calculé via un algorithme spécifique.
     */
    private String generateOfflineUUID(String username) {
        // Format UUID simple basé sur le hash du username
        int hash = username.hashCode();
        return String.format("00000000-0000-0000-0000-%012d", Math.abs(hash));
    }
    
    /**
     * Démarre un thread qui lit et affiche les logs du jeu en temps réel.
     */
    private void startLogReader() {
        Thread logThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(gameProcess.getInputStream()))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    // Affichage des logs du jeu
                    LOGGER.info("[GAME] {}", line);
                }
                
            } catch (IOException e) {
                LOGGER.error("Erreur lors de la lecture des logs du jeu", e);
            }
        });
        
        logThread.setDaemon(true);
        logThread.setName("Minecraft-Log-Reader");
        logThread.start();
    }
    
    /**
     * Vérifie si le jeu est en cours d'exécution.
     */
    public boolean isGameRunning() {
        return gameProcess != null && gameProcess.isAlive();
    }
    
    /**
     * Arrête le jeu s'il est en cours d'exécution.
     */
    public void stopGame() {
        if (gameProcess != null && gameProcess.isAlive()) {
            LOGGER.info("Arrêt du jeu...");
            gameProcess.destroy();
            
            try {
                gameProcess.waitFor();
                LOGGER.info("Jeu arrêté");
            } catch (InterruptedException e) {
                LOGGER.error("Erreur lors de l'arrêt", e);
            }
        }
    }
}
