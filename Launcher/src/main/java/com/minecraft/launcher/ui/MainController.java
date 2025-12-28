package com.minecraft.launcher.ui;

import com.minecraft.launcher.minecraft.*;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.List;

/**
 * Contrôleur JavaFX pour l'interface principale du launcher.
 * 
 * Cette classe gère toutes les interactions utilisateur :
 * - Récupération et affichage des versions Minecraft
 * - Sélection d'une version
 * - Configuration du lancement
 * - Lancement du jeu
 * - Affichage des logs
 */
public class MainController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    
    // Composants FXML (liés au fichier main.fxml)
    @FXML private ComboBox<MinecraftVersion> versionComboBox;
    @FXML private TextField usernameField;
    @FXML private Spinner<Integer> ramSpinner;
    @FXML private Button singleplayerButton;
    @FXML private Button multiplayerButton;
    @FXML private Button refreshButton;
    @FXML private TextArea logArea;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;
    @FXML private CheckBox releasesOnlyCheckbox;
    
    // Gestionnaires
    private VersionManager versionManager;
    private DownloadManager downloadManager;
    private LaunchManager launchManager;
    
    /**
     * Méthode d'initialisation appelée automatiquement par JavaFX.
     * 
     * Cette méthode est appelée après que tous les composants FXML
     * ont été injectés via @FXML.
     */
    @FXML
    public void initialize() {
        LOGGER.info("Initialisation du contrôleur...");
        
        // 1. Configuration du Spinner de RAM (512 Mo à 16 Go)
        SpinnerValueFactory<Integer> ramFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(512, 16384, 2048, 512);
        ramSpinner.setValueFactory(ramFactory);
        
        // 2. Configuration du nom d'utilisateur par défaut
        usernameField.setText("Joueur_" + System.currentTimeMillis() % 10000);
        
        // 3. Initialisation des gestionnaires
        Path minecraftDir = Paths.get(System.getProperty("user.home"), ".minecraft-launcher");
        downloadManager = new DownloadManager(minecraftDir);
        launchManager = new LaunchManager(downloadManager);
        versionManager = new VersionManager();
        
        // 4. Configuration de la ComboBox de versions
        versionComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MinecraftVersion item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getId() + " [" + item.getType() + "]");
                }
            }
        });
        versionComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(MinecraftVersion item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getId());
                }
            }
        });
        
        // 5. Chargement initial des versions
        loadVersions();
        
        LOGGER.info("Contrôleur initialisé");
    }
    
    /**
     * Charge la liste des versions Minecraft depuis l'API Mojang.
     */
    @FXML
    private void loadVersions() {
        LOGGER.info("Chargement des versions...");
        statusLabel.setText("Chargement des versions...");
        progressBar.setProgress(-1); // Mode indéterminé
        
        // Exécution dans un thread séparé pour ne pas bloquer l'UI
        new Thread(() -> {
            boolean success = versionManager.fetchVersions();
            
            // Mise à jour de l'UI dans le thread JavaFX
            Platform.runLater(() -> {
                if (success) {
                    updateVersionList();
                    statusLabel.setText("Prêt - " + versionManager.getVersions().size() + " versions disponibles");
                    logMessage("Versions chargées avec succès");
                } else {
                    statusLabel.setText("Erreur lors du chargement des versions");
                    showError("Impossible de récupérer les versions Minecraft. Vérifiez votre connexion internet.");
                }
                progressBar.setProgress(0);
            });
        }).start();
    }
    
    /**
     * Met à jour la liste des versions dans la ComboBox.
     */
    private void updateVersionList() {
        List<MinecraftVersion> versions;
        
        if (releasesOnlyCheckbox.isSelected()) {
            versions = versionManager.getReleases();
            logMessage("Affichage des releases uniquement");
        } else {
            versions = versionManager.getVersions();
            logMessage("Affichage de toutes les versions");
        }
        
        versionComboBox.setItems(FXCollections.observableArrayList(versions));
        
        // Sélection de la dernière release par défaut
        if (!versions.isEmpty()) {
            MinecraftVersion latest = versionManager.getLatestRelease();
            if (latest != null && versions.contains(latest)) {
                versionComboBox.setValue(latest);
            } else {
                versionComboBox.setValue(versions.get(0));
            }
        }
    }
    
    /**
     * Lance le jeu en mode solo.
     */
    @FXML
    private void onSingleplayerClicked() {
        launchGame();
    }
    
    /**
     * Lance le jeu en mode multijoueur.
     * 
     * Note : Le mode solo et multijoueur lancent le même jeu.
     * C'est ensuite dans le jeu que le joueur choisit solo ou multi.
     */
    @FXML
    private void onMultiplayerClicked() {
        launchGame();
    }
    
    /**
     * Recharge la liste des versions.
     */
    @FXML
    private void onRefreshClicked() {
        loadVersions();
    }
    
    /**
     * Appelée quand la checkbox "Releases seulement" change.
     */
    @FXML
    private void onReleasesOnlyChanged() {
        updateVersionList();
    }
    
    /**
     * Lance le jeu avec les paramètres sélectionnés.
     */
    private void launchGame() {
        // 1. Validation des paramètres
        MinecraftVersion selectedVersion = versionComboBox.getValue();
        if (selectedVersion == null) {
            showError("Veuillez sélectionner une version");
            return;
        }
        
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showError("Veuillez entrer un nom d'utilisateur");
            return;
        }
        
        int ram = ramSpinner.getValue();
        
        // 2. Désactivation des boutons pendant le lancement
        setButtonsEnabled(false);
        statusLabel.setText("Préparation du lancement...");
        logMessage("=== Lancement de Minecraft " + selectedVersion.getId() + " ===");
        logMessage("Joueur : " + username);
        logMessage("RAM : " + ram + " Mo");
        
        // 3. Téléchargement et lancement dans un thread séparé
        new Thread(() -> {
            try {
                // 3.1 Téléchargement du JSON de version
                Platform.runLater(() -> {
                    statusLabel.setText("Téléchargement des informations de version...");
                    progressBar.setProgress(-1);
                });
                
                JsonObject versionJson = downloadManager.downloadVersionJson(selectedVersion);
                if (versionJson == null) {
                    Platform.runLater(() -> {
                        showError("Erreur lors du téléchargement des informations de version");
                        setButtonsEnabled(true);
                        statusLabel.setText("Erreur");
                        progressBar.setProgress(0);
                    });
                    return;
                }
                
                Platform.runLater(() -> logMessage("✓ Informations de version téléchargées"));
                
                // 3.2 Téléchargement du JAR client
                Platform.runLater(() -> statusLabel.setText("Téléchargement du client..."));
                
                boolean jarDownloaded = downloadManager.downloadClientJar(selectedVersion, versionJson);
                if (!jarDownloaded) {
                    Platform.runLater(() -> {
                        showError("Erreur lors du téléchargement du client");
                        setButtonsEnabled(true);
                        statusLabel.setText("Erreur");
                        progressBar.setProgress(0);
                    });
                    return;
                }
                
                Platform.runLater(() -> logMessage("✓ Client téléchargé"));
                
                // 3.3 Téléchargement des libraries (NOUVEAU !)
                Platform.runLater(() -> {
                    statusLabel.setText("Téléchargement des libraries...");
                    logMessage("Téléchargement des dépendances Java...");
                });
                
                List<Path> libraryPaths = downloadManager.downloadLibraries(versionJson);
                if (libraryPaths.isEmpty()) {
                    Platform.runLater(() -> {
                        logMessage("⚠ Aucune library téléchargée (peut causer des problèmes)");
                    });
                } else {
                    Platform.runLater(() -> logMessage("✓ " + libraryPaths.size() + " libraries téléchargées"));
                }
                
                // 3.4 Téléchargement des assets (NOUVEAU !)
                Platform.runLater(() -> {
                    statusLabel.setText("Téléchargement des ressources (sons, textures)...");
                    logMessage("Téléchargement des assets (peut prendre du temps)...");
                });
                
                boolean assetsDownloaded = downloadManager.downloadAssets(versionJson);
                if (assetsDownloaded) {
                    Platform.runLater(() -> logMessage("✓ Assets téléchargés"));
                } else {
                    Platform.runLater(() -> logMessage("⚠ Erreur lors du téléchargement des assets"));
                }
                
                // 3.5 Lancement du jeu
                Platform.runLater(() -> {
                    statusLabel.setText("Lancement du jeu...");
                    logMessage("Construction de la commande de lancement...");
                });
                
                boolean launched = launchManager.launchGame(
                    selectedVersion, 
                    versionJson,
                    libraryPaths,  // NOUVEAU : on passe les libraries
                    username, 
                    ram
                );
                
                Platform.runLater(() -> {
                    if (launched) {
                        statusLabel.setText("🎮 Jeu lancé !");
                        logMessage("✓ Minecraft lancé avec succès");
                        logMessage("Vous pouvez maintenant fermer le launcher ou consulter les logs du jeu ci-dessus");
                    } else {
                        showError("Erreur lors du lancement du jeu. Vérifiez les logs.");
                        statusLabel.setText("Erreur");
                    }
                    
                    setButtonsEnabled(true);
                    progressBar.setProgress(0);
                });
                
            } catch (Exception e) {
                LOGGER.error("Erreur lors du lancement", e);
                Platform.runLater(() -> {
                    showError("Erreur : " + e.getMessage());
                    setButtonsEnabled(true);
                    statusLabel.setText("Erreur");
                    progressBar.setProgress(0);
                });
            }
        }).start();
    }
    
    /**
     * Active ou désactive les boutons de lancement.
     */
    private void setButtonsEnabled(boolean enabled) {
        singleplayerButton.setDisable(!enabled);
        multiplayerButton.setDisable(!enabled);
        refreshButton.setDisable(!enabled);
    }
    
    /**
     * Affiche un message dans la zone de logs.
     */
    private void logMessage(String message) {
        logArea.appendText(message + "\n");
    }
    
    /**
     * Affiche une boîte de dialogue d'erreur.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        
        logMessage("ERREUR : " + message);
    }
}
