# 🍺 Beer Store API

A RESTful API for an online beer store, built with **Spring Boot**. Authentication and authorization are handled via **Keycloak**, and the project is containerized with **Docker** for easy deployment.

## 🚀 Features

- Beer product management: create, read, update, delete.
- User authentication and role-based access using Keycloak.
- JWT-based security.
- Inventory and stock management.
- Full CRUD operations.
- Docker-ready for local or production environments.
- Environment-specific configuration support.

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Security with OAuth2
- Keycloak (Identity and Access Management)
- PostgreSQL
- Docker & Docker Compose
- Flyway (database migrations)
- Swagger/OpenAPI (API documentation)

## ⚙️ Configuration

### Prerequisites

- Docker & Docker Compose
- JDK 17+ (if running locally)
- Maven 3.8+

### Environment Variables

Set these variables in a `.env` file or as system environment variables:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/beer_store
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=yourpassword
KEYCLOAK_AUTH_SERVER_URL=http://keycloak:8080/auth
KEYCLOAK_REALM=beer-store
KEYCLOAK_RESOURCE=beer-store-api
