# Structure complète du projet Minecraft Educational Launcher

```
Minecraft/
│
├── 📄 README.md                           # Documentation principale (utilisateurs)
├── 📄 PROJECT_SUMMARY.md                  # Résumé complet du projet
├── 📄 TECHNICAL.md                        # Documentation technique (développeurs)
├── 📄 CHANGELOG.md                        # Historique des versions
├── 📄 CONTRIBUTING.md                     # Guide de contribution
├── 📄 LICENSE                             # Licence MIT éducative
├── 📄 .gitignore                          # Fichiers à ignorer
│
├── 🔧 pom.xml                             # Configuration Maven
├── 🚀 launcher.sh                         # Script de lancement Unix/Mac
├── 🚀 launcher.bat                        # Script de lancement Windows
│
└── 📁 src/
    ├── 📁 main/
    │   ├── 📁 java/com/minecraft/launcher/
    │   │   │
    │   │   ├── 🎯 Main.java               # Point d'entrée (JavaFX Application)
    │   │   │   └── Lance l'application et charge l'interface
    │   │   │
    │   │   ├── 📁 minecraft/              # Package logique métier
    │   │   │   │
    │   │   │   ├── 📦 MinecraftVersion.java
    │   │   │   │   └── Modèle : représente une version Minecraft
    │   │   │   │
    │   │   │   ├── 🌐 VersionManager.java
    │   │   │   │   └── Récupère la liste des versions depuis l'API Mojang
    │   │   │   │
    │   │   │   ├── 📥 DownloadManager.java
    │   │   │   │   ├── Télécharge le JSON de version
    │   │   │   │   ├── Télécharge le JAR client
    │   │   │   │   ├── Télécharge les libraries (dépendances Java)
    │   │   │   │   ├── Télécharge et extrait les natives (OS-specific)
    │   │   │   │   └── Télécharge les assets (sons, textures)
    │   │   │   │
    │   │   │   ├── 🚀 LaunchManager.java
    │   │   │   │   ├── Construit la commande Java complète
    │   │   │   │   ├── Configure le classpath avec toutes les libraries
    │   │   │   │   ├── Configure java.library.path pour les natives
    │   │   │   │   ├── Lance Minecraft via ProcessBuilder
    │   │   │   │   └── Lit les logs du jeu en temps réel
    │   │   │   │
    │   │   │   └── ✅ FileValidator.java
    │   │   │       └── Valide l'intégrité des fichiers téléchargés
    │   │   │
    │   │   ├── 📁 ui/                     # Package interface utilisateur
    │   │   │   │
    │   │   │   └── 🎨 MainController.java
    │   │   │       ├── Contrôleur JavaFX principal
    │   │   │       ├── Gère les interactions utilisateur
    │   │   │       ├── Orchestre le téléchargement
    │   │   │       ├── Lance le jeu
    │   │   │       └── Affiche les logs
    │   │   │
    │   │   └── 📁 utils/                  # Package utilitaires
    │   │       │
    │   │       ├── 📁 FileUtils.java
    │   │       │   ├── Création de dossiers
    │   │       │   ├── Suppression récursive
    │   │       │   ├── Calcul de taille
    │   │       │   └── Formatage (Ko, Mo, Go)
    │   │       │
    │   │       └── 📋 JsonUtils.java
    │   │           ├── Lecture de fichiers JSON
    │   │           ├── Écriture de fichiers JSON
    │   │           ├── Parsing de chaînes JSON
    │   │           └── Pretty-printing
    │   │
    │   └── 📁 resources/
    │       ├── 📁 fxml/
    │       │   └── 🎨 main.fxml           # Interface graphique (JavaFX)
    │       │       ├── Design inspiré Minecraft (sombre)
    │       │       ├── ComboBox de sélection de version
    │       │       ├── TextField pour le username
    │       │       ├── Spinner pour la RAM
    │       │       ├── Boutons SINGLEPLAYER / MULTIPLAYER
    │       │       ├── ProgressBar
    │       │       └── TextArea pour les logs
    │       │
    │       └── 📄 simplelogger.properties  # Configuration des logs SLF4J
    │
    └── 📁 test/                           # Tests unitaires (à venir)
        └── java/
            └── ...
```

---

## 🎯 Flux de données

