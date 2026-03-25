# TASK-09

## ID
TASK-09

## Objectif
Ajouter le bean PasswordEncoder avec BCrypt et préparer l’infrastructure sécurité pour le hash des mots de passe.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Ajouter un bean PasswordEncoder de type BCryptPasswordEncoder
- Le placer dans une configuration claire
- Vérifier que le projet compile
- Ne pas encore créer la logique d’inscription complète

## Livrables
- Bean PasswordEncoder disponible

## Critères d'acceptation
- le projet compile
- le bean peut être injecté

## Tests
- cd backend
- ./mvnw test

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
