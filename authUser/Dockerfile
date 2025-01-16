FROM eclipse-temurin:21-jdk-alpine

ARG JAR_FILE=./target/*.jar

COPY ./target/cloudywaybackend-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]