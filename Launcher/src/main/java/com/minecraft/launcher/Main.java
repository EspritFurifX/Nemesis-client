package com.minecraft.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Point d'entrée principal du Launcher Minecraft.
 * 
 * Cette classe lance l'application JavaFX et charge l'interface principale.
 * 
 * Pour lancer l'application :
 * - Depuis Maven : mvn javafx:run
 * - Depuis le JAR : java -jar minecraft-educational-launcher-1.0.0-SNAPSHOT.jar
 * 
 * @author Launcher Minecraft Éducatif
 * @version 1.0.0
 */
public class Main extends Application {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    /**
     * Méthode principale de l'application JavaFX.
     * 
     * Cette méthode est appelée automatiquement au démarrage
     * et configure la fenêtre principale.
     * 
     * @param primaryStage Stage principal de l'application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            LOGGER.info("=== Démarrage du Launcher Minecraft ===");
            LOGGER.info("Système : {}", System.getProperty("os.name"));
            LOGGER.info("Java : {}", System.getProperty("java.version"));
            LOGGER.info("JavaFX : {}", System.getProperty("javafx.version"));
            
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/main.fxml")
            );
            Parent root = loader.load();
            
            // Configuration de la scène
            Scene scene = new Scene(root, 550, 850);
            
            // Configuration de la fenêtre
            primaryStage.setTitle("Minecraft Launcher - Éducatif");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Taille fixe pour un design cohérent
            
            // Affichage de la fenêtre
            primaryStage.show();
            
            LOGGER.info("Interface chargée avec succès");
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du démarrage de l'application", e);
            System.err.println("Erreur critique : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Méthode appelée lors de la fermeture de l'application.
     * 
     * Permet de nettoyer les ressources et sauvegarder les paramètres.
     */
    @Override
    public void stop() {
        LOGGER.info("Fermeture du launcher");
        // Ici vous pourriez ajouter :
        // - Sauvegarde des préférences utilisateur
        // - Arrêt des téléchargements en cours
        // - Fermeture des connexions réseau
        // - etc.
    }
    
    /**
     * Point d'entrée Java standard.
     * 
     * Lance l'application JavaFX via Application.launch().
     * 
     * @param args Arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        LOGGER.info("Lancement de l'application...");
        
        // Vérification de la version Java
        String javaVersion = System.getProperty("java.version");
        LOGGER.info("Version Java détectée : {}", javaVersion);
        
        // Avertissement si Java < 17
        try {
            int majorVersion = Integer.parseInt(javaVersion.split("\\.")[0]);
            if (majorVersion < 17) {
                LOGGER.warn("Java 17 ou supérieur est recommandé. Version actuelle : {}", javaVersion);
            }
        } catch (Exception e) {
            LOGGER.warn("Impossible de déterminer la version Java majeure");
        }
        
        // Lancement de l'application JavaFX
        launch(args);
    }
}
