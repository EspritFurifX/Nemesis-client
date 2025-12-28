#!/bin/bash
# Script pour autoriser NEMESIS CLIENT sur macOS

echo "🔓 Suppression de la quarantaine macOS..."

# Copier l'app dans Applications si pas déjà fait
if [ ! -d "/Applications/NEMESIS-CLIENT.app" ]; then
    echo "📦 Montage du DMG..."
    hdiutil attach "packages/NEMESIS-CLIENT-2.0.0.dmg" -quiet
    
    echo "📂 Copie dans Applications..."
    cp -R "/Volumes/NEMESIS-CLIENT 1/NEMESIS-CLIENT.app" /Applications/
    
    echo "⏏️  Démontage du DMG..."
    hdiutil detach "/Volumes/NEMESIS-CLIENT 1" -quiet
fi

# Supprimer la quarantaine
echo "🔓 Autorisation de l'application..."
sudo xattr -rd com.apple.quarantine /Applications/NEMESIS-CLIENT.app

echo "✅ NEMESIS CLIENT est maintenant autorisé !"
echo "   Vous pouvez le lancer depuis Applications"
