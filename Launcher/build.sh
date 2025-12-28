#!/bin/bash

###############################################################################
# NÉMÉSIS CLIENT - Build Script
# Génère les releases pour Windows, macOS et Linux
###############################################################################

set -e

# Couleurs
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}"
echo "╔═══════════════════════════════════════════════════════════╗"
echo "║          NÉMÉSIS CLIENT - BUILD SCRIPT                   ║"
echo "║    Génération des releases multi-plateformes             ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Variables
VERSION="2.0.0"
BUILD_DIR="target"
RELEASE_DIR="releases"
JAR_NAME="nemesis-client-${VERSION}.jar"

# Créer le dossier releases
echo -e "${YELLOW}📁 Création du dossier releases...${NC}"
rm -rf "$RELEASE_DIR"
mkdir -p "$RELEASE_DIR"/{windows,macos,linux}

# Nettoyer et compiler
echo -e "${YELLOW}🧹 Nettoyage des builds précédents...${NC}"
mvn clean

echo -e "${YELLOW}🔨 Compilation du launcher...${NC}"
mvn package -DskipTests

# Vérifier que le JAR a été créé
if [ ! -f "$BUILD_DIR/minecraft-educational-launcher-1.0.0-SNAPSHOT.jar" ]; then
    echo -e "${RED}❌ Erreur: Le JAR n'a pas été généré${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Compilation réussie${NC}"

# Renommer le JAR
mv "$BUILD_DIR/minecraft-educational-launcher-1.0.0-SNAPSHOT.jar" "$BUILD_DIR/$JAR_NAME"

###############################################################################
# VERSION WINDOWS
###############################################################################

echo -e "${BLUE}🪟  Génération de la version Windows...${NC}"

# Copier le JAR
cp "$BUILD_DIR/$JAR_NAME" "$RELEASE_DIR/windows/"

# Créer un launcher .bat
cat > "$RELEASE_DIR/windows/nemesis-client.bat" << 'EOF'
@echo off
title NEMESIS CLIENT - Launcher
echo ========================================
echo   NEMESIS CLIENT - Launcher Minecraft
echo ========================================
echo.

REM Vérifier si Java est installé
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERREUR] Java n'est pas installe ou n'est pas dans le PATH
    echo.
    echo Telechargez Java 17+ : https://adoptium.net/
    pause
    exit /b 1
)

echo [INFO] Lancement du launcher...
echo.

REM Lancer le launcher avec 2 Go de RAM
java -Xmx2G -jar nemesis-client-2.0.0.jar

if errorlevel 1 (
    echo.
    echo [ERREUR] Le launcher a rencontre une erreur
    pause
)
EOF

# Créer un README Windows
cat > "$RELEASE_DIR/windows/README.txt" << 'EOF'
NEMESIS CLIENT - Version Windows
================================

INSTALLATION:
1. Assurez-vous que Java 17+ est installé
   Télécharger: https://adoptium.net/

2. Double-cliquez sur nemesis-client.bat

3. Le launcher se lancera automatiquement

PROBLEMES COURANTS:
- "Java n'est pas reconnu": Installez Java 17+
- Erreur de mémoire: Modifiez -Xmx2G dans le .bat
- Pare-feu: Autorisez Java dans le pare-feu Windows

SUPPORT:
GitHub: https://github.com/EspritFurifX/Nemesis-client/issues

(c) 2025 NEMESIS CLIENT - Licence MIT
EOF

echo -e "${GREEN}✅ Version Windows générée: $RELEASE_DIR/windows/${NC}"

###############################################################################
# VERSION MACOS
###############################################################################

echo -e "${BLUE}🍎 Génération de la version macOS...${NC}"

# Copier le JAR
cp "$BUILD_DIR/$JAR_NAME" "$RELEASE_DIR/macos/"

