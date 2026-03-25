# TASK-10

## ID
TASK-10

## Objectif
Implémenter l’infrastructure JWT de base.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package security :
  - JwtService
  - JwtAuthenticationFilter
- Prévoir :
  - génération d’un token
  - extraction du username ou email
  - validation du token
- Ajouter dans application.yml une section de configuration pour un secret JWT placeholder
- Ne pas encore finaliser toutes les règles métier d’authentification
- Conserver le code lisible et minimal

## Livrables
- JwtService.java
- JwtAuthenticationFilter.java
- config JWT dans application.yml

## Critères d'acceptation
- le projet compile
- le service JWT peut être utilisé par l’authentification

## Tests
- cd backend
- ./mvnw test

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
