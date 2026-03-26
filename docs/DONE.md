# DONE.md

## Historique des tâches terminées

### TASK-00
- Initialisation du backend Spring Boot dans `backend/` avec Maven Wrapper.
- Configuration du package `com.resiflow` et création de la classe `ResiflowApplication`.
- Ajout du endpoint `GET /health` qui retourne `OK`.
- Ajout d'un test d'intégration simple sur `/health`.
- Désactivation temporaire de l'auto-configuration datasource/JPA pour permettre le démarrage sans configuration PostgreSQL à ce stade.

### TASK-0.1
- Initialisation du backend Spring Boot (Java 21, Maven) avec les dépendances Web, Security, Data JPA, Validation, PostgreSQL et Lombok.
- Ajout d'un endpoint `/health` qui retourne `{"status":"OK"}` et d'un test d'intégration `HealthControllerTest`.
- Désactivation temporaire de l'auto-configuration JDBC/JPA en attendant la configuration PostgreSQL (tâches futures).

### TASK-0.2
- Création du projet Flutter dans `mobile/` (structure Android, iOS, web, desktop) et configuration du thème de base.
- Implémentation d'un écran de connexion simple avec le titre "ResiFlow", le texte "Connexion", deux champs (email, mot de passe) et un bouton "Se connecter".
- Mise à jour du test `widget_test.dart` pour vérifier la présence des éléments de l'écran et exécution des commandes `flutter pub get`, `flutter analyze` et `flutter run -d web-server`.

### TASK-DB-DOCKER
- Ajout du fichier `docker-compose.yml` (PostgreSQL 15, volume nommé `resiflow_pgdata`) et configuration du backend (`application.yml`) pour pointer vers `jdbc:postgresql://localhost:5432/resiflow_db` avec l'utilisateur `resiflow_user`.
- Tentative d'installation de Docker via le script rootless officielle (`get.docker.com/rootless`) bloquée faute de droits sudo (`uidmap`/`iptables` requis).
- Les commandes `docker compose up -d` et `docker ps` ne peuvent pas être exécutées tant que Docker n'est pas installé sur l'hôte ; l'application Spring Boot démarre mais la connexion PostgreSQL reste à valider dès qu'un serveur est disponible.

### TASK-03
- Création des packages `auth`, `security`, `user`, `residence`, `invitation`, `wallet`, `incident`, `event`, `vote`, `notification` et `common` sous `src/main/java/com/resiflow/` chacun accompagné d'un `package-info.java` descriptif.
- Réplication à l'identique de l'arborescence de tests sous `src/test/java/com/resiflow/` pour préparer les fixtures unitaires.
- Exécution de `./mvnw test` (Java 21 Temurin local via `JAVA_HOME`) pour vérifier que le projet compile toujours.

### TASK-08
- Ajout du endpoint `POST /auth/login`.
- Ajout des DTO `LoginRequest` et `LoginResponse`.
- Ajout de la logique de login via email et mot de passe à partir des données utilisateur existantes.
- Gestion des erreurs de validation en `400 Bad Request` et des identifiants invalides en `401 Unauthorized`.
- Ajout des tests unitaires contrôleur et service pour le login.
- Adaptation des tests existants pour qu'ils soient exécutables dans l'environnement local courant, puis exécution réussie de `./mvnw test`.

### TASK-09
- Ajout de la configuration JWT dans `application.yml` avec secret et durée d'expiration paramétrables par variables d'environnement.
- Ajout du service `JwtService` pour générer, valider et lire les claims principaux des tokens.
- Génération d'un JWT lors du login et retour du token dans la réponse `POST /auth/login`.
- Ajout et adaptation des tests unitaires pour couvrir la génération JWT et la réponse de login.

### TASK-10
- Ajout de la configuration Spring Security stateless avec `SecurityFilterChain`, désactivation de la session, `formLogin`, `httpBasic` et `csrf`.
- Autorisation publique de `GET /health`, `POST /auth/login` et `POST /users`, avec authentification requise pour les autres routes.
- Ajout d'un filtre JWT qui lit le bearer token, valide le token et place l'utilisateur authentifié dans le contexte de sécurité.
- Ajout d'un `PasswordEncoder` BCrypt et intégration du hashage lors de la création utilisateur et de la vérification lors du login.
- Adaptation et ajout des tests pour couvrir le hashage des mots de passe et les règles principales de sécurité.

### TASK-12
- Ajout de l'entité `Invitation` avec les champs `residenceId`, `targetValue`, `token`, `status`, `expiresAt`, `createdBy` et `createdAt`.
- Ajout du repository `InvitationRepository`, des DTO `CreateInvitationRequest` et `InvitationResponse`, et du service `InvitationService`.
- Ajout du endpoint sécurisé `POST /invitations` qui crée une invitation pour la résidence de l'utilisateur authentifié.
- Ajout des validations minimales sur la cible invitée, la date d'expiration et la présence du contexte utilisateur authentifié.
- Ajout des tests unitaires contrôleur, service et sécurité pour couvrir la création d'invitation et la protection JWT du endpoint.
- Exécution réussie de `./mvnw test`.

### TASK-13
- Ajout du rôle utilisateur `ADMIN` ou `RESIDENT` via l'enum `UserRole` et stockage du rôle sur l'entité `User`.
- Attribution par défaut du rôle `RESIDENT` lors de la création d'un utilisateur.
- Ajout du rôle dans le JWT et dans le contexte de sécurité via `AuthenticatedUser`, avec autorités Spring `ROLE_*`.
- Restriction de `POST /invitations` aux seuls utilisateurs ayant le rôle `ADMIN`.
- Adaptation des tests unitaires et de sécurité pour couvrir la persistance du rôle, son transport dans le JWT et le contrôle d'accès admin.

### TASK-15
- Ajout de la dépendance `flyway-core` au backend Spring Boot.
- Activation explicite de Flyway et passage de Hibernate en mode `validate` pour s'appuyer sur les migrations SQL.
- Ajout de la migration initiale `V1__init_schema.sql` pour créer les tables `residences`, `users` et `invitations` selon les entités existantes.
- Vérification par exécution des tests backend.
