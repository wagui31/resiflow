# TASK-14

## ID
TASK-14

## Objectif
Créer les endpoints admin pour approuver ou refuser un compte utilisateur en attente.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package user :
  - AdminUserController ou équivalent
  - UserApprovalService ou équivalent
- Ajouter les endpoints :
  - GET /admin/users/pending
  - POST /admin/users/{id}/approve
  - POST /admin/users/{id}/reject
- Règles :
  - seul ADMIN
  - uniquement sur la même résidence
  - approve => accountStatus APPROVED et enabled true
  - reject => accountStatus REJECTED

## Livrables
- endpoints de validation admin
- service associé

## Critères d'acceptation
- la liste des comptes en attente fonctionne
- approve et reject fonctionnent

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
