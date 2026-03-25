# TASK-19

## ID
TASK-19

## Objectif
Créer le module votes avec création de vote, options, réponses et résultats.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package vote :
  - enum VoteStatus
  - entité Vote
  - entité VoteOption
  - entité VoteResponse
  - repositories
  - VoteService
  - VoteController
  - DTO nécessaires
- Ajouter les endpoints :
  - POST /admin/votes
  - GET /votes
  - GET /votes/{id}
  - POST /votes/{id}/respond
- Règles :
  - admin crée un vote avec options
  - un résident ne vote qu’une seule fois
  - données filtrées par résidence
  - exposer les résultats calculés

## Livrables
- module votes complet

## Critères d'acceptation
- création du vote OK
- réponse d’un résident OK
- double vote bloqué
- résultats consultables

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
