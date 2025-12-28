# Guide de Contribution

Merci de votre intérêt pour contribuer au **Minecraft Educational Launcher** ! 

Ce projet est avant tout **pédagogique**. Toute contribution doit respecter cet esprit d'apprentissage.

## 🎯 Philosophie du projet

1. **Éducatif avant tout** : Le code doit être lisible et bien commenté
2. **100% légal** : Uniquement via les APIs officielles Mojang
3. **Aucun crack/piratage** : Ce n'est pas négociable
4. **Code propre** : Architecture claire, patterns reconnus

## 🚀 Comment contribuer ?

### 1. Issues (Problèmes & Suggestions)

Avant de créer une issue, vérifiez qu'elle n'existe pas déjà.

**Pour un bug** :
```
Titre : [BUG] Description courte

Description :
- Version du launcher : 2.0.0
- Système d'exploitation : macOS Sonoma 14.2
- Version Java : 17.0.5
- Étapes pour reproduire :
  1. Lancer le launcher
  2. Sélectionner la version 1.20.4
  3. Cliquer sur "Singleplayer"
- Comportement attendu : Le jeu devrait démarrer
- Comportement observé : Erreur "ClassNotFoundException"
- Logs : [copier les logs pertinents]
```

**Pour une suggestion** :
```
Titre : [FEATURE] Description courte

Description :
- Fonctionnalité demandée : Support de Forge
- Cas d'usage : Permettre de jouer avec des mods
- Bénéfice éducatif : Apprendre la gestion des modloaders
```

### 2. Pull Requests

#### Avant de commencer

1. **Fork** le repository
2. Créez une **branche** depuis `main` :
   ```bash
   git checkout -b feature/ma-fonctionnalite
   ```
3. **Communiquez** votre intention dans une issue

#### Structure d'une PR

```markdown
## Description

Brève description de ce que fait cette PR.

## Type de changement

- [ ] Bug fix
- [ ] Nouvelle fonctionnalité
- [ ] Amélioration
- [ ] Documentation

## Checklist

- [ ] Le code compile sans erreur
- [ ] Les commentaires sont en français
- [ ] Le code suit l'architecture existante
- [ ] Les nouvelles classes ont une JavaDoc
- [ ] Testé sur au moins une version de Minecraft
- [ ] README.md mis à jour si nécessaire

## Tests effectués

- OS : macOS
- Java : 17.0.5
- Versions Minecraft testées : 1.20.4, 1.19.2
- Résultat : ✅ Le jeu démarre correctement
```

#### Standards de code

**Java** :
- Suivre les conventions Java standard
- Classes : `PascalCase`
- Méthodes/variables : `camelCase`
- Constantes : `UPPER_SNAKE_CASE`

**Commentaires** :
```java
/**
 * Description de la classe en français.
 * 
 * Explication pédagogique du rôle de la classe.
 */
public class MaClasse {
    
    /**
     * Description de la méthode.
     * 
     * @param param Description du paramètre
     * @return Description du retour
     */
    public String maMethode(String param) {
        // Commentaire inline pour expliquer une ligne complexe
        return param.toUpperCase();
    }
}
```

**Architecture** :
- Respecter la structure MVC existante
- Une classe = une responsabilité
- Packages logiques :
  - `minecraft/` : logique Minecraft
  - `ui/` : interface utilisateur
  - `utils/` : utilitaires génériques

## 🎓 Idées de contributions

### Débutant

- 🐛 Corriger des fautes de frappe dans la documentation
- 📝 Améliorer les commentaires existants
- 🎨 Améliorer l'interface (couleurs, disposition)
- 🌍 Ajouter une langue dans l'interface

### Intermédiaire

- ⚙️ Ajouter une option "Valider les fichiers"
- 📊 Afficher les statistiques de téléchargement
- 🔍 Ajouter un filtre de recherche dans la liste des versions
- 💾 Sauvegarder les préférences utilisateur

### Avancé

- 🔧 Support de Forge
- 🧵 Support de Fabric
- 🔐 Authentification Microsoft (OAuth2)
- 📦 Gestionnaire de mods
- 🎮 Profils multiples

## ⚠️ Ce qui n'est PAS accepté

❌ **Code de crack ou bypass**
- Contournement de l'authentification
- Modification des fichiers Minecraft
- Bypass de protection DRM

❌ **Code non éducatif**
- Code obfusqué volontairement
- Absence de commentaires sur du code complexe
- Architecture incompréhensible

❌ **Redistribution de fichiers Mojang**
- JAR Minecraft
- Libraries propriétaires
- Assets du jeu

## 🧪 Tester localement

```bash
# Cloner votre fork
git clone https://github.com/EspritFurifX/Nemesis-client.git
cd Nemesis-client/Launcher

# Compiler
mvn clean package

# Lancer
mvn javafx:run
```

## 📜 Licence

En contribuant, vous acceptez que votre code soit publié sous la même licence que le projet (MIT éducative).

## 🤝 Code de conduite

- Soyez respectueux et bienveillant
- Acceptez les critiques constructives
- Privilégiez l'apprentissage et le partage
- Respectez la propriété intellectuelle de Mojang/Microsoft

## 💬 Questions ?

Si vous avez des questions avant de contribuer :
1. Consultez le [README.md](README.md)
2. Regardez les [Issues existantes](../../issues)
3. Créez une nouvelle issue avec le tag `[QUESTION]`

---

**Merci de contribuer à ce projet éducatif ! 🎓**
