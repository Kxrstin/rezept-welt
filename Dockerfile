# 1. Build-Phase: Nutzt Gradle mit Java 21
FROM gradle:8.11.0-jdk21 AS builder
WORKDIR /app
COPY . .

# Windows-Zeilenenden entfernen und Ausführungsrechte setzen
RUN sed -i -e 's/\r$//' gradlew && chmod +x gradlew

# Build ausführen
RUN ./gradlew bootJar --no-daemon

# 2. Runtime-Phase: Läuft im schlanken Java 21 Runtime-Container
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]