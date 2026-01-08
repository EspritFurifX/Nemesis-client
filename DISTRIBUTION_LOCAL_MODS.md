# Configuration des Mods LOCAL - Nemesis Launcher

## ⚠️ Important: URLs LOCAL:

Les mods du client PVP utilisent des URLs `LOCAL:` qui nécessitent que les fichiers soient **pré-packagés avec le launcher**.

### Structure requise:

Les fichiers JAR doivent être placés dans le répertoire du launcher:
```
<LauncherDirectory>/
  ├── common/
  │   └── modstore/
  │       └── fabric/
  │           ├── fabric-api-0.140.3+26.1.jar
  │           ├── sodium-fabric-0.8.2+mc1.21.11.jar
  │           ├── lithium-fabric-0.21.2+mc1.21.11.jar
  │           ├── ferritecore-8.0.3-fabric.jar
  │           ├── entityculling-fabric-1.9.5-mc1.21.8.jar
  │           ├── iris-fabric-1.10.4+mc1.21.11.jar
  │           ├── bettercombat-fabric-3.0.0+1.21.11.jar
  │           ├── cloth-config-21.11.153-fabric.jar
  │           ├── modmenu-18.0.0-alpha.3.jar
  │           ├── dynamic-fps-3.11.2+minecraft-1.21.11-fabric.jar
  │           ├── MouseTweaks-fabric-mc1.21.11-2.30.jar
  │           ├── NoChatReports-FABRIC-1.21.11-v2.18.0.jar
  │           ├── reeses-sodium-options-fabric-2.0.2+mc1.21.11.jar
  │           ├── c2me-fabric-mc1.21.11-0.3.6.0.0.jar
  │           └── lazydfu-0.1.3.jar
```

### Copier les mods lors du build:

Ajoutez au script de build pour copier les mods:

```bash
#!/bin/bash
# Copier les mods dans le package du launcher
mkdir -p build/common/modstore/fabric
cp nemesis-clients/1.21-pvp/mods/*.jar build/common/modstore/fabric/
```

### Alternative: Utiliser des URLs HTTP(s)

Pour éviter le packaging local, vous pouvez héberger les mods sur:
- Un serveur web statique
- GitHub Releases
- Azure Blob Storage / AWS S3
- Votre serveur Azuriom

Ensuite, remplacez les URLs `LOCAL:` par les URLs complètes dans distribution.json.

### Exemple avec GitHub Releases:

```json
{
    "id": "maven.modrinth:AANobbMI:0.8.2+mc1.21.11",
    "name": "Sodium",
    "type": "FabricMod",
    "artifact": {
        "size": 1876396,
        "MD5": "48b94db165ff1d51c3aa440287dd8b7c",
        "url": "https://github.com/EspritFurifX/Nemesis-Launcher/releases/download/v1.0.0/sodium-fabric-0.8.2+mc1.21.11.jar"
    }
}
```

## IDs Maven Modrinth

Format: `maven.modrinth:<project_id>:<version>`

| Mod | Project ID | Notes |
|-----|------------|-------|
| Fabric API | P7dR8mSH | API officielle Fabric |
| Sodium | AANobbMI | Optimisation rendu |
| Lithium | gvQqBUqZ | Optimisation serveur |
| FerriteCore | uXXizFIs | Réduction RAM |
| Entity Culling | NNAgCjsB | Culling entités |
| Iris | YL57xq9U | Shaders |
| Better Combat | 5sy6g3kz | Combat amélioré |
| Cloth Config | 9s6osm5g | Config API |
| Mod Menu | mOgUt4GM | Menu des mods |
| Dynamic FPS | LQ3K71Q1 | FPS dynamique |
| Mouse Tweaks | aC3cM3Vq | Améliorations souris |
| No Chat Reports | qQyHxfxd | Désactive signalement |
| Reese's Sodium Options | Bh37bMuy | Options Sodium |
| C2ME | VSNURh3q | Optimisation chunks |
| LazyDFU | hvFnDODi | Chargement lazy |

## Corrections appliquées

### ✅ Problème 1: devMode = true
- **Solution**: Changé à `false` dans distromanager.js
- **Fichier**: [distromanager.js](app/assets/js/distromanager.js#L15)

### ✅ Problème 2: IDs non-Maven
- **Solution**: Tous les IDs sont maintenant au format `maven.modrinth:<project>:<version>`
- **Fichier**: [distribution.json](distribution.json)

### ✅ Problème 3: VersionManifest manquant
- **Solution**: Ajouté module VersionManifest pour chaque Fabric Loader
- **Format**: `1.21.1-0.15.11@net.fabricmc:fabric-loader`

### ✅ Problème 4: MD5 vides
- **Solution**: Calculé et ajouté les MD5 pour Fabric Loader (1327691 bytes, MD5: b49fcf1bafc14bcda2472a0f060ebe41)

### ⚠️ URLs LOCAL: restantes
Les 15 mods du client PVP utilisent toujours LOCAL:
- Avantage: Pas de téléchargement, installation rapide
- Inconvénient: Doit être packagé avec le launcher
- Alternative: Héberger sur un CDN/serveur

## Test

Pour tester le launcher:
```bash
npm start
```

Si vous voyez "No mod loader version manifest module found!", vérifiez que le module VersionManifest est bien présent dans distribution.json.
