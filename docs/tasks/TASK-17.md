# TASK-17

## ID
TASK-17

## Objectif
Créer le module signalements avec entité, service et endpoints.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package incident :
  - enum IncidentStatus
  - enum IncidentPriority
  - entité IncidentReport
  - IncidentRepository
  - IncidentService
  - IncidentController
  - DTO nécessaires
- Champs minimum :
  - id
  - residence
  - createdBy
  - title
  - description
  - status
  - priority
  - photoUrl
  - createdAt
  - updatedAt
- Ajouter les endpoints :
  - POST /incidents
  - GET /incidents
  - GET /incidents/{id}
  - PUT /admin/incidents/{id}/status
- Règles :
  - résident crée un signalement dans sa résidence
  - admin peut changer le statut

## Livrables
- module signalements complet

## Critères d'acceptation
- création signalement OK
- liste filtrée par résidence
- changement de statut admin OK

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
