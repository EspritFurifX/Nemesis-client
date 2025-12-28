package com.minecraft.launcher.ui;

import com.minecraft.launcher.minecraft.*;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur JavaFX pour l'interface principale du launcher.
 * Structure: Sidebar versions | Zone centrale logs | Bottom actions
 */
public class MainController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    
    // Composants FXML - Nouvelle structure
    @FXML private Label userLabel;
    @FXML private TextField searchField;
    @FXML private CheckBox releasesOnlyCheckbox;
    @FXML private ListView<MinecraftVersion> versionListView;
    @FXML private Slider ramSlider;
    @FXML private Label ramLabel;
    @FXML private Label versionInfoLabel;
    @FXML private VBox logSection;
    @FXML private TextArea logArea;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;
    @FXML private Label downloadSpeedLabel;
    @FXML private Button singleplayerButton;
    @FXML private Button multiplayerButton;
    @FXML private Button launchButton;
    
    // Gestionnaires
    private VersionManager versionManager;
    private DownloadManager downloadManager;
    private LaunchManager launchManager;
    
    // Version sélectionnée
    private MinecraftVersion selectedVersion = null;
    
    // Compte Microsoft authentifié
    private LoginController.MinecraftAccount authenticatedAccount = null;
    
    /**
     * Définir le compte authentifié depuis l'écran de connexion
     */
    public void setAuthenticatedAccount(LoginController.MinecraftAccount account) {
        this.authenticatedAccount = account;
        if (account != null) {
            userLabel.setText(account.getUsername());
            logMessage("✅ Connecté en tant que " + account.getUsername());
        }
    }
    
    /**
     * Méthode d'initialisation appelée automatiquement par JavaFX.
     */
    @FXML
    public void initialize() {
        LOGGER.info("Initialisation du contrôleur...");
        
        // 1. Initialisation des gestionnaires
        Path minecraftDir = Paths.get(System.getProperty("user.home"), ".minecraft-launcher");
        downloadManager = new DownloadManager(minecraftDir);
        launchManager = new LaunchManager(downloadManager);
        versionManager = new VersionManager();
        
        // 2. Configuration du slider RAM
        ramSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int ram = newVal.intValue();
            ramLabel.setText(ram + " Mo");
        });
        ramSlider.setValue(2048);
        
        // 3. Configuration de la ListView des versions
        versionListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MinecraftVersion item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getId());
                    setStyle("-fx-background-color: " + (item.getType().equals("release") ? "#1a1d24" : "#14161e") + 
                             "; -fx-text-fill: " + (item.getType().equals("release") ? "#00d9ff" : "#7a8090") + 
                             "; -fx-padding: 10; -fx-font-size: 12; -fx-font-weight: " + 
                             (item.getType().equals("release") ? "bold" : "normal") + ";");
                }
            }
        });
        
        // Sélection d'une version dans la liste
        versionListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedVersion = newVal;
                versionInfoLabel.setText("Version: " + newVal.getId() + " (" + newVal.getType() + ")");
                logMessage("✓ Version sélectionnée: " + newVal.getId());
            }
        });
        
        // 4. Configuration du champ de recherche
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterVersions(newVal);
        });
        
        // 5. Username par défaut
        String username = "Joueur_" + System.currentTimeMillis() % 10000;
        userLabel.setText(username);
        
        // 6. Chargement initial des versions
        loadVersions();
        
        logMessage("=== NÉMÉSIS CLIENT v2.0 ===");
        logMessage("Launcher initialisé");
        logMessage("Prêt à télécharger et lancer Minecraft");
    }
    
    /**
     * Charge la liste des versions Minecraft
     */
    private void loadVersions() {
        LOGGER.info("Chargement des versions...");
        statusLabel.setText("Chargement des versions...");
        progressBar.setProgress(-1);
        
        new Thread(() -> {
            try {
                // Télécharger les versions depuis l'API Mojang
                boolean success = versionManager.fetchVersions();
                
                if (!success) {
                    Platform.runLater(() -> {
                        statusLabel.setText("❌ Erreur: impossible de charger les versions");
                        progressBar.setProgress(0);
                        logMessage("❌ Impossible de charger les versions depuis l'API Mojang");
                        logMessage("Vérifiez votre connexion internet");
                    });
                    return;
                }
                
                List<MinecraftVersion> versions;
                
                if (releasesOnlyCheckbox.isSelected()) {
                    versions = versionManager.getReleases();
                } else {
                    versions = versionManager.getVersions();
                }
                
                Platform.runLater(() -> {
                    versionListView.setItems(FXCollections.observableArrayList(versions));
                    statusLabel.setText("Prêt – " + versions.size() + " versions disponibles");
                    progressBar.setProgress(0);
                    logMessage("✓ " + versions.size() + " versions chargées");
                    
                    // Sélectionner la dernière release
                    if (!versions.isEmpty()) {
                        MinecraftVersion latest = versionManager.getLatestRelease();
                        if (latest != null && versions.contains(latest)) {
                            versionListView.getSelectionModel().select(latest);
                        } else {
                            versionListView.getSelectionModel().selectFirst();
                        }
                    }
                });
                
            } catch (Exception e) {
                LOGGER.error("Erreur chargement versions", e);
                Platform.runLater(() -> {
                    statusLabel.setText("Erreur chargement versions");
                    progressBar.setProgress(0);
                    logMessage("❌ Erreur: " + e.getMessage());
                });
            }
        }).start();
    }
    
    /**
     * Filtre les versions selon la recherche
     */
    private void filterVersions(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            updateVersionList();
            return;
        }
        
        String search = searchText.toLowerCase();
        List<MinecraftVersion> allVersions;
        
        if (releasesOnlyCheckbox.isSelected()) {
            allVersions = versionManager.getReleases();
        } else {
            allVersions = versionManager.getVersions();
        }
        
        List<MinecraftVersion> filtered = allVersions.stream()
            .filter(v -> v.getId().toLowerCase().contains(search))
            .collect(Collectors.toList());
        
        versionListView.setItems(FXCollections.observableArrayList(filtered));
        logMessage("🔍 " + filtered.size() + " versions trouvées");
    }
    
    /**
     * Met à jour la liste des versions
     */
    private void updateVersionList() {
        statusLabel.setText("Mise à jour de la liste...");
        progressBar.setProgress(-1);
        
        new Thread(() -> {
            List<MinecraftVersion> versions;
            
            if (releasesOnlyCheckbox.isSelected()) {
                versions = versionManager.getReleases();
            } else {
                versions = versionManager.getVersions();
            }
            
            Platform.runLater(() -> {
                versionListView.setItems(FXCollections.observableArrayList(versions));
                statusLabel.setText("Prêt – " + versions.size() + " versions disponibles");
                progressBar.setProgress(0);
                logMessage("✓ Affichage de " + versions.size() + " versions");
            });
        }).start();
    }
    
    /**
     * Handler pour le checkbox "Releases uniquement"
     */
    @FXML
    private void onReleasesOnlyChanged() {
        updateVersionList();
        String mode = releasesOnlyCheckbox.isSelected() ? "releases uniquement" : "toutes versions";
        logMessage("🔄 Mode: " + mode);
    }
    
    /**
     * Handler pour le bouton LANCER
     */
    @FXML
    private void onLaunchClicked() {
        if (selectedVersion == null) {
            logMessage("❌ Aucune version sélectionnée");
            return;
        }
        
        launchButton.setDisable(true);
        String versionId = selectedVersion.getId();
        int ramMb = (int) ramSlider.getValue();
        String username = userLabel.getText();
        
        logMessage("===========================================");
        logMessage("▶ LANCEMENT DE MINECRAFT");
        logMessage("Version: " + versionId);
        logMessage("RAM: " + ramMb + " Mo");
        logMessage("Joueur: " + username);
        logMessage("===========================================");
        
        statusLabel.setText("Préparation du lancement...");
        progressBar.setProgress(0);
        
        new Thread(() -> launchGame(versionId, ramMb, username)).start();
    }
    
    /**
     * Handlers pour les boutons SINGLEPLAYER, MULTIPLAYER, OPTIONS
     */
    @FXML
    private void onSingleplayerClicked() {
        onLaunchClicked();
    }
    
    @FXML
    private void onMultiplayerClicked() {
        logMessage("🌐 Mode multijoueur - Connexion à un serveur après lancement");
        onLaunchClicked();
    }
    
    @FXML
    private void onOptionsClicked() {
        logMessage("⚙️ Options - Configuration disponible après premier lancement");
    }
    
    /**
     * Lance le jeu Minecraft avec les paramètres sélectionnés.
     */
    private void launchGame(String versionId, int ramMb, String username) {
        try {
            // 1. Téléchargement du JSON de version
            Platform.runLater(() -> {
                statusLabel.setText("Téléchargement des informations de version...");
                progressBar.setProgress(-1);
            });
            
            MinecraftVersion version = selectedVersion;
            JsonObject versionJson = downloadManager.downloadVersionJson(version);
            if (versionJson == null) {
                Platform.runLater(() -> {
                    statusLabel.setText("❌ Erreur: informations de version");
                    progressBar.setProgress(0);
                    logMessage("❌ Impossible de télécharger les informations de version");
                    launchButton.setDisable(false);
                });
                return;
            }
            
            Platform.runLater(() -> logMessage("✓ Informations de version téléchargées"));
            
            // 2. Téléchargement du JAR client
            Platform.runLater(() -> {
                statusLabel.setText("Téléchargement du client Minecraft...");
                progressBar.setProgress(0.2);
            });
            
            boolean jarDownloaded = downloadManager.downloadClientJar(version, versionJson);
            if (!jarDownloaded) {
                Platform.runLater(() -> {
                    statusLabel.setText("❌ Erreur: client Minecraft");
                    progressBar.setProgress(0);
                    logMessage("❌ Impossible de télécharger le client");
                    launchButton.setDisable(false);
                });
                return;
            }
            
            Platform.runLater(() -> logMessage("✓ Client Minecraft téléchargé"));
            
            // 3. Téléchargement des libraries
            Platform.runLater(() -> {
                statusLabel.setText("Téléchargement des libraries Java...");
                progressBar.setProgress(0.4);
                logMessage("Téléchargement des dépendances Java...");
            });
            
            List<Path> libraryPaths = downloadManager.downloadLibraries(versionJson);
            if (libraryPaths.isEmpty()) {
                Platform.runLater(() -> {
                    logMessage("⚠ Aucune library téléchargée");
                });
            } else {
                Platform.runLater(() -> logMessage("✓ " + libraryPaths.size() + " libraries téléchargées"));
            }
            
            // 4. Téléchargement des assets
            Platform.runLater(() -> {
                statusLabel.setText("Téléchargement des ressources (sons, textures)...");
                progressBar.setProgress(0.6);
                logMessage("Téléchargement des assets (peut prendre du temps)...");
            });
            
            boolean assetsDownloaded = downloadManager.downloadAssets(versionJson);
            if (assetsDownloaded) {
                Platform.runLater(() -> logMessage("✓ Assets téléchargés"));
            } else {
                Platform.runLater(() -> logMessage("⚠ Erreur lors du téléchargement des assets"));
            }
            
            // 5. Lancement du jeu
            Platform.runLater(() -> {
                statusLabel.setText("Lancement de Minecraft...");
                progressBar.setProgress(0.8);
                logMessage("Construction de la commande de lancement...");
            });
            
            boolean launched = launchManager.launchGame(
                version,
                versionJson,
                libraryPaths,
                username,
                ramMb
            );
            
            Platform.runLater(() -> {
                if (launched) {
                    statusLabel.setText("🎮 Minecraft lancé avec succès !");
                    progressBar.setProgress(1.0);
                    logMessage("✓ Minecraft lancé avec succès");
                    logMessage("===========================================");
                    logMessage("Vous pouvez consulter les logs ci-dessus");
                    logMessage("Bon jeu !");
                } else {
                    statusLabel.setText("❌ Erreur lors du lancement");
                    progressBar.setProgress(0);
                    logMessage("❌ Erreur: Impossible de lancer le jeu");
                }
                
                launchButton.setDisable(false);
            });
            
        } catch (Exception e) {
            LOGGER.error("Erreur lors du lancement", e);
            Platform.runLater(() -> {
                statusLabel.setText("❌ Erreur: " + e.getMessage());
                progressBar.setProgress(0);
                logMessage("❌ Exception: " + e.getMessage());
                launchButton.setDisable(false);
            });
        }
    }
    
    /**
     * Affiche un message dans la zone de logs.
     */
    private void logMessage(String message) {
        String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
        logArea.appendText("[" + timestamp + "] " + message + "\n");
    }
}
