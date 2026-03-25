# DECISIONS.md

## Décisions validées

### Architecture
- backend en Java Spring Boot
- application mobile en Flutter
- base de données PostgreSQL
- architecture monolithe modulaire
- une seule application pour plusieurs résidences
- séparation logique des données par residence_id

### Authentification
- JWT pour sécuriser l'API
- mot de passe hashé avec BCrypt

### Inscription
- aucune inscription libre sans invitation
- invitation créée par un admin
- validation admin après inscription

### Utilisateur
- l'utilisateur choisit son pseudo
- l'utilisateur peut masquer son email
- l'utilisateur peut masquer son téléphone

### Fonctionnalités MVP
- authentification
- invitation
- validation admin
- profil
- cagnotte
- signalements
- événements
- votes
- notifications internes

### Philosophie de travail
- toujours avancer par petites tâches
- une tâche = un livrable concret et testable
- OpenClaw ne doit jamais dépendre de la mémoire de session
- tous les choix importants doivent être consignés ici

## Décisions encore ouvertes
- login par email uniquement ou email + pseudo
- Flyway ou Liquibase
- stockage photo local ou objet storage
- Firebase dès le MVP ou plus tard