```
1. Utilisateur lance l'application
   └─> Main.java
       └─> Charge main.fxml
           └─> Instancie MainController

2. Chargement des versions
   └─> MainController.initialize()
       └─> VersionManager.fetchVersions()
           └─> GET https://launchermeta.mojang.com/mc/game/version_manifest.json
               └─> Parse JSON
                   └─> Remplit la ComboBox

3. Sélection et lancement
   └─> Utilisateur clique "SINGLEPLAYER"
       └─> MainController.launchGame()
           │
           ├─> DownloadManager.downloadVersionJson()
           │   └─> GET {version_url}
           │       └─> Sauvegarde versions/{id}/{id}.json
           │
           ├─> DownloadManager.downloadClientJar()
           │   └─> GET {client_url}
           │       └─> Sauvegarde versions/{id}/{id}.jar
           │
           ├─> DownloadManager.downloadLibraries()
           │   └─> Pour chaque library :
           │       ├─> Vérifie règles OS
           │       ├─> GET {library_url}
           │       └─> Extrait natives si nécessaire
           │
           ├─> DownloadManager.downloadAssets()
           │   └─> GET {asset_index_url}
           │       └─> Pour chaque asset :
           │           └─> GET https://resources.download.minecraft.net/{hash}
           │
           └─> LaunchManager.launchGame()
               ├─> Construit classpath
               ├─> Configure java.library.path
               └─> ProcessBuilder lance :
                   java -Xmx2G -cp {libraries}:{client.jar} \
                        -Djava.library.path={natives} \
                        net.minecraft.client.main.Main \
                        --username Player --version 1.20.4 ...
```

---

## 📊 Statistiques

| Catégorie | Valeur |
|-----------|--------|
| **Lignes de code Java** | ~2000 |
| **Classes Java** | 11 |
| **Fichiers FXML** | 1 |
| **Fichiers de documentation** | 5 (MD) |
| **Scripts** | 2 (.sh, .bat) |
| **Packages** | 3 (minecraft, ui, utils) |
| **Dépendances Maven** | 5 (JavaFX, Gson, SLF4J) |

---

## 🔑 Classes clés et responsabilités

| Classe | Responsabilité | LOC approximatif |
|--------|----------------|------------------|
| `Main.java` | Point d'entrée JavaFX | 80 |
| `MinecraftVersion.java` | Modèle de données | 70 |
| `VersionManager.java` | Récupération versions API | 150 |
| `DownloadManager.java` | Téléchargements (JSON, JAR, libraries, assets, natives) | 500 |
| `LaunchManager.java` | Lancement du jeu | 250 |
| `FileValidator.java` | Validation de fichiers | 200 |
| `MainController.java` | Contrôleur UI | 400 |
| `FileUtils.java` | Utilitaires fichiers | 150 |
| `JsonUtils.java` | Utilitaires JSON | 100 |

**Total** : ~1900 lignes de code

---

## 🎨 Architecture MVC

```
┌──────────────────────────────────────────┐
│              VIEW (Présentation)         │
│                                          │
│  main.fxml                               │
│  ├─ ComboBox<MinecraftVersion>          │
│  ├─ TextField (username)                │
│  ├─ Spinner<Integer> (RAM)              │
│  ├─ Button (SINGLEPLAYER)               │
│  ├─ Button (MULTIPLAYER)                │
│  ├─ ProgressBar                          │
│  └─ TextArea (logs)                      │
└──────────────┬───────────────────────────┘
               │
               │ binding
               │
┌──────────────▼───────────────────────────┐
│           CONTROLLER                     │
│                                          │
│  MainController.java                     │
│  ├─ initialize()                         │
│  ├─ loadVersions()                       │
│  ├─ onSingleplayerClicked()              │
│  ├─ onMultiplayerClicked()               │
│  └─ launchGame()                         │
└──────────────┬───────────────────────────┘
               │
               │ utilise
               │
┌──────────────▼───────────────────────────┐
│              MODEL (Métier)              │
│                                          │
│  minecraft/                              │
│  ├─ MinecraftVersion                     │
│  ├─ VersionManager                       │
│  ├─ DownloadManager                      │
│  ├─ LaunchManager                        │
│  └─ FileValidator                        │
│                                          │
│  utils/                                  │
│  ├─ FileUtils                            │
│  └─ JsonUtils                            │
└──────────────────────────────────────────┘
```

---

## 🌐 APIs utilisées

| API | URL | Usage |
|-----|-----|-------|
| **Version Manifest** | `launchermeta.mojang.com/mc/game/version_manifest.json` | Liste de toutes les versions |
| **Version JSON** | `launchermeta.mojang.com/.../1.20.4.json` | Détails d'une version |
| **Client JAR** | `piston-data.mojang.com/.../client.jar` | Exécutable Minecraft |
| **Libraries** | `libraries.minecraft.net/...` | Dépendances Java |
| **Assets** | `resources.download.minecraft.net/...` | Ressources du jeu |

---

## 📦 Fichiers générés à l'exécution

```
~/.minecraft-launcher/
├── versions/
│   └── 1.20.4/
│       ├── 1.20.4.json        (~50 KB)
│       └── 1.20.4.jar         (~25 MB)
├── libraries/                 (~100 MB)
│   └── com/google/.../*.jar
├── natives/                   (~10 MB)
│   └── *.dll / *.so / *.dylib
└── assets/                    (~500 MB)
    ├── indexes/
    │   └── 5.json
    └── objects/
        └── ab/abc123...

Total : ~600-700 MB par version
```

---

**Structure mise à jour : Décembre 2025**  
**Version du launcher : 2.0.0**
