# TASK-16

## ID
TASK-16

## Objectif
Créer le module cagnotte avec l’entité WalletTransaction, le calcul du solde et les endpoints de consultation / création.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions
- Lire docs/PROJECT_CONTEXT.md
- Lire docs/ARCHITECTURE.md
- Lire docs/DECISIONS.md
- Lire docs/CURRENT_TASK.md
- Travailler uniquement dans backend/
- Créer dans le package wallet :
  - enum TransactionType avec CREDIT et DEBIT
  - entité WalletTransaction
  - WalletTransactionRepository
  - WalletService
  - WalletController
  - DTO nécessaires
- Champs minimum :
  - id
  - residence
  - type
  - category
  - amount
  - description
  - operationDate
  - createdBy
  - visibleToResidents
  - createdAt
- Ajouter les endpoints :
  - GET /wallet/balance
  - GET /wallet/transactions
  - POST /admin/wallet/transactions
- Règles :
  - les données sont filtrées par résidence
  - le solde = somme crédits - somme débits
  - seul ADMIN peut créer une opération
  - résidents consultent seulement leur résidence

## Livrables
- entité, repository, service, controller, DTO

## Critères d'acceptation
- le solde est calculé
- la liste des transactions fonctionne
- la création admin fonctionne

## Tests
- cd backend
- ./mvnw test
- fournir exemples curl

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister les fichiers modifiés
