# TASK-11

## ID
TASK-11

## Objectif
Créer les DTO d’authentification et l’endpoint POST /auth/login.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package auth :
  - LoginRequest DTO
  - LoginResponse DTO
  - AuthController
  - AuthService
- Le login peut utiliser l’email comme identifiant
- Si l’authentification réussit, retourner un JWT
- Si elle échoue, retourner une erreur propre
- Ne pas encore implémenter l’inscription dans cette tâche

## Livrables
- AuthController.java
- AuthService.java
- DTO de login

## Critères d'acceptation
- POST /auth/login existe
- réponse JWT en cas de succès
- erreur propre en cas d’échec

## Tests
- cd backend
- ./mvnw test
- fournir exemple de requête curl de login

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
