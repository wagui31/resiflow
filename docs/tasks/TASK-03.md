# TASK-03

## ID
TASK-03

## Objectif
Créer la structure des packages backend pour organiser proprement le projet Spring Boot.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Aller dans backend/src/main/java/com/resiflow/
- Créer les packages suivants :
  - auth
  - security
  - user
  - residence
  - invitation
  - wallet
  - incident
  - event
  - vote
  - notification
  - common
- Créer aussi la même logique de packages de test sous backend/src/test/java/com/resiflow/ si utile
- Ne pas créer encore les classes métier détaillées dans cette tâche
- Vérifier que le projet compile toujours

## Livrables
- Arborescence des packages backend propre

## Critères d'acceptation
- tous les packages existent
- le projet compile toujours

## Tests
- cd backend
- ./mvnw test
- find src/main/java/com/resiflow -type d | sort

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
