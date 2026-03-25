# TASK-12

## ID
TASK-12

## Objectif
Créer l’endpoint admin pour générer une invitation.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package invitation :
  - CreateInvitationRequest DTO
  - InvitationResponse DTO
  - InvitationController
  - InvitationService
- Ajouter l’endpoint :
  - POST /admin/invitations
- Règles :
  - seul un ADMIN authentifié peut inviter
  - l’invitation appartient à la résidence de l’admin
  - générer un token unique
  - statut initial = PENDING
  - expiration = maintenant + 7 jours
- Ne pas encore envoyer d’email réel

## Livrables
- Controller, service, DTO
- invitation persistée

## Critères d'acceptation
- l’endpoint existe
- le token est généré
- une invitation est créée

## Tests
- cd backend
- ./mvnw test
- fournir exemple curl du endpoint

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
