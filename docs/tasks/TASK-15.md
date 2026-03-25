# TASK-15

## ID
TASK-15

## Objectif
Créer le module profil utilisateur avec lecture et modification du profil courant.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package user :
  - UserProfileResponse DTO
  - UpdateUserProfileRequest DTO
  - UpdatePrivacyRequest DTO
  - UserController
  - UserService si nécessaire
- Ajouter les endpoints :
  - GET /users/me
  - PUT /users/me
  - PUT /users/me/privacy
- Permettre au résident de :
  - modifier pseudo
  - modifier email
  - modifier téléphone
  - modifier visibilité email / téléphone
- Appliquer la sécurité sur l’utilisateur connecté

## Livrables
- endpoints profil
- DTO associés

## Critères d'acceptation
- l’utilisateur connecté peut voir son profil
- il peut le modifier
- il peut modifier sa confidentialité

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
