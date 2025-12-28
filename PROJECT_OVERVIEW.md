# 🎮 NEMESIS LAUNCHER - Documentation Complète du Projet

**Date:** 28 décembre 2025  
**Version:** 2.2.1  
**Status:** ✅ Fonctionnel et Prêt pour Production

---

## 📋 Table des Matières

1. [Vue d'Ensemble](#vue-densemble)
2. [Architecture du Projet](#architecture-du-projet)
3. [Fonctionnalités Principales](#fonctionnalités-principales)
4. [Système d'Authentification](#système-dauthentification)
5. [Technologies Utilisées](#technologies-utilisées)
6. [Installation et Démarrage](#installation-et-démarrage)
7. [Configuration](#configuration)
8. [Distribution Minecraft](#distribution-minecraft)
9. [Sécurité et Conformité](#sécurité-et-conformité)
10. [Structure des Fichiers](#structure-des-fichiers)
11. [Workflows et Processus](#workflows-et-processus)
12. [Guide Utilisateur](#guide-utilisateur)
13. [Développement](#développement)

---

## 🎯 Vue d'Ensemble

**NEMESIS LAUNCHER** est un launcher Minecraft Java Edition complet et professionnel construit avec **Electron** et **Node.js**. Il offre une expérience moderne et sécurisée pour lancer Minecraft avec authentification Microsoft officielle.

### Ce qui Rend Nemesis Launcher Unique

- ✅ **Authentification Microsoft 100% Officielle** (OAuth 2.0)
- ✅ **Multi-versions Minecraft** (1.12.2 à 1.20.1)
- ✅ **Support Forge Intégré** avec téléchargement automatique
- ✅ **Interface Moderne** avec animations et design professionnel
- ✅ **Auto-Update** pour le launcher et les fichiers du jeu
- ✅ **Multi-comptes** avec changement facile
- ✅ **Gestion Java Automatique** - détection et téléchargement
- ✅ **Discord Rich Presence** intégré
- ✅ **100% Légal** - respect total des règles Mojang/Microsoft

---

## 🏗 Architecture du Projet

### Structure du Monorepo

```
Nemesis-Launcher/
│
├── nemesis-client/              # 🚀 Application Launcher (Electron)
│   ├── app/                     # Code source du launcher
│   │   ├── assets/              # Ressources (CSS, JS, images)
│   │   │   ├── css/            # Styles
│   │   │   ├── js/             # Logique métier JavaScript
│   │   │   │   ├── authmanager.js       # Gestion authentification
│   │   │   │   ├── configmanager.js     # Configuration
│   │   │   │   ├── distromanager.js     # Distribution Minecraft
│   │   │   │   ├── processbuilder.js    # Lancement du jeu
│   │   │   │   └── scripts/             # Scripts UI
│   │   │   └── images/         # Assets visuels
│   │   ├── *.ejs                # Templates HTML (EJS)
│   │   └── lang/                # Fichiers de langue
│   │
│   ├── index.js                 # Point d'entrée Electron
│   ├── package.json             # Dépendances Node.js
│   ├── distribution.json        # Configuration versions Minecraft
│   ├── electron-builder.yml     # Configuration build
│   └── build/                   # Assets de build
│
├── website/                     # 🌐 Site Web (PHP/Azuriom)
│   └── public/
│
└── Docs/                        # 📚 Documentation
    ├── SUCCES.md
    ├── AUTHENTICATION_COMPLETE.md
    └── README.md
```

### Technologies Stack

| Couche | Technologies |
|--------|-------------|
| **Frontend** | Electron, HTML5, CSS3, JavaScript ES6+ |
| **Backend** | Node.js 20.x |
| **Authentification** | Microsoft OAuth 2.0, helios-core |
| **UI Framework** | jQuery, EJS Templates |
| **Build** | electron-builder |
| **Distribution** | helios-distribution-types |
| **Discord** | discord-rpc-patch |

---

## 🎯 Fonctionnalités Principales

### 1. 🔐 Authentification Microsoft Officielle

**Flow Complet OAuth 2.0:**

```
Utilisateur clique "Se connecter avec Microsoft"
    ↓
Ouverture navigateur → Page de connexion Microsoft
    ↓
Authentification utilisateur (email + mot de passe)
    ↓
Microsoft retourne le code d'autorisation
    ↓
Launcher échange le code contre un access token
    ↓
Authentification Xbox Live
    ↓
Génération XSTS Token
    ↓
Authentification Minecraft Services
    ↓
Vérification Ownership Minecraft (✅ OBLIGATOIRE)
    ↓
Récupération du profil (UUID + Username)
    ↓
Stockage sécurisé du compte
    ↓
✅ Prêt à lancer Minecraft
```

**Sécurité:**
- ❌ Aucun mode offline
- ❌ Aucun compte crack accepté
- ✅ Tokens stockés localement (chiffrés)
- ✅ Aucun mot de passe sauvegardé
- ✅ Communication HTTPS uniquement
- ✅ Validation à chaque lancement

### 2. 🎮 Multi-Versions Minecraft

**5 Versions Configurées:**

| Version | Forge | Java Requis | RAM Recommandée | Status |
|---------|-------|-------------|-----------------|--------|
| 1.20.1 | 47.2.0 | Java 17+ | 4 GB | ⭐ Principal |
| 1.19.4 | 45.2.0 | Java 17+ | 4 GB | ✅ |
| 1.18.2 | 40.2.0 | Java 17+ | 3 GB | ✅ |
| 1.16.5 | 36.2.39 | Java 8-16 | 3 GB | ✅ |
| 1.12.2 | 14.23.5.2859 | Java 8 | 2.5 GB | ✅ Legacy |

**Système de Distribution:**
- Fichier `distribution.json` hébergé sur GitHub
- Téléchargement automatique des fichiers Minecraft
- Vérification d'intégrité (checksums)
- Support des mods serveur (auto-download)

### 3. ☕ Gestion Java Automatique

```javascript
// Le launcher gère Java automatiquement:
- Détection de la version Java installée
- Téléchargement automatique si incompatible
- Support Java 8, 11, 17, 21
- Gestion multi-version (une JVM par version MC)
```

### 4. 🎨 Interface Utilisateur

**Écrans disponibles:**
1. **Welcome** - Premier lancement
2. **Login** - Authentification
3. **Landing** - Écran principal
4. **Settings** - Configuration
5. **Overlay** - Informations en superposition

**Features UI:**
- Animations fluides
- Thème personnalisable
- News feed intégré
- Statut des serveurs en temps réel
- Gestion multi-comptes visuelle
- Barre de progression du téléchargement

### 5. 🔄 Auto-Update

**Système de mise à jour:**
```javascript
// electron-updater
- Vérification automatique au démarrage
- Téléchargement en arrière-plan
- Installation au prochain redémarrage
- Support des pre-release
```

### 6. 🎧 Discord Rich Presence

**Intégration Discord:**
- Affiche "Joue à Minecraft via Nemesis Launcher"
- Affiche le serveur actif
- Affiche le temps de jeu
- Image personnalisée

---

## 🔐 Système d'Authentification

### Modules Impliqués

**1. authmanager.js**
```javascript
// Gestion complète de l'authentification
- addMicrosoftAccount()  // Ajoute un compte Microsoft
- validateSelected()      // Valide le compte sélectionné
- removeMojangAccount()   // Suppression de compte
- getAccountList()        // Liste des comptes
```

**2. helios-core/microsoft**
```javascript
// Module Microsoft Auth
const { MicrosoftAuth } = require('helios-core/microsoft')

const msAuth = new MicrosoftAuth(
    AZURE_CLIENT_ID,
    'http://localhost:1337', // Redirect URI
    ConfigManager.getDataDirectory(),
    ConfigManager.getDataDirectory()
)

// Flow d'authentification
await msAuth.login()
```

### Azure App Configuration

**Client ID:** `59c2bb85-71b1-4f0c-9bd1-e5dfdcabf88f`
**Redirect URI:** `http://localhost:1337`

### Stockage des Comptes

```javascript
// Structure de compte (config.json)
{
    "accounts": {
        "UUID": {
            "type": "microsoft",
            "accessToken": "encrypted_token",
            "username": "PlayerName",
            "uuid": "player-uuid",
            "displayName": "PlayerName",
            "lastLogin": timestamp
        }
    },
    "selectedAccount": {
        "uuid": "current-account-uuid"
    }
}
```

### Workflow Utilisateur

**Première Connexion:**
1. Lancer le launcher
2. Clic sur "Se connecter avec Microsoft"
3. Navigateur s'ouvre → login.live.com
4. Entrer identifiants Microsoft
5. Accepter les permissions
6. Redirection automatique vers le launcher
7. ✅ Compte ajouté et sélectionné

**Connexions Suivantes:**
1. Double-clic sur le compte dans la liste
2. Le launcher valide le token
3. Si expiré → refresh automatique
4. ✅ Prêt à jouer

---

## ⚙️ Configuration

### Fichiers de Configuration

**1. distribution.json**
```json
{
    "version": "1.0.0",
    "discord": {
        "clientId": "YOUR_DISCORD_CLIENT_ID",
        "smallImageText": "Nemesis Launcher",
        "smallImageKey": "nemesis_logo"
    },
    "servers": [
        {
            "id": "nemesis-1.20.1",
            "name": "Nemesis Minecraft 1.20.1",
            "description": "Serveur principal Nemesis",
            "icon": "URL_ICON",
            "version": "1.20.1",
            "address": "play.nemesis.fr",
            "minecraftVersion": "1.20.1",
            "discord": {
                "shortId": "Nemesis 1.20.1",
                "largeImageText": "Nemesis Minecraft",
                "largeImageKey": "server_icon"
            },
            "mainServer": true,
            "autoconnect": false,
            "modules": [...]
        }
    ]
}
```

**2. package.json**
```json
{
    "name": "nemesis-client",
    "version": "2.2.1",
    "productName": "Nemesis Launcher",
    "main": "index.js",
    "scripts": {
        "start": "electron .",
        "dist": "electron-builder build"
    }
}
```

**3. electron-builder.yml**
```yaml
appId: com.nemesis.launcher
productName: Nemesis Launcher
artifactName: ${productName}-setup-${version}.${ext}

directories:
  buildResources: build
  output: dist

files:
  - "!{dist,.gitignore,.vscode,docs,dev-app-update.yml,.github}"

win:
  target: nsis

mac:
  target: dmg
  category: public.app-category.games

linux:
  target: AppImage
  category: Game
```

---

## 🎮 Distribution Minecraft

### Comment ça Fonctionne

**1. Fichier Distribution**
```
distribution.json hébergé sur GitHub
    ↓
Launcher télécharge au démarrage
    ↓
Parse les serveurs/versions disponibles
    ↓
Affichage dans l'UI
```

**2. Modules**
```json
{
    "modules": [
        {
            "id": "net.minecraftforge:forge:1.20.1-47.2.0",
            "name": "Minecraft Forge 1.20.1",
            "type": "ForgeHosted",
            "artifact": {
                "size": 123456,
                "MD5": "checksum",
                "url": "forge-installer-url"
            }
        },
        {
            "id": "com.nemesis:mod:optifine",
            "name": "OptiFine",
            "type": "ForgeMod",
            "artifact": {
                "size": 789012,
                "MD5": "checksum",
                "url": "mod-download-url"
            },
            "required": {
                "value": false
            }
        }
    ]
}
```

**3. Téléchargement**
```javascript
// helios-core gère:
- Téléchargement des assets Minecraft
- Téléchargement des libraries
- Téléchargement de Forge
- Téléchargement des mods
- Vérification MD5
- Extraction et installation
```

---

## 🔒 Sécurité et Conformité

### Règles Mojang/Microsoft Respectées

✅ **1. Utilisation des APIs Officielles**
- Microsoft OAuth 2.0
- Xbox Live Authentication
- Minecraft Services
- Profile API

✅ **2. Pas de Bypass DRM**
- Vérification ownership obligatoire
- Aucun crack
- Aucun compte offline

✅ **3. Respect des Droits**
- Téléchargement depuis serveurs Mojang officiels
- Pas de redistribution des assets Minecraft
- Pas de modification du client

✅ **4. Sécurité Utilisateur**
- Aucun mot de passe stocké
- Tokens chiffrés localement
- Communication HTTPS uniquement
- Validation des certificats

### Conformité RGPD

- ✅ Données stockées localement uniquement
- ✅ Pas de tracking utilisateur
- ✅ Pas d'analytics sans consentement
- ✅ Suppression facile des comptes

---

## 📂 Structure des Fichiers

### Fichiers Clés

```
nemesis-client/
│
├── index.js                     # Point d'entrée Electron
│   - Initialisation de l'app
│   - Gestion des fenêtres
│   - IPC handlers
│   - Auto-updater
│
├── app/
│   ├── assets/js/
│   │   ├── authmanager.js       # ⭐ Authentification
│   │   │   - addMicrosoftAccount()
│   │   │   - validateSelected()
│   │   │   - Microsoft OAuth flow
│   │   │
│   │   ├── configmanager.js     # ⭐ Configuration
│   │   │   - Gestion config.json
│   │   │   - Sauvegarde/chargement settings
│   │   │   - Gestion des comptes
│   │   │
│   │   ├── distromanager.js     # ⭐ Distribution
│   │   │   - Téléchargement distribution.json
│   │   │   - Parse des serveurs
│   │   │   - API helios-core
│   │   │
│   │   ├── processbuilder.js    # ⭐ Lancement Minecraft
│   │   │   - Construction des arguments JVM
│   │   │   - Arguments du jeu
│   │   │   - Lancement du processus
│   │   │
│   │   ├── serverstatus.js      # Status des serveurs
│   │   ├── discordwrapper.js    # Discord RPC
│   │   ├── langloader.js        # Gestion i18n
│   │   │
│   │   └── scripts/
│   │       ├── landing.js       # UI écran principal
│   │       ├── login.js         # UI login
│   │       ├── settings.js      # UI paramètres
│   │       └── uicore.js        # Utilities UI
│   │
│   ├── landing.ejs              # Template écran principal
│   ├── login.ejs                # Template login
│   ├── settings.ejs             # Template settings
│   └── welcome.ejs              # Template welcome
│
├── distribution.json            # ⭐ Configuration Minecraft
│   - Définition des serveurs
│   - Versions Minecraft
│   - Mods et Forge
│
├── package.json                 # Dépendances Node.js
├── electron-builder.yml         # Configuration build
└── build/                       # Assets de build
    └── icon.png                 # Icône application
```

---

## 🔄 Workflows et Processus

### 1. Démarrage du Launcher

```
Lancement electron .
    ↓
index.js - Initialisation
    ↓
Création BrowserWindow
    ↓
Chargement welcome.ejs (si premier lancement)
    OU
    ↓
Chargement login.ejs (si compte existe)
    ↓
Initialisation modules:
    - ConfigManager
    - LangLoader
    - DistroManager
    ↓
Téléchargement distribution.json
    ↓
Vérification des updates (auto-updater)
    ↓
✅ Launcher prêt
```

### 2. Processus d'Authentification

```
Utilisateur: Clic "Se connecter"
    ↓
authmanager.addMicrosoftAccount()
    ↓
MicrosoftAuth.login() [helios-core]
    ↓
Ouverture navigateur → login.live.com
    ↓
Utilisateur entre credentials
    ↓
Microsoft redirige vers http://localhost:1337?code=XXX
    ↓
Launcher intercepte le code
    ↓
Échange code → access_token
    ↓
Xbox Live Authentication
    ↓
XSTS Token Generation
    ↓
Minecraft Services Login
    ↓
Vérification Ownership
    ↓
Si OK: Récupération profil (UUID + Username)
    ↓
Stockage dans config.json
    ↓
✅ Redirection vers landing.ejs
```

### 3. Lancement du Jeu

```
Utilisateur: Clic "LANCER"
    ↓
Validation du compte sélectionné
    ↓
Vérification/téléchargement assets:
    - Minecraft JAR
    - Libraries (.jar)
    - Natives (OS-specific)
    - Assets (sons, textures)
    - Forge (si applicable)
    - Mods (si configurés)
    ↓
Construction arguments:
    - JVM args (-Xmx, -Xms, etc.)
    - Game args (--username, --uuid, --accessToken)
    - Classpath
    ↓
Lancement processus Java
    ↓
Discord RPC: "Joue à Minecraft"
    ↓
Monitoring du processus
    ↓
✅ Minecraft lancé
```

### 4. Vérification des Updates

```
Démarrage du launcher
    ↓
autoUpdater.checkForUpdates()
    ↓
Comparaison version locale vs GitHub releases
    ↓
Si nouvelle version:
    - Notification à l'utilisateur
    - Téléchargement en background
    - Installation au prochain démarrage
```

---

## 👤 Guide Utilisateur

### Installation

**Windows:**
1. Télécharger `Nemesis-Launcher-setup-2.2.1.exe`
2. Double-clic pour installer
3. Suivre l'assistant d'installation
4. Lancer depuis le menu Démarrer

**macOS:**
1. Télécharger `Nemesis-Launcher-setup-2.2.1.dmg`
2. Ouvrir le DMG
3. Glisser vers Applications
4. Lancer depuis Applications

**Linux:**
1. Télécharger `Nemesis-Launcher-setup-2.2.1.AppImage`
2. Rendre exécutable: `chmod +x Nemesis-Launcher-*.AppImage`
3. Lancer: `./Nemesis-Launcher-*.AppImage`

### Utilisation

**Première Connexion:**
1. Lancer Nemesis Launcher
2. Cliquer "Se connecter avec Microsoft"
3. S'authentifier dans le navigateur
4. Retour automatique au launcher
5. Sélectionner une version Minecraft
6. Ajuster la RAM (Settings)
7. Cliquer "LANCER"

**Ajouter un Compte:**
1. Settings → Accounts
2. Cliquer "Add Account"
3. Se connecter avec Microsoft
4. Le compte apparaît dans la liste

**Changer de Compte:**
1. Settings → Accounts
2. Double-clic sur le compte désiré
3. Le launcher change automatiquement

**Paramètres:**
- RAM allouée (Min/Max)
- Résolution de la fenêtre
- Arguments JVM personnalisés
- Langue de l'interface
- Mode plein écran

### Console Développeur

**Ouvrir la console:**
```
Ctrl + Shift + I  (Windows/Linux)
Cmd + Shift + I   (macOS)
```

**Logs disponibles:**
- Authentification
- Téléchargements
- Lancement du jeu
- Erreurs

---

## 💻 Développement

### Prérequis

- Node.js 20.x
- npm 11.x
- Git

### Installation

```bash
# Cloner le repository
git clone https://github.com/EspritFurifX/Nemesis-Launcher.git
cd Nemesis-Launcher/nemesis-client

# Installer les dépendances
npm install

# Lancer en mode développement
npm start
```

### Scripts Disponibles

```json
{
    "start": "electron .",              // Lancer le launcher
    "dist": "electron-builder build",   // Build toutes plateformes
    "dist:win": "npm run dist -- -w",   // Build Windows
    "dist:mac": "npm run dist -- -m",   // Build macOS
    "dist:linux": "npm run dist -- -l", // Build Linux
    "lint": "eslint --config .eslintrc.json ."
}
```

### Build Production

```bash
# Build pour votre plateforme
npm run dist

# Build multi-plateformes
./build-all-releases.sh

# Fichiers générés dans dist/
```

### Dépendances Principales

| Package | Version | Usage |
|---------|---------|-------|
| electron | ^33.2.1 | Framework application |
| helios-core | ~2.2.4 | Distribution & Auth Minecraft |
| electron-updater | ^6.3.9 | Auto-update |
| discord-rpc-patch | ^4.0.1 | Discord Rich Presence |
| ejs-electron | ^3.0.0 | Templates |
| jquery | ^3.7.1 | DOM manipulation |

### Structure de Développement

```javascript
// Exemple: Ajouter une nouvelle fonctionnalité

// 1. Créer un nouveau module (si nécessaire)
// app/assets/js/myfeature.js
class MyFeature {
    constructor() {
        this.init()
    }
    
    init() {
        // Initialisation
    }
}

module.exports = MyFeature

// 2. Importer dans le script UI approprié
// app/assets/js/scripts/landing.js
const MyFeature = require('./myfeature')
const myFeature = new MyFeature()

// 3. Ajouter UI dans le template EJS
// app/landing.ejs
<button onclick="myFeature.doSomething()">Action</button>

// 4. Tester
npm start
```

### Debugging

```javascript
// Dans n'importe quel fichier .js
const { LoggerUtil } = require('helios-core')
const logger = LoggerUtil.getLogger('MyModule')

logger.info('Information message')
logger.warn('Warning message')
logger.error('Error message', error)
```

---

## 📊 Statistiques du Projet

### Code

- **Lignes de code:** ~10,000+
- **Fichiers JavaScript:** 25+
- **Templates EJS:** 8
- **Fichiers CSS:** 5+
- **Langues supportées:** 2 (EN, FR)

### Fonctionnalités

- ✅ Authentification Microsoft
- ✅ Multi-comptes
- ✅ 5 versions Minecraft
- ✅ Auto-update
- ✅ Discord RPC
- ✅ Multi-langue
- ✅ Gestion Java
- ✅ News feed
- ✅ Server status
- ✅ Multi-plateforme

### Platefomes Supportées

- ✅ Windows 10/11 (x64)
- ✅ macOS 10.15+ (x64, arm64)
- ✅ Linux (x64) - AppImage

---

## 🎯 Statut du Projet

### ✅ Terminé et Fonctionnel

- [x] Authentification Microsoft OAuth 2.0
- [x] Multi-versions Minecraft (1.12.2 à 1.20.1)
- [x] Support Forge
- [x] Téléchargement automatique assets
- [x] Interface utilisateur complète
- [x] Multi-comptes
- [x] Auto-update
- [x] Discord Rich Presence
- [x] Gestion Java automatique
- [x] Documentation complète

### 🚀 Prêt pour Production

Le launcher est **100% fonctionnel** et **prêt à être distribué** :

- ✅ Build réussi sur toutes les plateformes
- ✅ Authentification testée et validée
- ✅ Lancement Minecraft testé
- ✅ Conformité Mojang/Microsoft
- ✅ Sécurité implémentée
- ✅ Documentation complète

---

## 📞 Support et Ressources

### Documentation

- [README Principal](README.md)
- [Guide Client](nemesis-client/README.md)
- [Configuration Distribution](nemesis-client/DISTRIBUTION_GUIDE.md)
- [Migration](nemesis-client/MIGRATION_CHECKLIST.md)
- [Succès](SUCCES.md)
- [Authentification](AUTHENTICATION_COMPLETE.md)

### Liens Utiles

- **Repository:** https://github.com/EspritFurifX/Nemesis-Launcher
- **Issues:** https://github.com/EspritFurifX/Nemesis-Launcher/issues
- **Releases:** https://github.com/EspritFurifX/Nemesis-Launcher/releases

### Contact

- GitHub: @EspritFurifX
- Email: Disponible sur GitHub

---

## 🎉 Conclusion

**NEMESIS LAUNCHER** est un projet complet et professionnel qui offre:

1. ✅ **Sécurité** - Authentification Microsoft officielle
2. ✅ **Conformité** - 100% légal et respectueux des règles
3. ✅ **Fonctionnalité** - Toutes les features attendues d'un launcher moderne
4. ✅ **Qualité** - Code propre et documenté
5. ✅ **Maintenance** - Auto-update et facilité de mise à jour

Le projet est **prêt pour la production** et peut être distribué en toute confiance.

---

**Nemesis Launcher v2.2.1**  
*Launcher Minecraft Java Edition Officiel*

🔒 **Sécurisé** · 🎮 **Fonctionnel** · 📖 **Documenté** · ✅ **Conforme**

---

*Document généré le 28 décembre 2025*
