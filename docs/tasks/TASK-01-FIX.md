# TASK-02-FIX

Objectif:
Nettoyer la configuration Spring Boot

Instructions:
- Supprimer backend/src/main/resources/application.properties
- Garder uniquement application.yml
- Créer application.yml propre avec:

server:
  port: 8080

spring:
  application:
    name: resiflow
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update

- Vérifier que l'application démarre

Livrables:
- application.yml propre
- application.properties supprimé

Test:
- mvn spring-boot:run OK
