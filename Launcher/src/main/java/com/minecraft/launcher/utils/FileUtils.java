package com.minecraft.launcher.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Utilitaires pour la gestion des fichiers.
 * 
 * Cette classe fournit des méthodes pratiques pour :
 * - Créer des dossiers
 * - Supprimer des dossiers récursivement
 * - Vérifier l'existence de fichiers
 * - Calculer la taille de fichiers
 * - Nettoyer le cache
 */
public class FileUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
    
    /**
     * Crée un dossier et tous ses parents si nécessaire.
     * 
     * @param path Chemin du dossier à créer
     * @return true si le dossier existe après l'opération
     */
    public static boolean createDirectory(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                LOGGER.info("Dossier créé : {}", path);
            }
            return true;
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la création du dossier : {}", path, e);
            return false;
        }
    }
    
    /**
     * Supprime un dossier et tout son contenu de manière récursive.
     * 
     * ATTENTION : Cette opération est irréversible !
     * 
     * @param path Chemin du dossier à supprimer
     * @return true si la suppression a réussi
     */
    public static boolean deleteDirectory(Path path) {
        if (!Files.exists(path)) {
            return true;
        }
        
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
            
            LOGGER.info("Dossier supprimé : {}", path);
            return true;
            
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la suppression du dossier : {}", path, e);
            return false;
        }
    }
    
    /**
     * Calcule la taille d'un fichier ou dossier en octets.
     * 
     * @param path Chemin du fichier ou dossier
     * @return Taille en octets, ou 0 en cas d'erreur
     */
    public static long getSize(Path path) {
        if (!Files.exists(path)) {
            return 0;
        }
        
        try {
            if (Files.isDirectory(path)) {
                try (Stream<Path> walk = Files.walk(path)) {
                    return walk
                        .filter(Files::isRegularFile)
                        .mapToLong(p -> {
                            try {
                                return Files.size(p);
                            } catch (IOException e) {
                                return 0;
                            }
                        })
                        .sum();
                }
            } else {
                return Files.size(path);
            }
        } catch (IOException e) {
            LOGGER.error("Erreur lors du calcul de la taille : {}", path, e);
            return 0;
        }
    }
    
    /**
     * Formate une taille en octets en format lisible (Ko, Mo, Go).
     * 
     * @param bytes Taille en octets
     * @return Chaîne formatée (ex: "1.5 Mo")
     */
    public static String formatSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " o";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f Ko", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.1f Mo", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f Go", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * Vérifie si un fichier existe et n'est pas vide.
     * 
     * @param path Chemin du fichier
     * @return true si le fichier existe et a une taille > 0
     */
    public static boolean isValidFile(Path path) {
        try {
            return Files.exists(path) 
                && Files.isRegularFile(path) 
                && Files.size(path) > 0;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Copie un fichier.
     * 
     * @param source Fichier source
     * @param destination Fichier destination
     * @return true si la copie a réussi
     */
    public static boolean copyFile(Path source, Path destination) {
        try {
            Files.createDirectories(destination.getParent());
            Files.copy(source, destination);
            LOGGER.info("Fichier copié : {} -> {}", source, destination);
            return true;
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la copie : {} -> {}", source, destination, e);
            return false;
        }
    }
}
