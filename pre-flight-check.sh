#!/bin/bash

# 🚀 Pre-Flight Check - Nemesis Launcher
# Vérifie que tout est prêt avant le lancement

echo "🔍 PRE-FLIGHT CHECK - Nemesis Launcher"
echo "========================================"
echo ""

# Couleurs
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

ERRORS=0
WARNINGS=0

# 1. Check Node.js
echo "📦 Vérification Node.js..."
if command -v node &> /dev/null; then
    NODE_VERSION=$(node -v)
    echo -e "${GREEN}✓${NC} Node.js installé: $NODE_VERSION"
else
    echo -e "${RED}✗${NC} Node.js non trouvé"
    ((ERRORS++))
fi
echo ""

# 2. Check npm
echo "📦 Vérification npm..."
if command -v npm &> /dev/null; then
    NPM_VERSION=$(npm -v)
    echo -e "${GREEN}✓${NC} npm installé: $NPM_VERSION"
else
    echo -e "${RED}✗${NC} npm non trouvé"
    ((ERRORS++))
fi
echo ""

# 3. Check node_modules
echo "📚 Vérification dépendances..."
if [ -d "node_modules" ]; then
    MODULES_COUNT=$(ls node_modules | wc -l | tr -d ' ')
    echo -e "${GREEN}✓${NC} node_modules présent ($MODULES_COUNT packages)"
else
    echo -e "${YELLOW}⚠${NC} node_modules absent - lancer 'npm install'"
    ((WARNINGS++))
fi
echo ""

# 4. Check fichiers critiques
echo "📄 Vérification fichiers critiques..."
CRITICAL_FILES=(
    "package.json"
    "index.js"
    "distribution.json"
    "app/landing.ejs"
    "app/assets/css/launcher.css"
)

for file in "${CRITICAL_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓${NC} $file"
    else
        echo -e "${RED}✗${NC} $file MANQUANT"
        ((ERRORS++))
    fi
done
echo ""

# 5. Check assets
echo "🖼️ Vérification assets..."
ASSET_FILES=(
    "app/assets/images/SealCircle.png"
    "app/assets/images/logo_nemesis.png"
    "app/assets/images/backgrounds/"
)

for asset in "${ASSET_FILES[@]}"; do
    if [ -f "$asset" ] || [ -d "$asset" ]; then
        if [ -f "$asset" ]; then
            SIZE=$(du -h "$asset" | cut -f1)
            echo -e "${GREEN}✓${NC} $asset ($SIZE)"
        else
            COUNT=$(ls "$asset" 2>/dev/null | wc -l | tr -d ' ')
            echo -e "${GREEN}✓${NC} $asset ($COUNT fichiers)"
        fi
    else
        echo -e "${RED}✗${NC} $asset MANQUANT"
        ((ERRORS++))
    fi
done
echo ""

# 6. Check distribution.json
echo "⚙️ Vérification distribution.json..."
if [ -f "distribution.json" ]; then
    if grep -q "nemesis-1.21" distribution.json; then
        echo -e "${GREEN}✓${NC} Configuration nemesis-1.21 trouvée"
    else
        echo -e "${RED}✗${NC} nemesis-1.21 non trouvé dans distribution.json"
        ((ERRORS++))
    fi
    
    if grep -q "fabric-loader" distribution.json; then
        FABRIC_VERSION=$(grep -A 1 "fabric-loader" distribution.json | grep "name" | cut -d'"' -f4 | head -1)
        echo -e "${GREEN}✓${NC} Fabric Loader configuré: $FABRIC_VERSION"
    else
        echo -e "${YELLOW}⚠${NC} Fabric Loader non trouvé"
        ((WARNINGS++))
    fi
else
    echo -e "${RED}✗${NC} distribution.json manquant"
    ((ERRORS++))
fi
echo ""

# 7. Check landing.ejs pour affichage version
echo "🎨 Vérification UI..."
if grep -q "version_info_display" app/landing.ejs; then
    echo -e "${GREEN}✓${NC} Affichage version configuré dans landing.ejs"
else
    echo -e "${YELLOW}⚠${NC} Affichage version non trouvé dans landing.ejs"
    ((WARNINGS++))
fi
echo ""

# 8. Résumé
echo "========================================"
if [ $ERRORS -eq 0 ] && [ $WARNINGS -eq 0 ]; then
    echo -e "${GREEN}✅ TOUT EST PRÊT POUR LE LANCEMENT${NC}"
    echo ""
    echo "Lance maintenant:"
    echo "  npm start"
elif [ $ERRORS -eq 0 ]; then
    echo -e "${YELLOW}⚠️ $WARNINGS warning(s)${NC}"
    echo "Le launcher peut fonctionner mais vérifie les warnings"
    echo ""
    echo "Lance quand même:"
    echo "  npm start"
else
    echo -e "${RED}❌ $ERRORS erreur(s) critique(s)${NC}"
    if [ $WARNINGS -gt 0 ]; then
        echo -e "${YELLOW}⚠️ $WARNINGS warning(s)${NC}"
    fi
    echo ""
    echo "Corrige les erreurs avant de lancer"
    [ ! -d "node_modules" ] && echo "  npm install"
fi
echo ""
