# 🚀 Guide de Publication GitHub

Ce guide vous accompagne pour publier votre launcher sur GitHub.

---

## 📋 Checklist avant publication

### ✅ Vérifications techniques

- [x] Le code compile sans erreur (`mvn clean package`)
- [x] Le launcher démarre (`mvn javafx:run`)
- [x] La documentation est complète
- [x] Le .gitignore est configuré
- [x] Les scripts sont exécutables

### ✅ Contenu du repository

**Fichiers présents** :
- [x] README.md (avec badges)
- [x] LICENSE (MIT)
- [x] CONTRIBUTING.md
- [x] CHANGELOG.md
- [x] TECHNICAL.md
- [x] STRUCTURE.md
- [x] .gitignore
- [x] Templates GitHub (.github/)

**Facultatif** :
- [ ] Screenshots dans `/docs/screenshots/`
- [ ] Logo du projet
- [ ] GitHub Actions (CI/CD)

---

## 🎯 Étapes de publication

### 1. Initialiser le repository Git

```bash
# Dans le dossier du projet
git init
git add .
git commit -m "🎉 Initial commit: Minecraft Educational Launcher v2.0.0"
```

### 2. Créer le repository sur GitHub

1. Aller sur [github.com/new](https://github.com/new)
2. Nom du repository : `minecraft-educational-launcher`
3. Description : `🎮 Launcher Minecraft Java légal et éducatif - Projet d'apprentissage Java/JavaFX complet et fonctionnel`
4. Public ✅
5. **NE PAS** initialiser avec README (on a déjà le nôtre)
6. Cliquer "Create repository"

### 3. Lier et pousser

```bash
# Repository officiel
git remote add origin https://github.com/EspritFurifX/Nemesis-client.git
git branch -M main
git push -u origin main
```

### 4. Créer la première Release

1. Sur GitHub, aller dans "Releases" → "Create a new release"
2. Tag : `v2.0.0`
3. Title : `🚀 Version 2.0.0 - Production Ready`
4. Description :

```markdown
## 🎉 Première release officielle - Production Ready

### ✨ Fonctionnalités

- ✅ Téléchargement automatique de toutes les versions Minecraft
- ✅ Support complet des libraries Java
- ✅ Support des natives OS-spécifiques (Windows, macOS, Linux)
- ✅ Téléchargement des assets (sons, textures, langues)
- ✅ Interface JavaFX moderne
- ✅ Lancement fonctionnel de Minecraft

### 📦 Installation

Consultez le [README.md](README.md) pour les instructions complètes.

**Prérequis** :
- Java 17+
- Maven 3.8+

**Quick start** :
\```bash
git clone https://github.com/EspritFurifX/Nemesis-client.git
cd Nemesis-client/Launcher
./launcher.sh  # ou launcher.bat sur Windows
\```

### 📚 Documentation

- [README.md](README.md) - Guide utilisateur
- [TECHNICAL.md](TECHNICAL.md) - Documentation technique
- [CONTRIBUTING.md](CONTRIBUTING.md) - Guide de contribution

### ⚖️ Légalité

Ce launcher est 100% légal et utilise uniquement les APIs officielles Mojang.
Pour jouer en ligne, un compte Minecraft officiel est requis.

### 🙏 Remerciements

Merci à la communauté Minecraft et aux développeurs de JavaFX.

---

**Bon jeu ! 🎮**
```

5. Publier la release

---

## 🎨 Optimisations GitHub

### Topics (tags du repository)

Ajouter dans "Settings" → "Topics" :
- `minecraft`
- `launcher`
- `java`
- `javafx`
- `educational`
- `mojang-api`
- `maven`
- `open-source`

### About (description courte)

```
🎮 Launcher Minecraft Java légal et éducatif - Projet d'apprentissage complet avec JavaFX et APIs Mojang officielles
```

### Website

Si vous avez une page démo ou documentation en ligne.

### Social preview

Créer une image 1280x640px avec :
- Logo Minecraft stylisé
- Texte "Educational Launcher"
- "100% Legal & Open Source"

---

## 📢 Promotion (optionnel)

### LinkedIn / Portfolio

```markdown
🎮 Nouveau projet : Launcher Minecraft Éducatif

J'ai développé un launcher Minecraft Java complet et fonctionnel en utilisant :
• Java 17 & JavaFX pour l'interface
• APIs REST officielles Mojang
• Architecture MVC propre
• ~2000 lignes de code commenté

100% légal, open source et éducatif 🎓

Le projet télécharge automatiquement :
✓ Les versions Minecraft
✓ Les libraries Java (~100+ JARs)
✓ Les natives OS-spécifiques
✓ Les assets (sons, textures)

🔗 [lien GitHub]

#Java #JavaFX #Minecraft #OpenSource #DeveloppementLogiciel
```

### Reddit (r/javahelp, r/Minecraft)

Poster dans les subreddits appropriés avec contexte éducatif.

---

## 🎯 Checklist finale

Avant de partager publiquement :

- [ ] Repository créé et poussé
- [ ] README clair avec badges
- [ ] LICENSE présente
- [ ] Release v2.0.0 créée
- [ ] Topics configurés
- [ ] Description About configurée
- [ ] .gitignore correct (pas de fichiers sensibles)
- [ ] Testé un `git clone` + build complet

---

## 🏆 Vous êtes prêt !

Votre launcher est maintenant public et professionnel.

**Prochaines étapes** :
- Répondre aux issues
- Accepter les contributions
- Maintenir la documentation
- Peut-être ajouter des features (OAuth, Forge, etc.)

**Félicitations pour ce projet de qualité ! 🎉**

---

**Questions ?** Ouvrez une issue avec le tag `[QUESTION]`
