# TASK-04

## ID
TASK-04

## Objectif
Mettre en place la gestion globale des erreurs backend avec une exception métier et un handler centralisé.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package common :
  - une classe BusinessException
  - un objet de réponse d’erreur JSON simple
  - un GlobalExceptionHandler avec @RestControllerAdvice
- Gérer au minimum :
  - BusinessException
  - MethodArgumentNotValidException
  - Exception générique
- Retourner une réponse JSON cohérente avec :
  - timestamp
  - status
  - error
  - message
  - path si possible
- Ne pas encore brancher des règles métier complexes

## Livrables
- BusinessException
- ErrorResponse
- GlobalExceptionHandler

## Critères d'acceptation
- le projet compile
- les exceptions gérées retournent du JSON et non une page HTML

## Tests
- cd backend
- ./mvnw test

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
