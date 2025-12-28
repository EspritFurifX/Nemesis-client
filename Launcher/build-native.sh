#!/bin/bash

###############################################################################
# NÉMÉSIS CLIENT - Native Package Builder
# Génère des installateurs natifs : .dmg (macOS), .exe (Windows), .deb (Linux)
###############################################################################

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}"
echo "╔═══════════════════════════════════════════════════════════╗"
echo "║     NÉMÉSIS CLIENT - NATIVE PACKAGE BUILDER              ║"
echo "║    Génération d'installateurs natifs (.dmg/.exe/.deb)    ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Variables
APP_NAME="NEMESIS-CLIENT"
APP_VERSION="2.0.0"
MAIN_CLASS="com.minecraft.launcher.Launcher"
JAR_FILE="target/minecraft-educational-launcher-1.0.0-SNAPSHOT.jar"
BUILD_DIR="target"
PACKAGE_DIR="packages"
ICON_PATH="nemesis-icon.png"

# Vérifier Java
if ! command -v java &> /dev/null; then
    echo -e "${RED}❌ Java n'est pas installé${NC}"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo -e "${RED}❌ Java 17+ requis (version actuelle: $JAVA_VERSION)${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Java $JAVA_VERSION détecté${NC}"

# Nettoyer et compiler
echo -e "${YELLOW}🧹 Nettoyage...${NC}"
rm -rf "$PACKAGE_DIR"
mkdir -p "$PACKAGE_DIR"

echo -e "${YELLOW}🔨 Compilation Maven...${NC}"
mvn clean package -DskipTests

if [ ! -f "$JAR_FILE" ]; then
    echo -e "${RED}❌ Le JAR n'a pas été généré${NC}"
    exit 1
fi

echo -e "${GREEN}✅ JAR généré: $JAR_FILE${NC}"

###############################################################################
# PACKAGE macOS (.dmg)
###############################################################################

if [[ "$OSTYPE" == "darwin"* ]]; then
    echo -e "${BLUE}🍎 Génération du package macOS (.dmg)...${NC}"
    
    # Vérifier le logo
    ICON_OPTION=""
    if [ -f "$ICON_PATH" ]; then
        echo -e "${GREEN}✅ Logo trouvé: $ICON_PATH${NC}"
        ICON_OPTION="--icon $ICON_PATH"
    else
        echo -e "${YELLOW}⚠️  Logo non trouvé, icône par défaut utilisée${NC}"
    fi
    
    # Note: On n'utilise PAS --add-modules car JavaFX est déjà embarqué dans le JAR fat
    jpackage \
        --input "$BUILD_DIR" \
        --name "$APP_NAME" \
        --main-jar "$(basename $JAR_FILE)" \
        --main-class "$MAIN_CLASS" \
        --type dmg \
        --app-version "$APP_VERSION" \
        --vendor "NEMESIS Development Team" \
        --copyright "© 2025 NEMESIS CLIENT - MIT License" \
        --description "Launcher Minecraft Java Edition éducatif" \
        --dest "$PACKAGE_DIR" \
        --java-options "-Xmx2G" \
        --mac-package-name "NEMESIS CLIENT" \
        --mac-package-identifier "com.nemesis.launcher" \
        $ICON_OPTION \
        --verbose
    
    if [ -f "$PACKAGE_DIR/$APP_NAME-$APP_VERSION.dmg" ]; then
        echo -e "${GREEN}✅ Package macOS créé: $PACKAGE_DIR/$APP_NAME-$APP_VERSION.dmg${NC}"
    else
        echo -e "${RED}❌ Échec de la création du .dmg${NC}"
    fi
fi

###############################################################################
# INSTRUCTIONS MULTI-PLATEFORMES
###############################################################################

echo ""
echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}"
echo -e "${GREEN}            BUILD TERMINÉ AVEC SUCCÈS !${NC}"
echo -e "${BLUE}═══════════════════════════════════════════════════════════${NC}"
echo ""

if [[ "$OSTYPE" == "darwin"* ]]; then
    echo -e "${YELLOW}📦 Package macOS généré:${NC}"
    echo "  $PACKAGE_DIR/$APP_NAME-$APP_VERSION.dmg"
    echo ""
    echo -e "${YELLOW}Installation:${NC}"
    echo "  1. Double-cliquer sur le .dmg"
    echo "  2. Glisser l'application dans le dossier Applications"
    echo "  3. Lancer NEMESIS CLIENT depuis Applications"
    echo ""
fi

echo -e "${YELLOW}📦 Pour générer les packages Windows/Linux:${NC}"
echo ""
echo -e "${BLUE}Windows (.exe + .msi):${NC}"
echo "  1. Sur une machine Windows avec JDK 17+"
echo "  2. Installer WiX Toolset: https://wixtoolset.org/"
echo "  3. Exécuter:"
echo "     mvn clean package"
echo "     jpackage --input target \\"
echo "       --name NEMESIS-CLIENT \\"
echo "       --main-jar $(basename $JAR_FILE) \\"
echo "       --main-class $MAIN_CLASS \\"
echo "       --type msi \\"
echo "       --win-dir-chooser \\"
echo "       --win-menu \\"
echo "       --win-shortcut"
echo ""
echo -e "${BLUE}Linux (.deb):${NC}"
echo "  1. Sur une machine Linux avec JDK 17+"
echo "  2. Exécuter:"
echo "     mvn clean package"
echo "     jpackage --input target \\"
echo "       --name nemesis-client \\"
echo "       --main-jar $(basename $JAR_FILE) \\"
echo "       --main-class $MAIN_CLASS \\"
echo "       --type deb \\"
echo "       --linux-shortcut"
echo ""

echo -e "${GREEN}✅ Prêt pour distribution !${NC}"
