#!/bin/bash
# Script de mise à jour de la distribution Nemesis Launcher

echo "🔄 Mise à jour de la distribution..."

# Chemins
LAUNCHER_DIR="$HOME/github/Minecraft/nemesis-launcher"
APP_DATA="$HOME/Library/Application Support/Nemesis Launcher"
MODS_SRC="$HOME/github/Minecraft/nemesis-clients/1.21-pvp/mods"
MODS_DEST="$APP_DATA/common/modstore/fabric"

# 1. Copier distribution_dev.json
echo "📝 Copie de distribution_dev.json..."
cp "$LAUNCHER_DIR/distribution_dev.json" "$APP_DATA/distribution_dev.json"

# 2. Créer structure mods si nécessaire
mkdir -p "$MODS_DEST"

# 3. Copier les mods
if [ -d "$MODS_SRC" ]; then
    echo "📦 Copie des mods..."
    cp "$MODS_SRC"/*.jar "$MODS_DEST/" 2>/dev/null
    COUNT=$(ls -1 "$MODS_DEST"/*.jar 2>/dev/null | wc -l | tr -d ' ')
    echo "✅ $COUNT mods installés"
else
    echo "⚠️  Dossier mods source non trouvé: $MODS_SRC"
fi

echo ""
echo "✅ Distribution mise à jour!"
echo "📂 Fichiers:"
echo "  - $APP_DATA/distribution_dev.json"
echo "  - $MODS_DEST/*.jar"
