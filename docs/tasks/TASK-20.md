# TASK-20

## ID
TASK-20

## Objectif
Créer le module notifications internes en base et exposer les endpoints de consultation.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package notification :
  - entité Notification
  - NotificationRepository
  - NotificationService
  - NotificationController
  - DTO nécessaires
- Champs minimum :
  - id
  - residence
  - user
  - title
  - message
  - type
  - read
  - createdAt
- Ajouter les endpoints :
  - GET /notifications
  - PUT /notifications/{id}/read
  - POST /admin/notifications/broadcast
- Prévoir une méthode simple pour créer une notification à toute la résidence
- Règles :
  - filtrage par résidence / utilisateur
  - seul ADMIN peut diffuser

## Livrables
- module notifications complet

## Critères d'acceptation
- liste des notifications OK
- marquer comme lu OK
- broadcast admin OK

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
