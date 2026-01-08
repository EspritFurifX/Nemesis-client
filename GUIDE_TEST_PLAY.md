# 🎮 GUIDE TEST PLAY - Minecraft 1.21

**Date:** 29/12/2025  
**Objectif:** Tester le flow complet Java 21 → Fabric → Minecraft 1.21

---

## ⚡ ÉTAPES À SUIVRE (ORDRE STRICT)

### 1️⃣ AVANT DE CLIQUER PLAY

**Vérifie que:**
- ✅ Launcher ouvert
- ✅ Authentifié (profil visible)
- ✅ Serveur sélectionné = "Nemesis"
- ✅ Affichage = "Minecraft 1.21 • Fabric • Java 21"

---

### 2️⃣ CLIQUER SUR "PLAY"

**Ce qui DOIT se passer:**

#### Phase A - Vérification Java 21
```
[1-10 secondes]
→ "Recherche de Java 21..."
→ Si absent: "Téléchargement de Java 21..."
→ Barre de progression (peut prendre 2-5 min)
```

**Où observer:**
- Zone centrale du launcher (barre progression)
- Console Electron (si ouverte avec Dev Tools)

**Logs attendus:**
```
[INFO] Validating Java installation...
[INFO] Java 21 not found
[INFO] Downloading Java 21...
[INFO] Extracting Java 21...
[INFO] Java 21 ready
```

---

#### Phase B - Téléchargement Assets Minecraft 1.21
```
[30 secondes - 3 minutes selon connexion]
→ "Téléchargement des assets Minecraft..."
→ "Téléchargement des bibliothèques..."
→ Progression 0% → 100%
```

**Taille approximative:**
- Minecraft 1.21 client: ~15 MB
- Librairies: ~100 MB
- Assets (sons, textures): ~400 MB
- **Total: ~500-600 MB**

**Logs attendus:**
```
[INFO] Validating Minecraft 1.21 installation
[INFO] Downloading minecraft-client-1.21.jar
[INFO] Downloading libraries...
[INFO] Verifying assets...
```

---

#### Phase C - Installation Fabric
```
[10-20 secondes]
→ "Installation de Fabric Loader 0.15.11..."
→ "Téléchargement de Fabric API..."
```

**Fichiers créés:**
```
~/.minecraft/versions/fabric-loader-0.15.11-1.21/
~/.minecraft/mods/fabric-api-0.100.0+1.21.jar
```

**Logs attendus:**
```
[INFO] Installing Fabric Loader 0.15.11
[INFO] Fabric installation complete
[INFO] Downloading Fabric API...
```

---

#### Phase D - Lancement Minecraft
```
[5-15 secondes]
→ "Lancement de Minecraft..."
→ Fenêtre Minecraft s'ouvre
→ Écran Mojang (logo rouge)
→ Menu principal
```

**Version affichée dans Minecraft:**
```
Minecraft 1.21
1 mod loaded (Fabric API)
```

---

## 🔍 CE QU'IL FAUT OBSERVER

### ✅ SIGNES DE SUCCÈS

**Launcher:**
- ✅ Progression fluide
- ✅ Pas de freeze
- ✅ Messages clairs
- ✅ Pas d'erreur rouge

**Minecraft:**
- ✅ Démarre en <30 secondes
- ✅ Menu principal visible
- ✅ Version 1.21 affichée en bas à gauche
- ✅ "Mods" dans le menu principal
- ✅ Clic "Mods" → Fabric API listé

**Console F3 (une fois en jeu):**
```
Java: 21.x.x
Minecraft: 1.21
Fabric Loader: 0.15.11
Mods: 1 loaded
```

---

## ❌ ERREURS POSSIBLES

### Erreur 1 - Java 21 téléchargement échoue
**Symptôme:**
```
[ERROR] Failed to download Java 21
```

**Cause probable:**
- Pas d'internet
- Firewall bloque
- URL Java invalide

**Solution:**
- Vérifier connexion internet
- Réessayer
- Vérifier logs détaillés

---

### Erreur 2 - Fabric installation échoue
**Symptôme:**
```
[ERROR] Failed to install Fabric Loader
```

