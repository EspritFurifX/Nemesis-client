package com.minecraft.launcher.minecraft;

/**
 * Représente une version Minecraft disponible sur les serveurs Mojang.
 * 
 * Cette classe contient toutes les informations nécessaires pour identifier
 * et télécharger une version spécifique de Minecraft Java Edition.
 */
public class MinecraftVersion {
    
    private String id;              // Ex: "1.20.4", "1.19.2", "23w51b"
    private String type;            // Ex: "release", "snapshot", "old_beta", "old_alpha"
    private String url;             // URL du JSON détaillé de la version
    private String releaseTime;     // Date de sortie ISO 8601
    
    /**
     * Constructeur complet.
     * 
     * @param id Identifiant de la version (ex: "1.20.4")
     * @param type Type de version (release, snapshot, etc.)
     * @param url URL du manifest JSON détaillé
     * @param releaseTime Date de sortie
     */
    public MinecraftVersion(String id, String type, String url, String releaseTime) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.releaseTime = releaseTime;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getReleaseTime() {
        return releaseTime;
    }
    
    /**
     * Vérifie si la version est une release stable.
     */
    public boolean isRelease() {
        return "release".equals(type);
    }
    
    /**
     * Vérifie si la version est un snapshot.
     */
    public boolean isSnapshot() {
        return "snapshot".equals(type);
    }
    
    @Override
    public String toString() {
        return id + " (" + type + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MinecraftVersion that = (MinecraftVersion) obj;
        return id != null && id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
