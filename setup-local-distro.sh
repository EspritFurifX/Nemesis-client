#!/bin/bash
echo "📦 Installation de la distribution locale..."

# Créer le répertoire de l'app s'il n'existe pas
APP_DATA="$HOME/Library/Application Support/nemesis-client"
mkdir -p "$APP_DATA"

# Copier distribution.json
cp distribution.json "$APP_DATA/distribution.json"

echo "✅ Distribution installée dans: $APP_DATA/distribution.json"
ls -lh "$APP_DATA/distribution.json"
