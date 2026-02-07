# Rezept Welt
## Full-stack Webanwendung zur Verwaltung eigener Rezeptideen.

Der Fokus dieses Projektes liegt auf der testgetriebenen Entwicklung einer Full-Stack Webanwendung mit Datenbankanbindung.

--- 

## Setup und Ausführung
Follow these steps to start the project in your local environment (e.g., WSL or Linux). 

### 1. Requirements 
* **Java 21** (JDK)
* **Docker & Docker Desktop**
    
### 2. Start Docker 
Start the PostgreSQL database via Docker Compose: 
* docker compose up -d

### 3. Start application 
Start the application with the Gradle wrapper: 
* ./gradlew bootRun

Once the startup process is complete, you can access the application at: 
* http://localhost:8080

--- 

## Features 
- Server-side rendered frontend with Thymeleaf
- SQL-based persistence with explicit JDBC access
- Database versioning and migrations with Flyway

---

## Tech Stack 
### Backend
- Java 21
- Spring Boot
- JDBC
- Flyway
- Gradle

### Frontend 
- Thymeleaf
- HTML
- CSS

### Database 
- Relational SQL database (PostgreSQL)

--- 

### What I learned 
Test-driven development and structuring of Java applications and Design of web-based services and their integration into server-side user interfaces Management of relational databases and migrations. 

--- 

### Images

