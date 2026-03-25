# TASK-00 Backend Init
Créer un projet Spring Boot avec :
- Java 17+
- Dépendances : Web, JPA, Lombok, PostgreSQL

Créer :
- package com.resiflow
- classe ResiflowApplication
- endpoint GET /health => OK

Test:
./mvnw spring-boot:run
curl http://localhost:8080/health
