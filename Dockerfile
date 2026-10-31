# 1. Build-Phase: Nutzt Gradle, um die JAR-Datei zu bauen
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

# 2. Runtime-Phase: Läuft in einem schlanken Java-Container
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]