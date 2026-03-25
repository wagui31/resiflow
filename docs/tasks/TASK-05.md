# TASK-05

## ID
TASK-05

## Objectif
Créer l’entité Residence et son repository.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer l’entité JPA Residence dans le package residence
- Champs attendus :
  - id : Long
  - name : String
  - address : String
  - createdAt : LocalDateTime
  - enabled : boolean
- Utiliser Lombok raisonnablement
- Ajouter @PrePersist pour createdAt si utile
- Créer ResidenceRepository
- Ne pas encore créer de controller pour Residence
- Vérifier que le projet compile

## Livrables
- Residence.java
- ResidenceRepository.java

## Critères d'acceptation
- l’entité compile
- le repository compile
- la table peut être générée par JPA

## Tests
- cd backend
- ./mvnw test

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