# Créer un launcher .command (équivalent .bat sur Mac)
cat > "$RELEASE_DIR/macos/nemesis-client.command" << 'EOF'
#!/bin/bash

echo "========================================"
echo "  NÉMÉSIS CLIENT - Launcher Minecraft"
echo "========================================"
echo ""

# Aller dans le dossier du script
cd "$(dirname "$0")"

# Vérifier Java
if ! command -v java &> /dev/null; then
    echo "[ERREUR] Java n'est pas installé"
    echo ""
    echo "Installez Java 17+ depuis:"
    echo "https://adoptium.net/"
    echo ""
    read -p "Appuyez sur Entrée pour quitter..."
    exit 1
fi

echo "[INFO] Lancement du launcher..."
echo ""

# Lancer avec 2 Go de RAM
java -Xmx2G -jar nemesis-client-2.0.0.jar

if [ $? -ne 0 ]; then
    echo ""
    echo "[ERREUR] Le launcher a rencontré une erreur"
    read -p "Appuyez sur Entrée pour quitter..."
fi
EOF

chmod +x "$RELEASE_DIR/macos/nemesis-client.command"

# Créer un README macOS
cat > "$RELEASE_DIR/macos/README.txt" << 'EOF'
NÉMÉSIS CLIENT - Version macOS
==============================

INSTALLATION:
1. Assurez-vous que Java 17+ est installé
   Télécharger: https://adoptium.net/

2. Double-cliquez sur nemesis-client.command
   (Si macOS bloque: Clic droit → Ouvrir)

3. Le launcher se lancera automatiquement

AUTORISATION GATEKEEPER (si bloqué):
  xattr -d com.apple.quarantine nemesis-client.command
  xattr -d com.apple.quarantine nemesis-client-2.0.0.jar

PROBLÈMES COURANTS:
- "Java n'est pas reconnu": Installez Java 17+
- Permission refusée: chmod +x nemesis-client.command
- Firewall: Autorisez Java dans Préférences Système

SUPPORT:
GitHub: https://github.com/EspritFurifX/Nemesis-client/issues

© 2025 NÉMÉSIS CLIENT - Licence MIT
EOF

echo -e "${GREEN}✅ Version macOS générée: $RELEASE_DIR/macos/${NC}"

###############################################################################
# VERSION LINUX
###############################################################################

echo -e "${BLUE}🐧 Génération de la version Linux...${NC}"

# Copier le JAR
cp "$BUILD_DIR/$JAR_NAME" "$RELEASE_DIR/linux/"

# Créer un launcher .sh
cat > "$RELEASE_DIR/linux/nemesis-client.sh" << 'EOF'
#!/bin/bash

echo "========================================"
echo "  NÉMÉSIS CLIENT - Launcher Minecraft"
echo "========================================"
echo ""

# Vérifier Java
if ! command -v java &> /dev/null; then
    echo "[ERREUR] Java n'est pas installé"
    echo ""
    echo "Installation Java 17+ :"
    echo "  Ubuntu/Debian: sudo apt install openjdk-17-jdk"
    echo "  Fedora: sudo dnf install java-17-openjdk"
    echo "  Arch: sudo pacman -S jdk17-openjdk"
    echo ""
    read -p "Appuyez sur Entrée pour quitter..."
    exit 1
fi

echo "[INFO] Lancement du launcher..."
echo ""

# Lancer avec 2 Go de RAM
java -Xmx2G -jar "$(dirname "$0")/nemesis-client-2.0.0.jar"

if [ $? -ne 0 ]; then
    echo ""
    echo "[ERREUR] Le launcher a rencontré une erreur"
    read -p "Appuyez sur Entrée pour quitter..."
fi
EOF

chmod +x "$RELEASE_DIR/linux/nemesis-client.sh"

