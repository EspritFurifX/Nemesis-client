# 🎮 NÉMÉSIS CLIENT

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](Docs/LICENSE)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://adoptium.net/)
[![Azuriom](https://img.shields.io/badge/CMS-Azuriom-blue.svg)](https://azuriom.com/)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen.svg)]()

**NÉMÉSIS CLIENT** est un projet complet comprenant un launcher Minecraft éducatif et son site web officiel.

## 📋 Structure du projet

Ce repository est organisé en **monorepo** avec trois composants principaux :

```
Minecraft/
├── 🚀 Launcher/          Application Java - Launcher Minecraft éducatif
├── 🌐 Website/           Site web officiel basé sur Azuriom CMS
└── 📚 Docs/              Documentation globale du projet
```

---

## 🚀 Launcher Minecraft

**Launcher Java éducatif 100% légal et fonctionnel**

### Fonctionnalités

- ✅ **Téléchargement automatique** de toutes les versions Minecraft (releases, snapshots)
- ✅ **Support complet** des libraries Java (~100+ JARs par version)
- ✅ **Natives OS-spécifiques** (Windows, macOS, Linux)
- ✅ **Assets complets** (sons, textures, langues - ~500 MB)
- ✅ **Interface JavaFX** moderne et intuitive
- ✅ **Lancement fonctionnel** - Minecraft démarre vraiment !

### Quick Start

```bash
cd Launcher
./launcher.sh        # Linux/macOS
launcher.bat         # Windows
```

📖 **Documentation complète** : [Launcher/README.md](Launcher/README.md)

---

## 🌐 Site Web Officiel

**Site vitrine basé sur Azuriom CMS**

Le site officiel NÉMÉSIS CLIENT est construit avec [Azuriom](https://azuriom.com/fr), le CMS spécialisé pour Minecraft.

### Fonctionnalités du site

- 🏠 **Présentation** du launcher et du projet
- 📥 **Téléchargement** du launcher (releases)
- 📜 **Légalité** - Mentions sur l'usage des APIs Mojang
- 📚 **Documentation** utilisateur
- 🎨 **Thème personnalisé** NÉMÉSIS CLIENT
- 📰 **News & Updates**

### Installation Azuriom

```bash
cd Website
# Suivre les instructions dans Website/README.md
```

📖 **Guide d'installation** : [Website/README.md](Website/README.md)

---

## 📚 Documentation

### Pour les utilisateurs

- 🎮 [Guide du Launcher](Launcher/README.md) - Installation et utilisation
- 🌐 [Site Web](Website/README.md) - Installation du site Azuriom
- ⚖️ [Aspects Légaux](Website/LEGAL.md) - Conformité Mojang

### Pour les développeurs

- 🏗️ [Architecture Technique](Launcher/TECHNICAL.md) - Détails du launcher
- 📊 [Structure du Code](Launcher/STRUCTURE.md) - Organisation du projet
- 🤝 [Guide de Contribution](Docs/CONTRIBUTING.md) - Comment contribuer
- 📝 [Changelog](Launcher/CHANGELOG.md) - Historique des versions

---

## ⚖️ Légalité

### 100% Conforme et Légal

✅ **Launcher** :
- Utilise **uniquement les APIs officielles Mojang**
- Ne contient **aucun fichier cracké ou piraté**
- Télécharge depuis les **serveurs officiels Mojang**
- **Aucun bypass** d'authentification

✅ **Site Web** :
- Présentation claire du projet éducatif
- Mentions légales complètes
- Liens vers Minecraft officiel
- Respect total de la propriété intellectuelle Mojang/Microsoft

### Pour jouer en ligne

⚠️ **Un compte Minecraft OFFICIEL est requis** pour jouer en multijoueur.  
👉 Achetez Minecraft sur [minecraft.net](https://minecraft.net)

---

## 🎯 Objectif du Projet

NÉMÉSIS CLIENT est un **projet éducatif** destiné à :

- 📚 **Apprendre** le développement Java avancé
- 🏗️ **Comprendre** les architectures logicielles (MVC, monorepo)
- 🌐 **Maîtriser** les APIs REST et le parsing JSON
- 🎨 **Découvrir** JavaFX et Azuriom CMS
- 💻 **Pratiquer** les bonnes pratiques de développement

---

## 🚀 Installation Rapide

### Prérequis

| Composant | Prérequis |
|-----------|-----------|
| **Launcher** | Java 17+, Maven 3.8+ |
| **Site Web** | PHP 8.1+, MySQL/MariaDB, Composer |

### Installation complète

```bash
# 1. Cloner le repository
git clone https://github.com/EspritFurifX/N-m-sis-client.git
cd N-m-sis-client

# 2. Installer le launcher
cd Launcher
mvn clean package
mvn javafx:run

# 3. Installer le site web
cd ../Website
# Suivre les instructions du README.md
```

---

## 🛠️ Technologies

### Launcher
- **Langage** : Java 17+
- **UI** : JavaFX 21
- **Build** : Maven
- **APIs** : Mojang Official REST APIs
- **Libs** : Gson, SLF4J

### Site Web
- **CMS** : [Azuriom](https://azuriom.com/fr)
- **Backend** : PHP 8.1+ (Laravel)
- **Frontend** : Bootstrap 5, Blade templates
- **Base de données** : MySQL/MariaDB

---

## 📊 Statistiques du Projet

| Métrique | Valeur |
|----------|--------|
| **Lignes de code** (Launcher) | ~2000 |
| **Classes Java** | 11 |
| **Fichiers de documentation** | 10+ |
| **Support OS** | Windows, macOS, Linux |
| **Versions Minecraft supportées** | Toutes (releases + snapshots) |

---

## 🤝 Contribution

Les contributions sont les bienvenues ! 🎉

📖 Consultez le [Guide de Contribution](Docs/CONTRIBUTING.md) pour :
- Règles de contribution
- Standards de code
- Process de Pull Request

### Domaines de contribution

- 🎮 **Launcher** : Nouvelles fonctionnalités (Forge, Fabric, OAuth)
- 🌐 **Site Web** : Thème Azuriom, plugins
- 📝 **Documentation** : Traductions, tutoriels
- 🐛 **Bugs** : Corrections et améliorations

---

## 📄 Licence

Ce projet est sous licence **MIT Educational Use**.  
Consultez [LICENSE](Docs/LICENSE) pour plus de détails.

### Mentions Importantes

**Minecraft™** est une marque déposée de **Mojang AB / Microsoft Corporation**.

Ce projet n'est PAS :
- Affilié à Mojang AB ou Microsoft
- Approuvé ou sponsorisé par Mojang/Microsoft
- Un produit officiel Minecraft

Tous les téléchargements proviennent des serveurs officiels Mojang.

---

## 🔗 Liens Utiles

- 🎮 [Minecraft Officiel](https://minecraft.net)
- 📖 [Documentation Mojang API](https://wiki.vg/Mojang_API)
- 🌐 [Azuriom CMS](https://azuriom.com/fr)
- 💬 [Discord Azuriom](https://azuriom.com/discord)

---

## 👥 Auteurs

**NÉMÉSIS CLIENT** - Projet éducatif open source

- Launcher : Architecture Java, JavaFX, APIs Mojang
- Site Web : Azuriom CMS avec thème personnalisé

---

## ⭐ Soutien

Si ce projet vous aide dans votre apprentissage :

- ⭐ **Star** le repository
- 🍴 **Fork** pour vos expérimentations
- 🐛 **Contribuez** avec des PRs
- 💬 **Partagez** avec d'autres développeurs

---

## 📞 Support

- 🐛 **Bugs** : [Ouvrir une issue](../../issues)
- 💡 **Questions** : [Discussions](../../discussions)
- 📧 **Contact** : Via les issues GitHub

---

**NÉMÉSIS CLIENT** - Apprendre le développement en créant un launcher Minecraft professionnel 🎓

*Launcher éducatif - Site officiel - 100% Légal - Open Source*

**Version 2.0.0** | Décembre 2025
