package com.minecraft.launcher.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur pour l'écran de connexion Microsoft.
 * Gère l'authentification OAuth 2.0 et le système multi-comptes.
 */
public class LoginController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    
    @FXML private VBox accountsBox;
    @FXML private ListView<String> accountsList;
    @FXML private Button loginButton;
    @FXML private Label statusLabel;
    
    private List<MinecraftAccount> savedAccounts;
    private MainController mainController;
    
    /**
     * Initialisation du contrôleur
     */
    @FXML
    public void initialize() {
        LOGGER.info("Initialisation de l'écran de connexion...");
        
        savedAccounts = new ArrayList<>();
        loadSavedAccounts();
        
        // Configuration de la liste des comptes
        if (savedAccounts.isEmpty()) {
            accountsBox.setVisible(false);
            accountsBox.setManaged(false);
        } else {
            updateAccountsList();
            
            // Sélection d'un compte sauvegardé
            accountsList.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    String selected = accountsList.getSelectionModel().getSelectedItem();
                    if (selected != null) {
                        loginWithSavedAccount(selected);
                    }
                }
            });
        }
    }
    
    /**
     * Définir le contrôleur principal pour basculer vers l'écran launcher
     */
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
    
    /**
     * Charge les comptes sauvegardés depuis le disque
     */
    private void loadSavedAccounts() {
        // TODO: Implémenter le chargement depuis un fichier JSON
        // Pour l'instant, simulation avec des comptes de test
        LOGGER.info("Chargement des comptes sauvegardés...");
    }
    
    /**
     * Met à jour l'affichage de la liste des comptes
     */
    private void updateAccountsList() {
        List<String> accountNames = savedAccounts.stream()
            .map(MinecraftAccount::getUsername)
            .toList();
        
        accountsList.setItems(FXCollections.observableArrayList(accountNames));
    }
    
    /**
     * Gère le clic sur le bouton de connexion Microsoft
     */
    @FXML
    private void onLoginClicked() {
        LOGGER.info("Tentative de connexion Microsoft OAuth 2.0...");
        
        loginButton.setDisable(true);
        statusLabel.setText("🔄 Connexion en cours...");
        statusLabel.setStyle("-fx-text-fill: #00d9ff;");
        
        // Authentification dans un thread séparé
        new Thread(() -> {
            try {
                // TODO: Implémenter vraie authentification Microsoft OAuth 2.0
                // Pour l'instant, simulation
                Thread.sleep(2000);
                
                // Simulation de succès
                String username = "Joueur_" + System.currentTimeMillis() % 10000;
                String accessToken = "mock_token_" + System.nanoTime();
                String uuid = "00000000-0000-0000-0000-" + String.format("%012d", System.currentTimeMillis() % 1000000000000L);
                
                MinecraftAccount account = new MinecraftAccount(username, uuid, accessToken);
                savedAccounts.add(account);
                
                Platform.runLater(() -> {
                    statusLabel.setText("✅ Connexion réussie !");
                    statusLabel.setStyle("-fx-text-fill: #00ff00;");
                    
                    LOGGER.info("Connexion réussie : {}", username);
                    
                    // Basculer vers l'écran principal après 1 seconde
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                            Platform.runLater(() -> switchToMainScreen(account));
                        } catch (InterruptedException e) {
                            LOGGER.error("Erreur lors du délai", e);
                        }
                    }).start();
                });
                
            } catch (InterruptedException e) {
                Platform.runLater(() -> {
                    statusLabel.setText("❌ Erreur de connexion");
                    statusLabel.setStyle("-fx-text-fill: #ff4444;");
                    loginButton.setDisable(false);
                    LOGGER.error("Erreur lors de l'authentification", e);
                });
            }
        }).start();
    }
    
    /**
     * Connexion avec un compte sauvegardé
     */
    private void loginWithSavedAccount(String username) {
        LOGGER.info("Connexion avec compte sauvegardé : {}", username);
        
        MinecraftAccount account = savedAccounts.stream()
            .filter(a -> a.getUsername().equals(username))
            .findFirst()
            .orElse(null);
        
        if (account != null) {
            statusLabel.setText("✅ Connexion avec " + username);
            switchToMainScreen(account);
        }
    }
    
    /**
     * Bascule vers l'écran principal du launcher
     */
    private void switchToMainScreen(MinecraftAccount account) {
        LOGGER.info("Basculement vers l'écran launcher...");
        
        try {
            javafx.scene.Scene scene = loginButton.getScene();
            javafx.stage.Stage stage = (javafx.stage.Stage) scene.getWindow();
            
            // Charger le FXML principal
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/fxml/main.fxml")
            );
            javafx.scene.Parent root = loader.load();
            
            // Obtenir le contrôleur et passer le compte
            MainController controller = loader.getController();
            controller.setAuthenticatedAccount(account);
            
            // Changer la scène
            scene.setRoot(root);
            
            LOGGER.info("Écran launcher chargé avec succès");
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du chargement de l'écran launcher", e);
            statusLabel.setText("❌ Erreur lors du chargement");
        }
    }
    
    /**
     * Classe représentant un compte Minecraft
     */
    public static class MinecraftAccount {
        private final String username;
        private final String uuid;
        private final String accessToken;
        
        public MinecraftAccount(String username, String uuid, String accessToken) {
            this.username = username;
            this.uuid = uuid;
            this.accessToken = accessToken;
        }
        
        public String getUsername() {
            return username;
        }
        
        public String getUuid() {
            return uuid;
        }
        
        public String getAccessToken() {
            return accessToken;
        }
    }
}
