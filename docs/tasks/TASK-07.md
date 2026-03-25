# TASK-07

## ID
TASK-07

## Objectif
Créer l’entité Invitation et son repository pour gérer l’inscription sur invitation.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package invitation :
  - enum InvitationStatus avec PENDING, ACCEPTED, EXPIRED, CANCELLED
  - entité Invitation
  - InvitationRepository
- Champs minimum :
  - id
  - residence (ManyToOne)
  - targetValue (email ou téléphone)
  - token
  - status
  - expiresAt
  - createdAt
  - createdBy (ManyToOne vers User)
- Le token doit être stocké en String
- Ne pas encore créer de controller

## Livrables
- Invitation.java
- InvitationStatus.java
- InvitationRepository.java

## Critères d'acceptation
- l’entité compile
- le repository compile

## Tests
- cd backend
- ./mvnw test

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