# Créer un .desktop pour intégration Linux
cat > "$RELEASE_DIR/linux/nemesis-client.desktop" << 'EOF'
[Desktop Entry]
Version=2.0.0
Type=Application
Name=NÉMÉSIS CLIENT
Comment=Launcher Minecraft Java Edition
Exec=/usr/bin/java -Xmx2G -jar %f
Icon=minecraft
Terminal=false
Categories=Game;
MimeType=application/x-java-archive;
EOF

# Créer un README Linux
cat > "$RELEASE_DIR/linux/README.txt" << 'EOF'
NÉMÉSIS CLIENT - Version Linux
==============================

INSTALLATION:
1. Installer Java 17+ selon votre distribution:
   
   Ubuntu/Debian:
     sudo apt update && sudo apt install openjdk-17-jdk
   
   Fedora:
     sudo dnf install java-17-openjdk
   
   Arch Linux:
     sudo pacman -S jdk17-openjdk

2. Lancer le launcher:
   ./nemesis-client.sh
   
   Ou double-cliquer sur nemesis-client.sh

INTÉGRATION DESKTOP (optionnel):
  cp nemesis-client.desktop ~/.local/share/applications/
  chmod +x ~/.local/share/applications/nemesis-client.desktop

PROBLÈMES COURANTS:
- Permission refusée: chmod +x nemesis-client.sh
- Java introuvable: Vérifiez avec 'java -version'
- Problème graphique: Installez les drivers GPU

SUPPORT:
GitHub: https://github.com/EspritFurifX/Nemesis-client/issues

© 2025 NÉMÉSIS CLIENT - Licence MIT
EOF

echo -e "${GREEN}✅ Version Linux générée: $RELEASE_DIR/linux/${NC}"

###############################################################################
# CRÉER LES ARCHIVES
###############################################################################

echo -e "${BLUE}📦 Création des archives...${NC}"

cd "$RELEASE_DIR"

# Windows (ZIP)
echo -e "${YELLOW}  → nemesis-client-windows-${VERSION}.zip${NC}"
zip -r -q "nemesis-client-windows-${VERSION}.zip" windows/

# macOS (ZIP)
echo -e "${YELLOW}  → nemesis-client-macos-${VERSION}.zip${NC}"
zip -r -q "nemesis-client-macos-${VERSION}.zip" macos/

# Linux (tar.gz)
echo -e "${YELLOW}  → nemesis-client-linux-${VERSION}.tar.gz${NC}"
tar -czf "nemesis-client-linux-${VERSION}.tar.gz" linux/

cd ..

###############################################################################
# RÉSUMÉ
###############################################################################

echo ""
echo -e "${GREEN}╔═══════════════════════════════════════════════════════════╗${NC}"
echo -e "${GREEN}║              BUILD TERMINÉ AVEC SUCCÈS !                 ║${NC}"
echo -e "${GREEN}╚═══════════════════════════════════════════════════════════╝${NC}"
echo ""
echo -e "${BLUE}📦 Releases générées dans: ${YELLOW}$RELEASE_DIR/${NC}"
echo ""
echo -e "${YELLOW}Archives créées:${NC}"
echo "  🪟  nemesis-client-windows-${VERSION}.zip"
echo "  🍎 nemesis-client-macos-${VERSION}.zip"
echo "  🐧 nemesis-client-linux-${VERSION}.tar.gz"
echo ""
echo -e "${BLUE}📊 Tailles:${NC}"
du -sh "$RELEASE_DIR"/*.zip "$RELEASE_DIR"/*.tar.gz 2>/dev/null | awk '{print "  " $2 ": " $1}'
echo ""
echo -e "${GREEN}✅ Prêt pour publication sur GitHub Releases !${NC}"
echo ""
echo -e "${YELLOW}Prochaines étapes:${NC}"
echo "  1. Tester chaque version sur sa plateforme"
echo "  2. Créer un tag Git: git tag v${VERSION}"
echo "  3. Publier sur GitHub: gh release create v${VERSION} releases/*.{zip,tar.gz}"
echo ""
