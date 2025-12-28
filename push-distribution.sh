#!/bin/bash

# Script pour pousser la distribution sur GitHub

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "🚀 Mise à jour de la distribution Nemesis Launcher"
echo ""

# Vérifier si git est installé
if ! command -v git &> /dev/null; then
    echo "❌ Git n'est pas installé"
    exit 1
fi

# Vérifier si on est dans un repo git
if [ ! -d ".git" ]; then
    echo "❌ Ce n'est pas un dépôt Git"
    exit 1
fi

echo "📝 Fichiers à committer :"
echo "  - distribution.json (6 versions Minecraft)"
echo "  - distromanager.js (URL mise à jour)"
echo "  - index.js (menus macOS améliorés)"
echo ""

# Ajouter les fichiers
git add distribution.json
git add app/assets/js/distromanager.js
git add index.js
git add DISTRIBUTION_GUIDE.md

# Vérifier s'il y a des changements
if git diff --staged --quiet; then
    echo "✓ Aucun changement à committer"
    exit 0
fi

echo "📦 Commit des changements..."
git commit -m "feat: Add 1.8.9 PVP version & improve macOS menu

- ✨ Ajout version Minecraft 1.8.9 optimisée PVP (serveur principal)
- 📋 Menu macOS complet : View, Window, Help
- 🔧 Configuration distribution locale (GitHub)
- 📚 6 versions disponibles : 1.8.9, 1.12.2, 1.16.5, 1.18.2, 1.19.4, 1.20.1
- 🗑️  Suppression lien distribution externe"

echo ""
echo "✓ Changements committés !"
echo ""
read -p "Voulez-vous pousser sur GitHub ? [o/N]: " push_confirm

if [[ $push_confirm =~ ^[Oo]$ ]]; then
    echo ""
    echo "📤 Push vers GitHub..."
    
    # Récupérer la branche actuelle
    CURRENT_BRANCH=$(git branch --show-current)
    
    git push origin "$CURRENT_BRANCH"
    
    echo ""
    echo "✅ Distribution publiée !"
    echo ""
    echo "🌐 URL de distribution:"
    echo "   https://raw.githubusercontent.com/EspritFurifX/Nemesis-Launcher/$CURRENT_BRANCH/nemesis-client/distribution.json"
    echo ""
    echo "📝 N'oubliez pas de rebuilder le launcher pour utiliser la nouvelle distribution !"
else
    echo ""
    echo "⏸️  Push annulé. Vous pouvez pousser manuellement plus tard avec:"
    echo "   git push"
fi

echo ""
echo "✨ Terminé !"
