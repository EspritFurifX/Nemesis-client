# Configuration Minecraft 1.21 pour Nemesis Launcher

**Date:** 29/12/2025  
**Version cible:** Minecraft 1.21  
**Loader:** Fabric 0.15.11  
**Java:** 21 LTS

---

## ✅ Décision stratégique validée

### Pourquoi Minecraft 1.21 ?

**Avantages techniques :**
- ✅ Dernière version stable officielle Mojang
- ✅ Support Microsoft/Mojang actif
- ✅ Java 21 LTS (Long Term Support)
- ✅ Meilleures performances natives
- ✅ Sécurité améliorée

**Avantages écosystème :**
- ✅ Mods Fabric/Quilt actifs et maintenus
- ✅ Forge disponible si besoin
- ✅ Datapacks modernes compatibles
- ✅ Resource packs récents supportés
- ✅ Communauté active

**Avantages image/projet :**
- ✅ Projet moderne et crédible
- ✅ Attire nouveaux joueurs
- ✅ Vision long terme
- ✅ Évite l'image "serveur cheat/PvP oldschool"

### ❌ Pourquoi abandonner la 1.8.9 ?

- ❌ Techniquement obsolète (2014)
- ❌ API Mojang dépassée
- ❌ Mods modernes incompatibles
- ❌ Java récent bridé inutilement
- ❌ Image négative (PvP/cheat)
- ❌ Aucun projet sérieux ne démarre en 1.8 aujourd'hui

---

## 📋 Configuration distribution.json

### Structure principale

```json
{
  "id": "nemesis-1.21",
  "name": "Nemesis - Minecraft 1.21",
  "description": "Version officielle Minecraft 1.21 avec Fabric",
  "minecraftVersion": "1.21",
  "mainServer": true,
  "autoconnect": true,
  "javaOptions": {
    "supported": ">=21",
    "suggestedMajor": 21,
    "ram": {
      "recommended": 4096,
      "minimum": 2048
    }
  }
}
```

### Loader : Fabric (recommandé)

**Pourquoi Fabric ?**
- Plus léger que Forge
- Démarrage plus rapide
- Mods optimisés
- Mises à jour rapides
- Compatible Quilt

**Configuration :**
```json
{
  "id": "net.fabricmc:fabric-loader:0.15.11",
  "name": "Fabric Loader 0.15.11",
  "type": "Fabric",
  "subModules": [
    {
      "id": "net.fabricmc:fabric-api:0.100.0+1.21",
      "name": "Fabric API 0.100.0",
      "type": "FabricMod"
    }
  ]
}
```

---

## 🔧 Configuration Java

### Java 21 LTS

**Téléchargement automatique :**
- Helios télécharge automatiquement Java 21
- Pas besoin d'installation système
- Isolation par launcher
- Optimisé pour Minecraft 1.21

**Paramètres recommandés :**
```json
{
  "supported": ">=21",
  "suggestedMajor": 21,
  "ram": {
    "recommended": 4096,
    "minimum": 2048
  }
}
```

**Arguments JVM optimisés** (à ajouter) :
```
-XX:+UnlockExperimentalVMOptions
-XX:+UseG1GC
-XX:G1NewSizePercent=20
-XX:G1ReservePercent=20
-XX:MaxGCPauseMillis=50
-XX:G1HeapRegionSize=32M
```

---

## 📦 Mods recommandés (Fabric)

