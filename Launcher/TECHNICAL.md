# Documentation Technique - Minecraft Educational Launcher

Cette documentation explique le fonctionnement interne du launcher pour les développeurs souhaitant contribuer ou comprendre l'architecture.

## 📋 Table des matières

1. [Architecture globale](#architecture-globale)
2. [Flux de lancement](#flux-de-lancement)
3. [APIs Mojang](#apis-mojang)
4. [Structure des fichiers](#structure-des-fichiers)
5. [Commande Java finale](#commande-java-finale)
6. [Dépannage](#dépannage)

---

## Architecture globale

### Pattern MVC

Le launcher suit le pattern **Model-View-Controller** :

```
┌─────────────────────────────────────────────┐
│                   VIEW (UI)                 │
│         MainController.java                 │
│         main.fxml                           │
└────────────────┬────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────┐
│              CONTROLLER                     │
│   • Gère les interactions utilisateur       │
│   • Orchestre le téléchargement             │
│   • Lance le jeu                            │
└────────────────┬────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────┐
│                MODEL (Business Logic)       │
│   • VersionManager  → Récupère versions     │
│   • DownloadManager → Télécharge fichiers   │
│   • LaunchManager   → Lance le jeu          │
└─────────────────────────────────────────────┘
```

### Packages

```
com.minecraft.launcher/
├── Main.java                    # Point d'entrée JavaFX
├── minecraft/                   # Logique Minecraft
│   ├── MinecraftVersion.java    # Modèle de version
│   ├── VersionManager.java      # API versions
│   ├── DownloadManager.java     # Téléchargements
│   ├── LaunchManager.java       # Lancement
│   └── FileValidator.java       # Validation (bonus)
├── ui/                          # Interface utilisateur
│   └── MainController.java      # Contrôleur JavaFX
└── utils/                       # Utilitaires
    ├── FileUtils.java           # Gestion fichiers
    └── JsonUtils.java           # Gestion JSON
```

---

## Flux de lancement

### Diagramme de séquence

```
Utilisateur → Interface → Controller → Managers → Mojang API
    |            |            |            |            |
    |  Clic      |            |            |            |
    |----------->|            |            |            |
    |            |  validate  |            |            |
    |            |----------->|            |            |
    |            |            | fetch      |            |
    |            |            | versions   |            |
    |            |            |----------->|  GET      |
    |            |            |            |---------->|
    |            |            |            |<----------|
    |            |            |<-----------|            |
    |            |            | download   |            |
    |            |            | JSON       |            |
    |            |            |----------->|  GET      |
    |            |            |            |---------->|
    |            |            |            |<----------|
    |            |            | download   |            |
    |            |            | JAR        |            |
    |            |            |----------->|  GET      |
    |            |            |            |---------->|
    |            |            |            |<----------|
    |            |            | download   |            |
    |            |            | libraries  |            |
    |            |            |----------->|  GET x N  |
    |            |            |            |---------->|
    |            |            |            |<----------|
    |            |            | download   |            |
    |            |            | assets     |            |
    |            |            |----------->|  GET x N  |
    |            |            |            |---------->|
    |            |            |            |<----------|
    |            |            | launch     |            |
    |            |            |----------->|            |
    |            |            |  ProcessBuilder         |
    |            |            |  starts Minecraft       |
    |  Jeu       |            |            |            |
    |  lancé     |            |            |            |
    |<-----------|            |            |            |
```

### Étapes détaillées

1. **Récupération des versions**
   - GET `https://launchermeta.mojang.com/mc/game/version_manifest.json`
   - Parse du JSON
   - Affichage dans la ComboBox

2. **Sélection et préparation**
   - Utilisateur choisit une version
   - Configure username et RAM
   - Clique sur "SINGLEPLAYER" ou "MULTIPLAYER"

3. **Téléchargement du JSON de version**
   - GET de l'URL spécifique à la version
   - Sauvegarde dans `.minecraft-launcher/versions/{id}/{id}.json`

4. **Téléchargement du client JAR**
   - Extraction de l'URL depuis `downloads.client.url`
   - Téléchargement du JAR
   - Sauvegarde dans `.minecraft-launcher/versions/{id}/{id}.jar`

5. **Téléchargement des libraries**
   - Parse de `libraries[]`
   - Pour chaque library :
     - Vérification des règles OS
     - Téléchargement si nécessaire
     - Extraction des natives si présents

6. **Téléchargement des assets**
   - Téléchargement de l'index des assets
   - Pour chaque objet :
     - Construction de l'URL via le hash
     - Téléchargement dans `assets/objects/{hash[0:2]}/{hash}`

7. **Lancement**
   - Construction du classpath complet
   - Configuration de `java.library.path`
   - Lancement via `ProcessBuilder`
   - Lecture des logs en temps réel

---

## APIs Mojang

### 1. Version Manifest

**Endpoint** : `https://launchermeta.mojang.com/mc/game/version_manifest.json`

**Structure** :
```json
{
  "latest": {
    "release": "1.20.4",
    "snapshot": "24w03a"
  },
  "versions": [
    {
      "id": "1.20.4",
      "type": "release",
      "url": "https://launchermeta.mojang.com/.../1.20.4.json",
      "releaseTime": "2023-12-07T12:00:00+00:00"
    }
  ]
}
```

### 2. Version JSON

**Endpoint** : URL extraite du manifest

**Structure** :
```json
{
  "id": "1.20.4",
  "mainClass": "net.minecraft.client.main.Main",
  "downloads": {
    "client": {
      "url": "https://piston-data.mojang.com/.../client.jar",
      "sha1": "abc123...",
      "size": 25000000
    }
  },
  "libraries": [
    {
      "name": "com.google.guava:guava:31.1-jre",
      "downloads": {
        "artifact": {
          "path": "com/google/guava/guava/31.1-jre/guava-31.1-jre.jar",
          "url": "https://libraries.minecraft.net/...",
          "sha1": "def456...",
          "size": 2000000
        }
      }
    }
  ],
  "assetIndex": {
    "id": "5",
    "url": "https://piston-meta.mojang.com/.../5.json",
    "sha1": "ghi789...",
    "size": 500000
  }
}
```

### 3. Libraries

**Base URL** : `https://libraries.minecraft.net/`

**Exemple** : `https://libraries.minecraft.net/com/google/guava/guava/31.1-jre/guava-31.1-jre.jar`

### 4. Assets

**Index** : URL depuis le version JSON

**Objets** : `https://resources.download.minecraft.net/{hash[0:2]}/{hash}`

**Exemple** : `https://resources.download.minecraft.net/ab/abc123def456...`

---

## Structure des fichiers

```
.minecraft-launcher/
├── versions/
│   └── 1.20.4/
│       ├── 1.20.4.json          # JSON de version
│       └── 1.20.4.jar           # Client Minecraft
├── libraries/
│   └── com/
│       └── google/
│           └── guava/
│               └── guava/
│                   └── 31.1-jre/
│                       └── guava-31.1-jre.jar
├── natives/
│   ├── lwjgl.dll                # Windows
│   ├── liblwjgl.dylib          # macOS
│   └── liblwjgl.so             # Linux
└── assets/
    ├── indexes/
    │   └── 5.json               # Index des assets
    └── objects/
        └── ab/
            └── abc123...        # Fichier asset
```

---

## Commande Java finale

### Exemple réel

```bash
java \
  -Xmx2048M \
  -Xms1024M \
  -XX:+UnlockExperimentalVMOptions \
  -XX:+UseG1GC \
  -XX:G1NewSizePercent=20 \
  -XX:G1ReservePercent=20 \
  -XX:MaxGCPauseMillis=50 \
  -XX:G1HeapRegionSize=32M \
  -Djava.library.path=/path/to/.minecraft-launcher/natives \
  -cp /path/to/lib1.jar:/path/to/lib2.jar:...:/path/to/client.jar \
  net.minecraft.client.main.Main \
  --username Player \
  --version 1.20.4 \
  --gameDir /path/to/.minecraft-launcher \
  --assetsDir /path/to/.minecraft-launcher/assets \
  --assetIndex 5 \
  --uuid 00000000-0000-0000-0000-000000000000 \
  --accessToken 0 \
  --userType legacy \
  --versionType release
```

### Détails des arguments

| Argument | Signification |
|----------|--------------|
| `-Xmx2048M` | RAM maximale (2 Go) |
| `-Xms1024M` | RAM initiale (1 Go) |
| `-XX:+UseG1GC` | Garbage Collector G1 (recommandé par Mojang) |
| `-Djava.library.path` | Chemin des bibliothèques natives (LWJGL) |
| `-cp` | Classpath : toutes les libraries + client.jar |
| `net.minecraft.client.main.Main` | Classe principale de Minecraft |
| `--username` | Nom du joueur |
| `--gameDir` | Dossier .minecraft |
| `--assetsDir` | Dossier des assets |
| `--assetIndex` | ID de l'index des assets |

---

## Dépannage

### Le jeu ne se lance pas

**1. Vérifier Java**
```bash
java -version
```
Doit être **Java 17+**

**2. Vérifier le classpath**
Logs : regarder la ligne `Classpath : X entrées`
- Si 0 ou 1 : les libraries n'ont pas été téléchargées

**3. Vérifier les natives**
```bash
ls ~/.minecraft-launcher/natives/
```
Doit contenir des fichiers `.dll` (Windows), `.dylib` (macOS) ou `.so` (Linux)

**4. Logs du jeu**
Chercher dans l'interface :
- `ClassNotFoundException` → Classpath incomplet
- `UnsatisfiedLinkError` → Natives manquants ou mauvais
- `FileNotFoundException` → Assets manquants

### Erreurs de téléchargement

**Timeout**
- Serveurs Mojang temporairement indisponibles
- Connexion internet lente
- Solution : Réessayer plus tard

**403 Forbidden**
- Rare, mais possible si trop de requêtes
- Solution : Attendre quelques minutes

**Disque plein**
- Minecraft + assets ≈ 1-2 Go
- Solution : Libérer de l'espace

### Performances faibles

**Augmenter la RAM**
- 2 Go minimum recommandé
- 4 Go pour du modding

**Java non optimisé**
- Utiliser Java 17+ avec G1GC
- Arguments JVM optimisés déjà inclus

---

## Références

- [Mojang API Documentation](https://wiki.vg/Mojang_API)
- [Minecraft Wiki - Launcher](https://minecraft.fandom.com/wiki/Launcher)
- [LWJGL](https://www.lwjgl.org/)
- [JavaFX Documentation](https://openjfx.io/)

---

**Dernière mise à jour** : 28 décembre 2025  
**Version du launcher** : 2.0.0
