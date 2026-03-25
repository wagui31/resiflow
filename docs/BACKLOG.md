# BACKLOG.md

## Règle
Une seule tâche active à la fois.
CURRENT_TASK.md contient toujours la tâche en cours.

---

## PHASE 0 - Initialisation

### TASK-0.1
Créer le projet Spring Boot avec dépendances:
- Spring Web
- Spring Security
- Spring Data JPA
- Validation
- PostgreSQL Driver
- Lombok

Livrables:
- projet généré
- structure de base
- endpoint /health qui retourne OK

### TASK-0.2
Créer le projet Flutter
Livrables:
- application Flutter démarre
- structure initiale propre
- écran splash simple
- écran login vide

---

## PHASE 1 - Base technique backend

### TASK-1.1
Configurer PostgreSQL dans application.yml

### TASK-1.2
Créer la structure des packages métier

### TASK-1.3
Ajouter la gestion globale des exceptions

### TASK-1.4
Ajouter Swagger/OpenAPI si souhaité

---

## PHASE 2 - Modèle de données

### TASK-2.1
Créer l'entité Residence

### TASK-2.2
Créer l'entité User

### TASK-2.3
Créer l'entité Invitation

### TASK-2.4
Créer l'entité WalletTransaction

### TASK-2.5
Créer l'entité IncidentReport

### TASK-2.6
Créer l'entité Event

### TASK-2.7
Créer les entités Vote, VoteOption, VoteResponse

### TASK-2.8
Créer l'entité Notification

---

## PHASE 3 - Sécurité

### TASK-3.1
Configurer Spring Security

### TASK-3.2
Créer UserDetailsService

### TASK-3.3
Implémenter JWT

### TASK-3.4
Créer endpoint login

---

## PHASE 4 - Invitations et inscription

### TASK-4.1
Créer endpoint admin pour inviter un résident

### TASK-4.2
Créer génération et persistance du token d'invitation

### TASK-4.3
Créer endpoint lecture invitation par token

### TASK-4.4
Créer endpoint d'inscription via invitation

### TASK-4.5
Ajouter statut compte en attente de validation

### TASK-4.6
Créer endpoints admin approuver / refuser utilisateur

---

## PHASE 5 - Profil

### TASK-5.1
Créer endpoint GET /users/me

### TASK-5.2
Créer endpoint PUT /users/me

### TASK-5.3
Créer endpoint privacy pour email et téléphone

---

## PHASE 6 - Cagnotte

### TASK-6.1
Créer service de calcul du solde

### TASK-6.2
Créer endpoint GET /wallet/balance

### TASK-6.3
Créer endpoint GET /wallet/transactions

### TASK-6.4
Créer endpoint admin POST /wallet/transactions

### TASK-6.5
Ajouter tests de calcul du solde

---

## PHASE 7 - Signalements

### TASK-7.1
Créer endpoint création signalement

### TASK-7.2
Créer endpoint liste signalements

### TASK-7.3
Créer endpoint détail signalement

### TASK-7.4
Créer endpoint admin changement de statut

---

## PHASE 8 - Événements

### TASK-8.1
Créer endpoint admin création événement

### TASK-8.2
Créer endpoint liste événements

### TASK-8.3
Créer endpoint détail événement

---

## PHASE 9 - Votes

### TASK-9.1
Créer endpoint admin création vote

### TASK-9.2
Créer endpoint liste votes

### TASK-9.3
Créer endpoint détail vote

### TASK-9.4
Créer endpoint réponse à un vote

### TASK-9.5
Empêcher double vote

### TASK-9.6
Calculer résultats du vote

---

## PHASE 10 - Notifications

### TASK-10.1
Créer entité/service notifications internes

### TASK-10.2
Créer endpoint liste notifications

### TASK-10.3
Créer notifications automatiques sur événements métier

### TASK-10.4
Créer endpoint marquer comme lu

---

## PHASE 11 - Flutter UI

### TASK-11.1
Créer thème de base Flutter

### TASK-11.2
Créer écran login

### TASK-11.3
Créer écran inscription via invitation

### TASK-11.4
Créer navigation principale

### TASK-11.5
Créer page accueil avec cartes

### TASK-11.6
Créer écran profil

### TASK-11.7
Créer écran cagnotte et transactions

### TASK-11.8
Créer écran signalements

### TASK-11.9
Créer écran événements

### TASK-11.10
Créer écran votes

### TASK-11.11
Créer écran notifications

### TASK-11.12
Créer écrans admin