### Performance
- **Sodium** - Optimisation graphique (jusqu'à +400% FPS)
- **Lithium** - Optimisation serveur/game logic
- **Starlight** - Optimisation éclairage
- **FerriteCore** - Réduction RAM

### Qualité de vie
- **Mod Menu** - Interface gestion mods
- **Roughly Enough Items (REI)** - Recettes
- **AppleSkin** - Affichage faim/saturation
- **Xaero's Minimap** - Minimap

### Cosmétiques (optionnel)
- **Iris Shaders** - Support shaders
- **Continuity** - Connected textures
- **LambDynamicLights** - Lumières dynamiques

---

## 🚀 Migration depuis l'ancienne version

### Étapes de migration

1. **Backup** des anciennes données
   ```bash
   cp -r ~/.minecraft/nemesis ~/.minecraft/nemesis_backup_1.8.9
   ```

2. **Mise à jour distribution.json**
   - Remplacer mainServer par la 1.21
   - Marquer 1.8.9 comme "Legacy"

3. **Test complet**
   - Lancement launcher
   - Téléchargement Java 21
   - Installation Fabric
   - Test connexion serveur

4. **Communication joueurs**
   - Annoncer le changement
   - Expliquer les avantages
   - Guide de migration

### Compatibilité anciennes versions

La 1.8.9 reste disponible mais :
- ❌ Non recommandée
- ❌ Plus le serveur principal
- ❌ Autoconnect désactivé
- ⚠️ Marquée "Legacy"

---

## 📊 Spécifications techniques

### Recommandations système

**Minimum :**
- CPU: Intel Core i3 / AMD Ryzen 3
- RAM: 4 GB (2 GB alloués Minecraft)
- GPU: Intégré moderne
- Stockage: 5 GB

**Recommandé :**
- CPU: Intel Core i5 / AMD Ryzen 5
- RAM: 8 GB (4 GB alloués Minecraft)
- GPU: Dédié (GTX 1050+)
- Stockage: 10 GB SSD

**Optimal :**
- CPU: Intel Core i7 / AMD Ryzen 7
- RAM: 16 GB (6-8 GB alloués Minecraft)
- GPU: RTX 3060+ / RX 6600+
- Stockage: 20 GB NVMe

---

## 🎮 Avantages pour les joueurs

### Performances
- +50% à +400% FPS avec Sodium
- Temps de chargement divisé par 2
- Moins de lag
- Support shaders natif amélioré

### Contenu
- Nouveaux biomes (1.18+)
- Nouvelles structures (1.19+)
- Ancient City, Deep Dark
- Mangrove, Cherry Blossom
- Archeology (1.20+)
- Armadillos, nouvelles armures (1.21)

### Expérience
- Interface améliorée
- Chat modéré
- Meilleure accessibilité
- Support multi-langues complet

---

## 🛠️ Dépannage

### Java 21 ne s'installe pas

**Solution :**
1. Vérifier connexion internet
2. Supprimer `~/.minecraft/runtime`
3. Relancer launcher
4. Java se télécharge automatiquement

### Mod incompatible

**Solution :**
1. Vérifier version mod (doit être 1.21)
2. Vérifier loader (Fabric vs Forge)
3. Vérifier dépendances (Fabric API)

### Performances faibles

**Solutions :**
1. Installer Sodium + Lithium + Starlight
2. Réduire distance rendu
3. Désactiver V-Sync
4. Allouer plus de RAM (4-6 GB)

---

## 📅 Roadmap

### Court terme (Janvier 2025)
- [x] Configuration 1.21 validée
- [ ] Tests complets
- [ ] Documentation joueurs
- [ ] Annonce migration

### Moyen terme (T1 2025)
- [ ] Pack de mods optimisé
- [ ] Profiles personnalisés
- [ ] Support Forge optionnel

### Long terme (T2 2025)
- [ ] Mods serveur custom
- [ ] Système de cosmétiques
- [ ] API plugins

---

## 🔗 Ressources utiles

**Fabric :**
- Site officiel: https://fabricmc.net/
- Wiki: https://fabricmc.net/wiki/
- Mods: https://modrinth.com/mods?l=fabric

**Minecraft 1.21 :**
- Release notes: https://www.minecraft.net/article/minecraft-java-edition-1-21
- Wiki: https://minecraft.fandom.com/wiki/Java_Edition_1.21

**Java 21 :**
- Documentation: https://docs.oracle.com/en/java/javase/21/
- Téléchargement: https://adoptium.net/

---

**Responsable:** EspritFurifX  
**Dernière mise à jour:** 29/12/2025  
**Statut:** Configuration validée ✅
