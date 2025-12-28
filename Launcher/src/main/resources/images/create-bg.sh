#!/bin/bash
# Créer un pattern Minecraft simple
convert -size 1280x800 xc:"#0d1117" \
  \( -size 32x32 pattern:checkerboard -fill "#1a1d24" -opaque white -fill "#0f1218" -opaque black \) \
  -compose multiply -composite \
  minecraft-bg.png 2>/dev/null || echo "ImageMagick non disponible, utilisation du fond uni"
