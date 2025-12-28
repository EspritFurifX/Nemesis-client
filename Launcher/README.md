# Minecraft Educational Launcher

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://adoptium.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)
[![Status](https://img.shields.io/badge/Status-Production%20Ready-brightgreen.svg)]()

Launcher Minecraft Java Edition **100% GRATUIT, LÉGAL et ÉDUCATIF** destiné à l'apprentissage du développement.

> **🎮 Launcher complet et fonctionnel** - Minecraft démarre vraiment !  
> **📚 Projet éducatif** - Code commenté et documenté pour l'apprentissage  
> **⚖️ 100% légal** - Utilise uniquement les APIs officielles Mojang

## 📸 Aperçu

> **Note** : Screenshots à venir après installation

```
Interface principale avec :
✓ Sélection de version (releases + snapshots)
✓ Configuration utilisateur et RAM
✓ Boutons style Minecraft
✓ Logs en temps réel
✓ Barre de progression
```

## 🎯 Objectif

Ce projet est un launcher éducatif qui permet de :
- 🎮 **Comprendre** le fonctionnement d'un launcher de jeu
- 🌐 **Apprendre** à utiliser les APIs officielles Mojang/Microsoft
- 💻 **Pratiquer** le développement Java avec JavaFX
- 📦 **Découvrir** la gestion de téléchargements et de processus
- 🏗️ **Maîtriser** une architecture logicielle complète (MVC)

## ⚖️ Légalité & Avertissements Importants

### ✅ Ce launcher est 100% LÉGAL car :
- ✅ Utilise **uniquement les APIs officielles Mojang** : `https://launchermeta.mojang.com`
- ✅ **Ne contient aucun contenu cracké ou piraté**
- ✅ Télécharge les fichiers **depuis les serveurs officiels Mojang**
- ✅ Ne modifie, ne redistribue, ni ne bypass aucun système d'authentification
- ✅ Projet **éducatif et open source**

### ⚠️ Conditions d'utilisation :
- **Pour jouer en SOLO hors ligne** : Le launcher fonctionne sans compte
- **Pour jouer en MULTIJOUEUR** : Vous DEVEZ posséder un compte Minecraft OFFICIEL acheté sur [minecraft.net](https://www.minecraft.net)
- Le mode hors ligne est autorisé par Mojang pour l'apprentissage et le développement
- Ce projet est destiné à **l'éducation** et à comprendre le fonctionnement d'un launcher

### 📜 Clause de non-responsabilité :
Ce launcher ne remplace PAS le launcher officiel Minecraft. Il est fourni à des fins éducatives pour apprendre :
- La communication avec des APIs REST
- Le développement d'interfaces JavaFX
- La gestion de processus et classpath Java
- L'architecture logicielle d'un launcher de jeu

**Pour une expérience optimale, utilisez le launcher officiel Minecraft.**

## ✨ Fonctionnalités

### ✅ Version 2.0 (ACTUELLE) - Launcher 100% Fonctionnel
- ✅ Interface graphique inspirée du menu Minecraft
- ✅ Récupération de toutes les versions Minecraft (releases, snapshots)
- ✅ **Téléchargement automatique des libraries** (dépendances Java)
- ✅ **Téléchargement automatique des assets** (sons, textures, langues)
- ✅ **Support des natives** (bibliothèques OS-spécifiques : Windows, macOS, Linux)
- ✅ **Classpath complet** avec toutes les dépendances
- ✅ **Configuration java.library.path** pour LWJGL/OpenGL
- ✅ Lancement du jeu avec paramètres configurables
- ✅ Affichage des logs en temps réel
- ✅ Gestion de la RAM allouée
- ✅ Support du mode hors ligne
- ✅ **Le jeu démarre vraiment !** 🎮

### 🚀 Futures versions
- 🔜 Support de Forge et Fabric
- 🔜� Installation & Lancement

### Prérequis

| Logiciel | Version | Lien |
|----------|---------|------|
| **Java** | 17+ | [Télécharger](https://adoptium.net/) |
| **Maven** | 3.8+ | [Télécharger](https://maven.apache.org/download.cgi) |
| **Git** | Dernière | [Télécharger](https://git-scm.com/) |

> **Pour jouer en ligne** : Un compte Minecraft officiel est nécessaire ([acheter ici](https://minecraft.net))

### Installation rapide

```bash
# 1. Cloner le repository
git clone https://github.com/EspritFurifX/Nemesis-client.git
cd Nemesis-client/Launcher

# 2. Méthode automatique (recommandée)
./launcher.sh        # Linux/macOS
launcher.bat         # Windows

# 3. OU méthode Maven
mvn clean package    # Compiler
mvn javafx:run       # Lancer
```

### Première utilisation

1. **Lancer le launcher** (via script ou Maven)
2. **Attendre** le chargement des versions (~5 secondes)
3. **Sélectionner** une version Minecraft
4. **Configurer** votre nom d'utilisateur et la RAM
5. **Cliquer** sur "SINGLEPLAYER" ou "MULTIPLAYER"
6. **Patienter** pendant le téléchargement (~1-2 Go la première fois)
7. **Jouer** ! 🎮loner le projet
git clone <votre-repo>
cd Minecraft

# Compiler le projet
mvn clean package

# Lancer l'application
mvn javafx:run
```

### Exécution du JAR
```bash
java -jar target/minecraft-educational-launcher-1.0.0-SNAPSHOT.jar
```

## 🏗️ Architecture du projet

```
src/
├── main/
│   ├── java/com/minecraft/launcher/
│   │   ├── Main.java                      # Point d'entrée
│   │   ├── minecraft/
│   │   │   ├── MinecraftVersion.java      # Modèle de version
│   │   │   ├── VersionManager.java        # API Mojang
│   │   │   ├── DownloadManager.java       # Téléchargements
│   │   │   └── LaunchManager.java         # Lancement du jeu
│   │   ├── ui/
│   │   │   └── MainController.java        # Contrôleur JavaFX
│   │   └── utils/
│   │       ├── FileUtils.java             # Utilitaires fichiers
│   │       └── JsonUtils.java             # Utilitaires JSON
│   └── resources/
│       ├── fxml/
│       │   └── main.fxml                  # Interface FXML
│       └── simplelogger.properties        # Configuration logs
└── test/                                   # Tests unitaires
```

## 🚀 Utilisation

1. **Lancer l'application**
   ```bash
   mvn javafx:run
   ```

2. **Sélectionner u vs Authentification
Le launcher fonctionne en mode hors ligne par défaut :
- En **solo** : Fonctionne directement sans compte
- En **multijoueur** : 
  - **Serveurs en mode online** : Nécessite un compte Minecraft OFFICIEL (authentification Microsoft à venir)
  - **Serveurs en mode offline** : Fonctionne avec n'importe quel pseudo
  
⚠️ **Important** : Jouer sur des serveurs en ligne sans compte Minecraft officiel est **impossible et illégal**. Ce launcher ne contourne aucun système d'authentification.
3. **Configurer le lancement**
   - Nom d'utilisateur (mode hors ligne)
   - RAM allouée (recommandé : 2048 Mo minimum)

4. **Lancer le jeu**
   - Cliquez sur "SINGLEPLAYER" ou "MULTIPLAYER"
   - Le launcher télécharge les fichiers nécessaires
   - Le jeu se lance automatiquement

## 📝 Notes importantes

### Mode hors ligne
Le launcher fonctionne en mode hors ligne par défaut. Pour jouer :
- En **solo** : fonctionne directement
- En **multijoueur** : nécessite un compte Minecraft légitime et l'authentification Microsoft (à venir)

### Dossier .minecraft
Le launcher crée un dossier `.minecraft-launcher` dans votre répertoire utilisateur :
- **Windows** : `C:\Users\VotreNom\.minecraft-launcher`
- **macOS** : `/Users/VotreNom/.minecraft-launcher`
- **Linux** : `/home/VotreNom/.minecraft-launcher`

### Versions anciennes
Les très anciennes versions (beta, alpha) peuvent nécessiter :
- Des versions spécifiques de Java
- Des paramètres de lancement différents
- Des libraries particulières

## 🐛 Dépannage

### Le jeu ne se lance pas
1. Vérifiez que Java 17+ est installé : `java -version`
2. Vérifiez les logs dans l'interface
3. Augmentez la RAM allouée
4. Vérifiez votre connexion internet (pour le téléchargement)

### Erreur de téléchargement
- Vérifiez votre connexion internet
- Les serveurs Mojang peuvent être temporairement indisponibles
- Essayez de relancer le téléchargement

### Erreur "ClassNotFoundException"
- Assurez-vous d'avoir compilé avec Maven
- Vérifiez que toutes les dépendances sont présentes

## 🎓 Aspects pédagogiques

Ce projet illustre :

### APIs REST
- Communication avec l'API Mojang
- Parsing de réponses JSON
- Gestion des erreurs réseau

### JavaFX
- Architecture MVC
- FXML et binding
- Threads et Platform.runLater()
- Composants personnalisés

### Gestion de processus
- ProcessBuilder
- Lecture de flux (streams)
- Arguments JVM et classpath

### Téléchargements
- HttpURLConnection
- Gestion de fichiers (NIO)
- Barre de progression

## 📚 Ressources & Documentation

### Documentation du projet

| Document | Description |
|----------|-------------|
| [README.md](README.md) | Documentation utilisateur (vous êtes ici) |
| [TECHNICAL.md](TECHNICAL.md) | Documentation technique détaillée |
| [STRUCTURE.md](STRUCTURE.md) | Architecture et flux de données |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Guide de contribution |
| [CHANGELOG.md](CHANGELOG.md) | Historique des versions |

### Ressources externes

- 📖 [Documentation API Mojang](https://wiki.vg/Mojang_API)
- 🎮 [Wiki Minecraft](https://minecraft.fandom.com)
- 🖼️ [JavaFX Documentation](https://openjfx.io/)
- 🔨 [Maven Getting Started](https://maven.apache.org/guides/getting-started/)

## ⭐ Remerciements

Merci à tous les contributeurs et à la communauté Minecraft pour les ressources !

Si ce projet vous a aidé dans votre apprentissage, n'hésitez pas à :
- ⭐ **Star** le repository
- 🍴 **Fork** pour vos propres expérimentations
- 🐛 **Reporter** des bugs ou proposer des améliorations
- 💬 **Partager** avec d'autres développeurs Java

## 🤝 Contribution

Ce projet est éducatif. Les contributions sont bienvenues :
1. Fork le projet
2. Créez une branche (`git checkout -b feature/amelioration`)
3. 🎓 Ce que ce projet vous apprend

Ce launcher est un **excellent projet pédagogique** car il couvre :

### 🏗️ Architecture logicielle
- Pattern MVC (Model-View-Controller)
- Séparation des responsabilités
- Organisation modulaire du code

### 🌐 Réseau & APIs
- Requêtes HTTP (GET)
- Parsing de JSON complexes (Gson)
- Gestion des erreurs réseau
- Téléchargement de fichiers avec progression

### 💻 Java avancé
- ProcessBuilder et gestion de processus
- Classpath et natives libraries
- Gestion de threads (concurrence)
- I/O avec NIO (Files, Paths)
- Extraction de fichiers ZIP

### 🖼️ Interface graphique
- JavaFX et FXML
- Composants UI (ComboBox, Spinner, TextArea)
- Threading avec Platform.runLater()
- Responsive UI (pas de freeze)

### 🎮 Reverse Engineering propre
- Lecture de la documentation Mojang
- Compréhension du format de manifests
- Analyse de la structure d'un jeu

## ⚠️ Avertissements Finaux

### Ce launcher est un projet éducatif
- ✅ **Présentable en portfolio** pour montrer vos compétences
- ✅ Excellent pour **apprendre Java** et l'architecture logicielle
- ✅ Permet de **comprendre comment fonctionne un launcher** de jeu
- ⚠️ **N'est pas destiné à remplacer** le launcher officiel

### Pour jouer à Minecraft légalement :
1. **Achetez Minecraft** sur [minecraft.net](https://www.minecraft.net) (≈30€)
2. **Utilisez le launcher officiel** pour l'expérience complète
3. Ce projet est pour **comprendre**, pas pour contourner

## 📄 Licence

Ce projet est fourni **à des fins éducatives uniquement** sous licence MIT.

**Minecraft™** est une marque déposée de **Mojang AB / Microsoft Corporation**.  
Ce projet n'est pas affilié, approuvé, sponsorisé ou spécifiquement approuvé par Mojang AB ou Microsoft Corporation.

Tous les téléchargements proviennent des serveurs officiels Mojang.  
Aucun fichier propriétaire n'est redistribué dans ce repository.

---

**Fait avec ❤️ pour apprendre le développement Java**  
**Version 2.0** - Launcher 100% fonctionnel (Décembre 2025)soft.

---

**Fait avec ❤️ pour apprendre le développement Java**
