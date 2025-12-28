# Icône NÉMÉSIS CLIENT

## Fichiers d'icônes

- `nemesis-icon.png` (24 KB) : Icône convertie depuis le logo original
- Source originale : `../némésis.jpg` (logo racine du projet)

## Format requis par plateforme

### macOS (.dmg)
- **Format requis** : `.icns` (Apple Icon Image)
- **État actuel** : PNG utilisé → jpackage utilise l'icône par défaut
- **Solution** : Créer un fichier `.icns` avec iconutil

### Windows (.exe/.msi)
- **Format requis** : `.ico` (Windows Icon)

### Linux (.deb)
- **Format requis** : `.png` (format standard)
- **État actuel** : ✅ Prêt (nemesis-icon.png)

## Comment créer une icône .icns correcte

```bash
# 1. Créer un iconset avec toutes les tailles
mkdir icon.iconset

# 2. Générer toutes les tailles nécessaires
sips -z 16 16   némésis.jpg --out icon.iconset/icon_16x16.png
sips -z 32 32   némésis.jpg --out icon.iconset/icon_16x16@2x.png
sips -z 32 32   némésis.jpg --out icon.iconset/icon_32x32.png
sips -z 64 64   némésis.jpg --out icon.iconset/icon_32x32@2x.png
sips -z 128 128 némésis.jpg --out icon.iconset/icon_128x128.png
sips -z 256 256 némésis.jpg --out icon.iconset/icon_128x128@2x.png
sips -z 256 256 némésis.jpg --out icon.iconset/icon_256x256.png
sips -z 512 512 némésis.jpg --out icon.iconset/icon_256x256@2x.png
sips -z 512 512 némésis.jpg --out icon.iconset/icon_512x512.png
sips -z 1024 1024 némésis.jpg --out icon.iconset/icon_512x512@2x.png

# 3. Convertir en .icns
iconutil -c icns icon.iconset -o nemesis-icon.icns
rm -rf icon.iconset

# 4. Mettre à jour build-native.sh pour utiliser nemesis-icon.icns
```

## Note

Pour l'instant, l'application fonctionne avec l'icône par défaut Java.
L'icône personnalisée sera ajoutée dans une version future.
