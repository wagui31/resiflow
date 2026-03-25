# PROJECT_CONTEXT.md

## Nom du projet
Application mobile de gestion de résidence.

## Vision produit
Créer une application simple et transparente pour une résidence.
Chaque résident peut suivre la vie de la résidence:
- la cagnotte commune
- les dépenses
- les signalements
- les événements
- les votes
- les notifications

L'application est obligatoirement liée à une résidence.
Un utilisateur ne peut rejoindre une résidence que s'il est invité par un administrateur.

## Objectifs MVP
Le MVP doit permettre:
1. connexion sécurisée
2. inscription uniquement par invitation
3. validation par administrateur
4. gestion du profil utilisateur
5. affichage d'un tableau de bord avec cartes
6. gestion de la cagnotte
7. gestion des signalements
8. gestion des événements
9. gestion des votes
10. notifications internes

## Stack technique retenue
### Backend
- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Flyway ou Liquibase pour les migrations

### Mobile
- Flutter

### Notifications
- Notifications internes en base dans un premier temps
- Firebase Cloud Messaging plus tard

## Principes fonctionnels
### Résidence
- une résidence possède des utilisateurs
- une résidence possède des transactions, signalements, événements, votes, notifications
- un utilisateur appartient à une seule résidence

### Utilisateur
- un utilisateur a un rôle: ADMIN ou RESIDENT
- un utilisateur choisit son pseudo
- un utilisateur peut masquer son email et son téléphone dans son profil public
- un utilisateur ne peut s'inscrire que s'il a reçu une invitation valide

### Admin
Un administrateur peut:
- inviter un résident
- valider ou refuser un compte
- ajouter des opérations dans la cagnotte
- gérer les signalements
- créer des événements
- créer des votes
- diffuser des notifications

### Résident
Un résident peut:
- consulter la cagnotte
- consulter les transactions autorisées
- créer un signalement
- voir les signalements
- voir les événements
- voter
- consulter ses notifications
- modifier son profil

## Page d'accueil mobile
La page d'accueil contient plusieurs cartes:
- Cagnotte de la résidence
- Signalements
- Événements
- Votes
- Notifications (optionnel au début)

## Contraintes importantes
- pas d'inscription libre sans invitation
- toutes les données doivent être liées à une résidence
- les données d'une résidence ne doivent jamais être visibles par une autre résidence
- ne pas partir sur des microservices pour le MVP
- utiliser un monolithe modulaire simple
- découper le travail en petites tâches autonomes et vérifiables

## Approche OpenClaw
OpenClaw ne doit jamais dépendre de sa mémoire de session.
Tout le contexte projet doit être lu depuis les fichiers du dossier docs.
Chaque tâche doit être petite, claire, testable et commitable.