**Cause probable:**
- Fabric version incompatible
- Fichier corrompu
- Conflit avec ancien mod loader

**Solution:**
- Vérifier distribution.json (Fabric 0.15.11)
- Nettoyer ~/.minecraft/versions/
- Réessayer

---

### Erreur 3 - Minecraft crash au démarrage
**Symptôme:**
```
[ERROR] Game crashed
Exit code: 1
```

**Cause probable:**
- Java version incompatible
- Mod corrompu
- RAM insuffisante

**Solution:**
- Vérifier logs crash (crash-reports/)
- Augmenter RAM (4 GB minimum)
- Vérifier Java 21 correctement installé

---

### Erreur 4 - Version mismatch
**Symptôme:**
```
Minecraft 1.19.4 démarre au lieu de 1.21
```

**Cause probable:**
- Cache ancien profil
- Distribution.json pas rechargé
- Profil wrong sélectionné

**Solution:**
- Relancer launcher
- Vérifier distribution.json
- Supprimer ~/.minecraft/launcher_profiles.json
- Réessayer

---

## 📊 CHECKLIST DE VALIDATION

### Phase Java 21
- [ ] Téléchargement démarré
- [ ] Progression affichée
- [ ] Installation réussie
- [ ] Version confirmée: `java -version` → 21

### Phase Minecraft
- [ ] Téléchargement client 1.21
- [ ] Téléchargement librairies
- [ ] Vérification assets
- [ ] Aucune erreur MD5

### Phase Fabric
- [ ] Fabric Loader 0.15.11 installé
- [ ] Fabric API téléchargée
- [ ] Dossier mods/ créé
- [ ] Fabric détecté par Minecraft

### Phase Lancement
- [ ] Minecraft démarre
- [ ] Menu principal affiché
- [ ] Version 1.21 confirmée
- [ ] Fabric fonctionne
- [ ] Aucun crash

---

## 🎯 APRÈS LE TEST

**Si TOUT OK:**
```
✅ Java 21: OK
✅ Fabric: OK
✅ MC 1.21: OK
✅ Launch: OK
```
→ **Passer à: Feature RAM Display**

**Si 1-2 bugs mineurs:**
```
⚠️ [décrire bug]
⚠️ [logs exacts]
```
→ **Debug ciblé, puis re-test**

**Si crash systématique:**
```
❌ [error exact]
❌ [exit code]
❌ [logs complets]
```
→ **Analyse crash-reports/, résolution, re-test**

---

## 📝 FORMAT RETOUR ATTENDU

**Option A - Succès complet:**
```
Java 21: OK
Fabric: OK  
MC Launch: OK
Version affichée: 1.21
Mods: Fabric API loaded
```

**Option B - Erreur:**
```
Java 21: OK
Fabric: KO
Erreur: [message exact]
Logs: [copier logs pertinents]
```

---

## ⏱️ TEMPS ESTIMÉS

**Premier lancement (installation complète):**
- Java 21: 2-5 min
- Minecraft 1.21: 3-5 min  
- Fabric: 20 sec
- Lancement: 15 sec
**Total: 6-10 minutes**

**Lancements suivants:**
- Vérifications: 10 sec
- Lancement: 15 sec
**Total: 25 secondes**

---

## 🔧 COMMANDES UTILES

**Vérifier Java installé:**
```bash
ls ~/.minecraft/runtime/
```

**Vérifier version Fabric:**
```bash
cat ~/.minecraft/versions/fabric-loader-0.15.11-1.21/fabric-loader-0.15.11-1.21.json | grep "id"
```

**Vérifier mods:**
```bash
ls ~/.minecraft/mods/
```

**Voir crash logs:**
```bash
ls -lt ~/.minecraft/crash-reports/ | head -5
```

**Nettoyer cache (si besoin):**
```bash
rm -rf ~/.minecraft/versions/fabric-*
rm -rf ~/.minecraft/mods/*
```

---

**Prêt pour le test?** 🚀  
**Clique PLAY et note TOUT ce qui se passe.**
