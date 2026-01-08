#!/bin/bash
echo "🧹 Nettoyage du cache Nemesis Launcher..."

# Répertoire de données de l'app
APP_DATA="$HOME/Library/Application Support/nemesis-client"

if [ -d "$APP_DATA" ]; then
    echo "📂 Trouvé: $APP_DATA"
    
    # Sauvegarder config
    if [ -f "$APP_DATA/config.json" ]; then
        cp "$APP_DATA/config.json" /tmp/nemesis-config-backup.json
        echo "�� Config sauvegardée"
    fi
    
    # Supprimer le cache de distribution
    if [ -f "$APP_DATA/distribution.json" ]; then
        rm "$APP_DATA/distribution.json"
        echo "🗑️  Cache distribution supprimé"
    fi
    
    if [ -f "$APP_DATA/distribution-backup.json" ]; then
        rm "$APP_DATA/distribution-backup.json"
        echo "🗑️  Backup distribution supprimé"
    fi
    
    # Restaurer config
    if [ -f /tmp/nemesis-config-backup.json ]; then
        cp /tmp/nemesis-config-backup.json "$APP_DATA/config.json"
        echo "💾 Config restaurée"
    fi
    
    echo "✅ Cache nettoyé!"
else
    echo "ℹ️  Aucun cache trouvé (première installation)"
fi
