# TASK-18

## ID
TASK-18

## Objectif
Créer le module événements avec entité, service et endpoints.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package event :
  - entité Event
  - EventRepository
  - EventService
  - EventController
  - DTO nécessaires
- Champs minimum :
  - id
  - residence
  - title
  - description
  - eventDate
  - location
  - createdBy
  - createdAt
- Ajouter les endpoints :
  - POST /admin/events
  - GET /events
  - GET /events/{id}
- Règles :
  - admin crée
  - tous les résidents de la résidence consultent

## Livrables
- module événements complet

## Critères d'acceptation
- création admin OK
- liste et détail OK

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
