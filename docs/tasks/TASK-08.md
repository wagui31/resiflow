# TASK-08

## ID
TASK-08

## Objectif
Mettre en place la configuration Spring Security de base.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Mettre à jour ou créer SecurityConfig dans le package security
- Autoriser sans authentification :
  - /health
  - /auth/**
- Exiger authentification pour le reste
- Désactiver CSRF pour API REST
- Configurer une politique stateless adaptée à JWT
- Ne pas encore finaliser le filtre JWT si ce n’est pas prêt

## Livrables
- SecurityConfig propre

## Critères d'acceptation
- /health reste accessible
- le reste est protégé selon la config

## Tests
- cd backend
- ./mvnw test
- ./mvnw spring-boot:run
- curl http://localhost:8080/health

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
