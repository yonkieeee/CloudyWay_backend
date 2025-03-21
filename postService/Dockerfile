FROM openjdk:21-slim as build

RUN apt-get update && apt-get install -y maven

COPY ./postService/pom.xml ./postService/

COPY ./postService/src ./postService/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy

COPY --from=build /target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
