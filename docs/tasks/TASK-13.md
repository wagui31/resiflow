# TASK-13

## ID
TASK-13

## Objectif
Créer l’endpoint permettant de lire une invitation via son token et l’endpoint d’inscription via invitation.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Ajouter :
  - GET /invitations/{token}
  - POST /auth/register-from-invitation
- Créer les DTO nécessaires
- Lors de l’inscription :
  - vérifier token valide
  - vérifier invitation non expirée
  - créer un User rattaché à la bonne résidence
  - hasher le mot de passe
  - accountStatus = PENDING
  - enabled = false si validation admin requise
  - marquer l’invitation comme ACCEPTED
- Ne pas connecter encore le mobile

## Livrables
- endpoint lecture invitation
- endpoint inscription via invitation
- DTO associés

## Critères d'acceptation
- le token peut être lu
- un utilisateur est créé via l’invitation
- le mot de passe est hashé

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
