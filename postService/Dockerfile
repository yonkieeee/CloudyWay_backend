FROM openjdk:21-slim as build

RUN apt-get update && apt-get install -y maven

COPY . .

FROM eclipse-temurin:21-jre-jammy

COPY --from=build /target/*.jar app.jar

ENTRYPOINT["java", "-jar", "app.jar"]