#!/bin/bash

# Script de premier commit Git pour le Minecraft Educational Launcher
# Ce script initialise le repository et prépare le premier commit

echo "========================================"
echo "🚀 Git Setup - Minecraft Launcher"
echo "========================================"
echo ""

# Vérifier que Git est installé
if ! command -v git &> /dev/null; then
    echo "❌ Git n'est pas installé"
    echo "   Installez Git depuis : https://git-scm.com/"
    exit 1
fi

echo "✅ Git détecté : $(git --version)"
echo ""

# Vérifier si déjà un repo Git
if [ -d ".git" ]; then
    echo "⚠️  Ce dossier est déjà un repository Git"
    read -p "Voulez-vous réinitialiser ? (y/N) : " reset
    if [ "$reset" = "y" ] || [ "$reset" = "Y" ]; then
        rm -rf .git
        echo "✅ Repository réinitialisé"
    else
        echo "❌ Opération annulée"
        exit 0
    fi
fi

echo ""
echo "📝 Configuration de votre identité Git"
echo ""

# Demander le nom et email si pas configurés
GIT_NAME=$(git config --global user.name)
GIT_EMAIL=$(git config --global user.email)

if [ -z "$GIT_NAME" ]; then
    read -p "Votre nom : " name
    git config --global user.name "$name"
    echo "✅ Nom configuré : $name"
else
    echo "✅ Nom déjà configuré : $GIT_NAME"
fi

if [ -z "$GIT_EMAIL" ]; then
    read -p "Votre email : " email
    git config --global user.email "$email"
    echo "✅ Email configuré : $email"
else
    echo "✅ Email déjà configuré : $GIT_EMAIL"
fi

echo ""
echo "🎯 Initialisation du repository Git..."

# Initialiser le repo
git init
git branch -M main

# Ajouter tous les fichiers
git add .

# Premier commit
git commit -m "🎉 Initial commit: Minecraft Educational Launcher v2.0.0

- Architecture MVC complète
- Téléchargement automatique (versions, libraries, assets, natives)
- Interface JavaFX moderne
- Support multi-OS (Windows, macOS, Linux)
- Documentation complète (README, TECHNICAL, CONTRIBUTING)
- Scripts de lancement inclus
- 100% légal (APIs officielles Mojang)

Features:
✅ Version manager with official Mojang API
✅ Download manager (JAR, libraries, assets, natives)
✅ Launch manager with full classpath
✅ JavaFX UI with real-time logs
✅ Multi-OS native support
✅ Comprehensive documentation"

echo ""
echo "✅ Premier commit créé !"
echo ""
echo "========================================"
echo "📋 Prochaines étapes"
echo "========================================"
echo ""
echo "1️⃣  Créer un repository sur GitHub :"
echo "   https://github.com/new"
echo ""
echo "2️⃣  Nom suggéré : minecraft-educational-launcher"
echo ""
echo "3️⃣  Description suggérée :"
echo "   🎮 Launcher Minecraft Java légal et éducatif - Projet d'apprentissage complet"
echo ""
echo "4️⃣  Puis exécuter ces commandes :"
echo ""
echo "   git remote add origin https://github.com/EspritFurifX/Nemesis-client.git"
echo "   git push -u origin main"
echo ""
echo "5️⃣  Consultez PUBLISH.md pour la suite (Release, Topics, etc.)"
echo ""
echo "========================================"
echo "🎉 Repository prêt à être publié !"
echo "========================================"
