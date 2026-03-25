# TASK-DB-DOCKER

## ID
TASK-DB-DOCKER

## Objectif
Mettre en place une base de données PostgreSQL via Docker et connecter le backend Spring Boot à cette base.

## Contexte
Tous les chemins sont relatifs à la racine du projet.

## Instructions

### Étape 1 - Installer Docker
- Vérifier si Docker est installé avec :
  docker --version
- Si Docker n’est pas installé :
  - Installer Docker (version stable)
- Vérifier que Docker fonctionne correctement

### Étape 2 - Créer docker-compose.yml

Créer un fichier à la racine du projet :

docker-compose.yml

Contenu EXACT à mettre :

version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: resiflow_postgres
    restart: always
    environment:
      POSTGRES_DB: resiflow_db
      POSTGRES_USER: resiflow_user
      POSTGRES_PASSWORD: resiflow_pass
    ports:
      - "5432:5432"
    volumes:
      - resiflow_pgdata:/var/lib/postgresql/data

volumes:
  resiflow_pgdata:

### Étape 3 - Lancer PostgreSQL

Exécuter la commande :

docker compose up -d

### Étape 4 - Vérifier le conteneur

Exécuter :

docker ps

- Vérifier que le conteneur "resiflow_postgres" est en statut "Up"

### Étape 5 - Configurer Spring Boot

Modifier le fichier :

backend/src/main/resources/application.yml

Ajouter ou compléter :

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/resiflow_db
    username: resiflow_user
    password: resiflow_pass
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

### Étape 6 - Tester la connexion backend

Exécuter :

cd backend
./mvnw spring-boot:run

- Vérifier qu’il n’y a aucune erreur de connexion à la base
- Vérifier que Hibernate initialise les tables

## Livrables
- docker-compose.yml créé à la racine
- conteneur PostgreSQL fonctionnel
- backend connecté à PostgreSQL
- configuration application.yml mise à jour

## Critères d'acceptation
- docker ps affiche le conteneur postgres actif
- l'application Spring Boot démarre sans erreur
- les tables sont créées automatiquement par Hibernate

## Tests
- docker ps
- docker logs resiflow_postgres
- cd backend
- ./mvnw spring-boot:run

## Fin de tâche
- Mettre à jour docs/DONE.md
- Mettre à jour docs/NEXT_STEP.md
- Lister tous les fichiers modifiés
