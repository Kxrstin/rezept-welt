FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .

# 1. Windows-Zeilenenden entfernen und Ausführungsrechte setzen
RUN sed -i -e 's/\r$//' gradlew && chmod +x gradlew

# 2. Build ausführen
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]