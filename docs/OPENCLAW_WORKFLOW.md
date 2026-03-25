# OPENCLAW_WORKFLOW.md

## Objectif
Utiliser OpenClaw comme exécutant discipliné, sans dépendre de sa mémoire de session.

---

## Principe fondamental
Ne jamais compter sur le contexte mémoire d'OpenClaw.
Tout le contexte doit être stocké dans des fichiers .md.

---

## Arborescence recommandée

```text
residence-app/
 ├── backend/
 ├── mobile/
 └── docs/
      ├── PROJECT_CONTEXT.md
      ├── ARCHITECTURE.md
      ├── DECISIONS.md
      ├── BACKLOG.md
      ├── CURRENT_TASK.md
      ├── DONE.md
      └── NEXT_STEP.md
```

---

## Méthode de travail détaillée

### Étape 1 - Préparer le dossier projet
Créer ton dépôt projet avec:
- backend/
- mobile/
- docs/

Copier tous les fichiers markdown dans docs/.

### Étape 2 - Fixer une seule tâche active
Ne jamais demander à OpenClaw:
- de faire tout le projet
- de faire plusieurs grosses fonctionnalités en même temps

Toujours travailler avec une seule tâche dans CURRENT_TASK.md.

### Étape 3 - Prompt standard à envoyer à OpenClaw
Utiliser ce prompt à chaque démarrage ou redémarrage:

```text
Lis obligatoirement les fichiers suivants avant toute action:
- docs/PROJECT_CONTEXT.md
- docs/ARCHITECTURE.md
- docs/DECISIONS.md
- docs/CURRENT_TASK.md
- docs/DONE.md
- docs/NEXT_STEP.md

Réalise uniquement la tâche décrite dans docs/CURRENT_TASK.md.
Ne fais aucune autre tâche.
Respecte strictement le périmètre.
À la fin:
1. résume ce qui a été fait
2. mets à jour docs/DONE.md
3. mets à jour docs/NEXT_STEP.md si nécessaire
4. liste les fichiers créés ou modifiés
5. propose les commandes de test
```

### Étape 4 - Vérifier le résultat
Quand OpenClaw termine:
- tu relis le code
- tu testes
- tu fais un commit Git

### Étape 5 - Mettre à jour la tâche suivante
Une fois la tâche validée:
- déplacer la tâche terminée dans DONE.md
- remplacer CURRENT_TASK.md par la prochaine tâche
- ajuster NEXT_STEP.md

### Étape 6 - Reprendre après redémarrage OpenClaw
Si OpenClaw redémarre et perd le contexte:
- ne pas réexpliquer tout à la main
- lui renvoyer le prompt standard
- lui demander de relire tous les fichiers docs/

Le contexte est alors reconstruit depuis les fichiers.

---

## Rythme conseillé
- 1 tâche = 1 commit Git
- 1 tâche = 1 livrable testable
- éviter les sessions trop longues

---

## Bonnes pratiques
- toujours préciser le périmètre
- toujours demander une liste des fichiers modifiés
- toujours demander les commandes pour tester
- toujours relire et commiter avant la tâche suivante

---

## Format conseillé pour une tâche détaillée

```text
ID: TASK-X.Y
Titre: ...
Objectif: ...
Périmètre: ...
Entrées: ...
Règles: ...
Livrables: ...
Critères d'acceptation: ...
```

---

## Exemple de reprise après crash

```text
OpenClaw a redémarré.
Relis complètement:
- docs/PROJECT_CONTEXT.md
- docs/ARCHITECTURE.md
- docs/DECISIONS.md
- docs/CURRENT_TASK.md
- docs/DONE.md
- docs/NEXT_STEP.md

Ensuite continue uniquement la tâche active.
Ne fais rien en dehors du périmètre.
À la fin donne:
- résumé
- fichiers modifiés
- commandes de test
```

---

## Discipline de versionnement Git
Après chaque tâche:

```bash
git add .
git commit -m "TASK-0.1 init spring boot backend and health endpoint"
```

Cela évite toute perte si:
- OpenClaw redémarre
- la machine plante
- une mauvaise modification casse le projet
