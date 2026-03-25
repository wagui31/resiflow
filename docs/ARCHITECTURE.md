# ARCHITECTURE.md

## Architecture générale

```text
Flutter Mobile App
        |
        v
   REST API Spring Boot
        |
        v
     PostgreSQL
```

## Style d'architecture
- monolithe modulaire
- une seule application backend
- une seule base PostgreSQL
- séparation logique par modules
- isolation fonctionnelle par residence_id

## Modules backend
```text
com.residence.app
 ├── auth
 ├── security
 ├── residence
 ├── user
 ├── invitation
 ├── wallet
 ├── incident
 ├── event
 ├── vote
 ├── notification
 └── common
```

## Modèle de sécurité
### Authentification
- login via email ou pseudo selon choix final
- JWT pour l'accès API
- BCrypt pour le hash des mots de passe

### Autorisation
- ADMIN
- RESIDENT

### Règles
- seuls les admins peuvent inviter et valider
- un résident ne voit que les données de sa résidence
- toutes les requêtes métier doivent filtrer par residence_id

## Entités principales

### Residence
- id
- name
- address
- createdAt
- enabled

### User
- id
- residenceId
- pseudo
- firstName
- lastName
- email
- phone
- passwordHash
- role
- emailVisible
- phoneVisible
- enabled
- accountStatus
- createdAt

### Invitation
- id
- residenceId
- targetValue
- token
- status
- expiresAt
- createdBy
- createdAt

### WalletTransaction
- id
- residenceId
- type
- category
- amount
- description
- operationDate
- createdBy
- visibleToResidents
- createdAt

### IncidentReport
- id
- residenceId
- createdBy
- title
- description
- status
- priority
- photoUrl
- createdAt
- updatedAt

### Event
- id
- residenceId
- title
- description
- eventDate
- location
- createdBy
- createdAt

### Vote
- id
- residenceId
- title
- description
- startDate
- endDate
- status
- createdBy
- createdAt

### VoteOption
- id
- voteId
- label

### VoteResponse
- id
- voteId
- optionId
- userId
- votedAt

### Notification
- id
- residenceId
- userId
- title
- message
- type
- read
- createdAt

## Flux d'inscription
1. un admin crée une invitation
2. un futur résident reçoit un token
3. il remplit son inscription
4. son compte passe en attente de validation si la double validation est activée
5. l'admin valide
6. le résident peut se connecter

## Accueil mobile
### Carte Cagnotte
- solde actuel
- total des entrées
- total des sorties
- dernières opérations

### Carte Signalements
- nombre ouverts
- nombre en cours
- accès à la liste

### Carte Événements
- prochain événement
- accès à la liste

### Carte Votes
- nombre de votes ouverts
- accès à la liste

## Règles de développement
- une tâche à la fois
- ne pas mélanger backend et mobile dans une même tâche sauf si demandé
- toujours produire des DTO dédiés
- toujours protéger les endpoints sensibles
- toujours prévoir validation et gestion d'erreurs
- ajouter tests minimum sur la logique métier

## Convention de travail avec OpenClaw
Pour chaque tâche:
1. lire PROJECT_CONTEXT.md
2. lire ARCHITECTURE.md
3. lire DECISIONS.md
4. lire CURRENT_TASK.md
5. ne réaliser que la tâche demandée
6. mettre à jour DONE.md
7. mettre à jour NEXT_STEP.md
