# TASK-06

## ID
TASK-06

## Objectif
Créer l’entité User, le rôle utilisateur, le statut de compte et le repository.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package user :
  - enum UserRole avec ADMIN et RESIDENT
  - enum AccountStatus avec PENDING, APPROVED, REJECTED
  - entité User
  - UserRepository
- Champs minimum de User :
  - id
  - residence (ManyToOne vers Residence)
  - pseudo
  - email
  - phone
  - passwordHash
  - role
  - emailVisible
  - phoneVisible
  - enabled
  - accountStatus
  - createdAt
- Ajouter des contraintes d’unicité utiles si raisonnable
- Utiliser des noms cohérents : passwordHash et pas password en clair
- Ne pas encore créer les endpoints utilisateurs

## Livrables
- User.java
- UserRole.java
- AccountStatus.java
- UserRepository.java

## Critères d'acceptation
- la relation User -> Residence compile
- les enums compile
- le repository compile

## Tests
- cd backend
- ./mvnw test

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
