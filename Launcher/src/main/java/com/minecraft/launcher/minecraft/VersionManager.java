package com.minecraft.launcher.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire des versions Minecraft via l'API officielle Mojang.
 * 
 * Cette classe communique avec l'API Mojang pour récupérer la liste
 * de toutes les versions Minecraft disponibles (releases, snapshots, betas, alphas).
 * 
 * API utilisée : https://launchermeta.mojang.com/mc/game/version_manifest.json
 */
public class VersionManager {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionManager.class);
    
    // URL de l'API Mojang pour la liste des versions
    private static final String VERSION_MANIFEST_URL = 
        "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    
    private List<MinecraftVersion> versions;
    private MinecraftVersion latestRelease;
    private MinecraftVersion latestSnapshot;
    
    /**
     * Constructeur. Initialise la liste des versions.
     */
    public VersionManager() {
        this.versions = new ArrayList<>();
    }
    
    /**
     * Récupère la liste de toutes les versions Minecraft depuis l'API Mojang.
     * 
     * Cette méthode fait une requête HTTP GET vers l'API officielle,
     * parse le JSON retourné et construit la liste des versions.
     * 
     * @return true si la récupération a réussi, false sinon
     */
    public boolean fetchVersions() {
        LOGGER.info("Récupération de la liste des versions Minecraft...");
        
        try {
            // 1. Connexion à l'API Mojang
            URL url = new URL(VERSION_MANIFEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            // 2. Lecture de la réponse
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                LOGGER.error("Erreur HTTP : code {}", responseCode);
                return false;
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
            
            // 3. Parse du JSON
            JsonObject root = JsonParser.parseString(response.toString()).getAsJsonObject();
            
            // 3.1 Récupération des dernières versions
            JsonObject latest = root.getAsJsonObject("latest");
            String latestReleaseId = latest.get("release").getAsString();
            String latestSnapshotId = latest.get("snapshot").getAsString();
            
            // 3.2 Récupération de la liste complète des versions
            JsonArray versionsArray = root.getAsJsonArray("versions");
            versions.clear();
            
            for (JsonElement element : versionsArray) {
                JsonObject versionObj = element.getAsJsonObject();
                
                String id = versionObj.get("id").getAsString();
                String type = versionObj.get("type").getAsString();
                String versionUrl = versionObj.get("url").getAsString();
                String releaseTime = versionObj.get("releaseTime").getAsString();
                
                MinecraftVersion version = new MinecraftVersion(id, type, versionUrl, releaseTime);
                versions.add(version);
                
                // Identification des dernières versions
                if (id.equals(latestReleaseId)) {
                    latestRelease = version;
                }
                if (id.equals(latestSnapshotId)) {
                    latestSnapshot = version;
                }
            }
            
            LOGGER.info("Récupération réussie : {} versions disponibles", versions.size());
            LOGGER.info("Dernière release : {}", latestRelease.getId());
            LOGGER.info("Dernier snapshot : {}", latestSnapshot.getId());
            
            return true;
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la récupération des versions", e);
            return false;
        }
    }
    
    /**
     * Retourne la liste complète des versions.
     */
    public List<MinecraftVersion> getVersions() {
        return new ArrayList<>(versions);
    }
    
    /**
     * Retourne uniquement les releases stables.
     */
    public List<MinecraftVersion> getReleases() {
        return versions.stream()
            .filter(MinecraftVersion::isRelease)
            .toList();
    }
    
    /**
     * Retourne uniquement les snapshots.
     */
    public List<MinecraftVersion> getSnapshots() {
        return versions.stream()
            .filter(MinecraftVersion::isSnapshot)
            .toList();
    }
    
    /**
     * Récupère une version par son ID.
     */
    public MinecraftVersion getVersionById(String id) {
        return versions.stream()
            .filter(v -> v.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Retourne la dernière release stable.
     */
    public MinecraftVersion getLatestRelease() {
        return latestRelease;
    }
    
    /**
     * Retourne le dernier snapshot.
     */
    public MinecraftVersion getLatestSnapshot() {
        return latestSnapshot;
    }
}
