package com.minecraft.launcher.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utilitaires pour la manipulation de fichiers JSON.
 * 
 * Cette classe fournit des méthodes pour :
 * - Lire des fichiers JSON
 * - Écrire des fichiers JSON
 * - Parser des chaînes JSON
 * - Formater du JSON (pretty print)
 */
public class JsonUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    
    /**
     * Lit un fichier JSON et retourne un JsonObject.
     * 
     * @param path Chemin du fichier JSON
     * @return JsonObject parsé, ou null en cas d'erreur
     */
    public static JsonObject readJsonFile(Path path) {
        try {
            if (!Files.exists(path)) {
                LOGGER.warn("Fichier JSON inexistant : {}", path);
                return null;
            }
            
            String content = Files.readString(path);
            return JsonParser.parseString(content).getAsJsonObject();
            
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la lecture du fichier JSON : {}", path, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Erreur lors du parsing du JSON : {}", path, e);
            return null;
        }
    }
    
    /**
     * Écrit un JsonObject dans un fichier.
     * 
     * @param jsonObject JsonObject à écrire
     * @param path Chemin du fichier de destination
     * @return true si l'écriture a réussi
     */
    public static boolean writeJsonFile(JsonObject jsonObject, Path path) {
        try {
            Files.createDirectories(path.getParent());
            String json = GSON.toJson(jsonObject);
            Files.writeString(path, json);
            
            LOGGER.info("Fichier JSON écrit : {}", path);
            return true;
            
        } catch (IOException e) {
            LOGGER.error("Erreur lors de l'écriture du fichier JSON : {}", path, e);
            return false;
        }
    }
    
    /**
     * Parse une chaîne JSON en JsonObject.
     * 
     * @param jsonString Chaîne JSON
     * @return JsonObject parsé, ou null en cas d'erreur
     */
    public static JsonObject parseJson(String jsonString) {
        try {
            return JsonParser.parseString(jsonString).getAsJsonObject();
        } catch (Exception e) {
            LOGGER.error("Erreur lors du parsing de la chaîne JSON", e);
            return null;
        }
    }
    
    /**
     * Formate un JsonObject en chaîne JSON pretty-printed.
     * 
     * @param jsonObject JsonObject à formater
     * @return Chaîne JSON formatée
     */
    public static String toPrettyJson(JsonObject jsonObject) {
        return GSON.toJson(jsonObject);
    }
    
    /**
     * Vérifie si une chaîne est un JSON valide.
     * 
     * @param jsonString Chaîne à vérifier
     * @return true si le JSON est valide
     */
    public static boolean isValidJson(String jsonString) {
        try {
            JsonParser.parseString(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
