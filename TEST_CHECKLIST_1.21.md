# 🧪 Checklist Tests - Nemesis Launcher 1.21

**Date:** 29/12/2025  
**Version:** 1.0.0  
**Configuration:** Minecraft 1.21 + Fabric + Java 21

---

## 🎯 Objectif

Valider le flow complet du launcher avec la nouvelle configuration Minecraft 1.21.

---

## ✅ Phase 1 : Tests de base

### 1.1 Lancement launcher
- [ ] Le launcher démarre sans erreur
- [ ] L'interface s'affiche correctement
- [ ] Les logos sont présents (seal.png)
- [ ] Le background est correct
- [ ] Aucune erreur console

**Notes :**
```
Commande : npm start
```

---

### 1.2 Affichage version
- [ ] Version Minecraft affichée : **1.21**
- [ ] Loader affiché : **Fabric 0.15.11**
- [ ] Java version : **21 LTS**
- [ ] Serveur principal : **Nemesis - Minecraft 1.21**

**Emplacement UI :**
- Écran principal (landing)
- Section paramètres (settings)

---

### 1.3 Authentification
- [ ] Connexion Microsoft fonctionne
- [ ] Avatar joueur s'affiche
- [ ] Pseudo récupéré
- [ ] Token valide

---

## 🔧 Phase 2 : Téléchargement & Installation

### 2.1 Java 21
- [ ] Détection : Java 21 non présent
- [ ] Téléchargement automatique démarré
- [ ] Progression affichée
- [ ] Installation réussie
- [ ] Vérification : `java -version` → 21

**Chemin attendu :**
```
~/.minecraft/runtime/java-runtime-gamma/
```

---

### 2.2 Fabric Loader
- [ ] Téléchargement Fabric 0.15.11
- [ ] Installation dans le profil
- [ ] Fabric API téléchargée
- [ ] Mods folder créé

**Chemin attendu :**
```
~/.minecraft/versions/fabric-loader-0.15.11-1.21/
```

---

### 2.3 Assets Minecraft
- [ ] Téléchargement assets 1.21
- [ ] Librairies 1.21
- [ ] Fichiers natifs
- [ ] Vérification intégrité (MD5)

**Logs à vérifier :**
```
[INFO] Downloading Minecraft 1.21
[INFO] Downloading libraries
[INFO] Verifying assets
```

---

## 🚀 Phase 3 : Lancement Minecraft

### 3.1 Démarrage
- [ ] Bouton "Jouer" cliquable
- [ ] Progression affichée
- [ ] Logs en temps réel
- [ ] Aucune erreur critique

**Temps attendu :** < 30 secondes (premier lancement)

---

### 3.2 Fenêtre Minecraft
- [ ] Minecraft 1.21 démarre
- [ ] Écran titre correct
- [ ] Version affichée : **1.21**
- [ ] Mods Fabric chargés
- [ ] Fabric API présent

**Vérifier dans les logs :**
```
[Fabric] Loading Fabric Loader 0.15.11
[Fabric] Loading mods: fabric-api-0.100.0+1.21
```

---

### 3.3 Performance
- [ ] FPS stable (>60)
- [ ] RAM utilisée : ~2-3 GB
- [ ] Temps de chargement world : <10s
- [ ] Pas de freeze

**Commande F3 dans Minecraft :**
- Java: 21.x.x
- Fabric Loader: 0.15.11
- Mods: X loaded

---

## 🔌 Phase 4 : Connexion serveur

### 4.1 Serveur test
- [ ] Ajout serveur : `play.nemesis.fr`
- [ ] Ping serveur correct
- [ ] Connexion établie
- [ ] Pas de kick (version mismatch)

**Si serveur pas encore en 1.21 :**
- Tester sur serveur public 1.21
- Ou lancer en solo

---

## 🛠️ Phase 5 : Fonctionnalités launcher

### 5.1 Paramètres
- [ ] Onglet paramètres accessible
- [ ] RAM ajustable (2-8 GB)
- [ ] Résolution modifiable
- [ ] Thème sombre/clair

---

### 5.2 Profils
- [ ] Création nouveau profil
- [ ] Sélection version (1.21 par défaut)
- [ ] Sauvegarde paramètres
- [ ] Switch entre profils

---

### 5.3 Logs
- [ ] Logs launcher accessibles
- [ ] Logs Minecraft visibles
- [ ] Copie logs possible
- [ ] Export logs pour debug

---

## ⚠️ Phase 6 : Tests d'erreur

### 6.1 Scénarios d'échec
- [ ] Sans internet → Message clair
- [ ] Java manquant → Téléchargement auto
- [ ] Fichier corrompu → Re-téléchargement
- [ ] RAM insuffisante → Alerte

---

### 6.2 Recovery
- [ ] Bouton "Réparer installation"
- [ ] Re-vérification fichiers
- [ ] Nettoyage cache
- [ ] Reset paramètres

---

## 📊 Phase 7 : Performance globale

### 7.1 Benchmarks
- [ ] RAM launcher : <200 MB
- [ ] CPU idle : <5%
- [ ] Temps démarrage total : <60s
- [ ] Taille installation : ~500 MB

---

### 7.2 Stabilité
- [ ] 5 lancements consécutifs OK
- [ ] Pas de memory leak
- [ ] Fermeture propre
- [ ] Logs clean

---

## 🎮 Phase 8 : Expérience utilisateur

### 8.1 UI/UX
- [ ] Interface réactive
- [ ] Animations fluides
- [ ] Textes lisibles
- [ ] Boutons cliquables

---

### 8.2 Feedback
- [ ] Messages d'état clairs
- [ ] Progression visible
- [ ] Erreurs explicites
- [ ] Tooltips présents

---

## 📝 Résumé des résultats

### ✅ Tests réussis
```
Total : __/50
Critiques : __/25
Optionnels : __/25
```

### ❌ Tests échoués
```
Bugs critiques : __
Bugs mineurs : __
```

### 🐛 Bugs identifiés

| ID | Sévérité | Description | Status |
|----|----------|-------------|--------|
| 1  |          |             |        |
| 2  |          |             |        |

---

## 🚦 Décision Go/No-Go

### ✅ GO si :
- [x] Tous les tests critiques passent
- [x] Aucun bug bloquant
- [x] Performance acceptable

### ❌ NO-GO si :
- [ ] Java 21 ne se télécharge pas
- [ ] Minecraft ne démarre pas
- [ ] Crash systématique

---

## 🔄 Actions correctives

### Priorité HAUTE
1. 
2. 
3. 

### Priorité MOYENNE
1. 
2. 

### Priorité BASSE
1. 
2. 

---

## 📅 Planning

**Tests prévus :** __/__/2025  
**Tests effectués :** __/__/2025  
**Validation :** __/__/2025  
**Release :** __/__/2025

---

**Testeur :** EspritFurifX  
**Version launcher :** 1.0.0  
**Environnement :** macOS / Windows / Linux
