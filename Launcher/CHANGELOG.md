# Changelog - Minecraft Educational Launcher

Toutes les modifications notables de ce projet seront documentées ici.

## [2.0.0] - 2025-12-28

### 🚀 LANCEMENT 100% FONCTIONNEL

Cette version majeure rend le launcher **complètement fonctionnel**. 
Minecraft démarre vraiment et peut être joué !

### ✨ Ajouts majeurs

#### Téléchargement des Libraries
- Parsing complet de la section `libraries` du manifest
- Support des règles OS (Windows, macOS, Linux)
- Téléchargement automatique depuis `libraries.minecraft.net`
- Gestion des artifacts standards et classifiers
- Construction automatique du classpath complet

#### Support des Natives (bibliothèques OS-spécifiques)
- Détection automatique de l'OS et de l'architecture (32/64 bits)
- Téléchargement des natives appropriés (LWJGL, OpenAL, etc.)
- Extraction automatique des fichiers natifs (.dll, .so, .dylib)
- Configuration de `java.library.path` dans la commande de lancement

#### Téléchargement des Assets
- Téléchargement de l'index des assets
- Téléchargement de tous les fichiers de ressources :
  - Sons
  - Musiques
  - Textures
  - Fichiers de langues
- Structure de stockage conforme : `assets/objects/ab/abc123...`
- Optimisation : skip des fichiers déjà téléchargés
- Progression affichée dans l'interface

#### Commande de lancement complète
- Classpath incluant TOUTES les libraries + le JAR client
- Configuration de `java.library.path` pour les natives
- Séparateur de classpath adapté à l'OS (: ou ;)
- Arguments JVM optimisés par Mojang
- Tous les arguments du jeu correctement passés

### 🔧 Améliorations

#### Interface utilisateur
- Logs enrichis avec symboles (✓, ⚠, 🎮)
- Messages de progression détaillés
- Affichage du nombre de libraries téléchargées
- Indication de l'étape en cours

#### Performance
- Vérification de l'existence des fichiers avant téléchargement
- Comparaison de taille pour éviter les téléchargements inutiles
- Logs de progression pour les longs téléchargements

#### Robustesse
- Gestion d'erreur améliorée pour chaque étape
- Logs détaillés pour le debugging
- Messages d'erreur explicites dans l'interface

### 📚 Documentation

- README.md enrichi avec :
  - Informations légales détaillées
  - Clause de non-responsabilité
  - Explication de ce que le projet enseigne
  - Avertissements sur l'utilisation
- Commentaires pédagogiques dans le code
- Explication des concepts complexes (classpath, natives, etc.)

### 🧪 Nouveautés techniques

- `FileValidator` : Classe pour valider l'intégrité des fichiers
- Extraction de fichiers ZIP pour les natives
- Gestion des règles de compatibilité OS
- Parsing de JSON complexes multi-niveaux

### 🐛 Corrections

- Correction de l'import manquant pour `List` dans `LaunchManager`
- Ajout de l'import `File` pour la compatibilité
- Gestion correcte du séparateur de classpath selon l'OS

---

## [1.0.0] - 2025-12-28

### 🎉 Version initiale

Première version du launcher avec les fonctionnalités de base.

### ✨ Fonctionnalités

- Interface JavaFX inspirée du menu Minecraft
- Récupération des versions via l'API Mojang
- Téléchargement du JSON de version
- Téléchargement du JAR client
- Lancement basique (sans libraries ni assets)
- Configuration :
  - Sélection de version
  - Nom d'utilisateur
  - RAM allouée
- Logs dans l'interface
- Architecture MVC propre

### 🏗️ Architecture

- Package `minecraft/` : logique métier
- Package `ui/` : interface JavaFX
- Package `utils/` : utilitaires (JSON, fichiers)
- Maven comme système de build
- Dépendances : JavaFX 21, Gson, SLF4J

### 📝 Documentation

- README.md complet
- Commentaires pédagogiques dans le code
- Instructions de compilation et d'exécution

### ⚠️ Limitations

- Les libraries n'étaient pas téléchargées
- Les assets n'étaient pas téléchargés
- Le jeu ne pouvait pas démarrer complètement
- Classpath incomplet

---

## Légende

- 🚀 Fonctionnalité majeure
- ✨ Nouvelle fonctionnalité
- 🔧 Amélioration
- 🐛 Correction de bug
- 📚 Documentation
- 🧪 Expérimental
- ⚠️ Avertissement
- 🔒 Sécurité

---

**Format du changelog basé sur [Keep a Changelog](https://keepachangelog.com/)**
