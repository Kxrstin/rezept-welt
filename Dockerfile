FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .

# Führe das aus, um die Ausführungsrechte für gradlew zu setzen:
RUN chmod +x gradlew

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]