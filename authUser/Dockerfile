FROM openjdk:21-slim AS build

ARG FIREBASE_KEY
ARG FIREBASE_URL

ENV FIREBASE_KEY=${FIREBASE_KEY}
ENV FIREBASE_URL=${FIREBASE_URL}

RUN apt-get update && apt-get install -y maven

COPY authUser/pom.xml /app/pom.xml
COPY authUser/ /app/

RUN mvn clean package -DFIREBASE_KEY=${FIREBASE_KEY} -DFIREBASE_URL=${FIREBASE_URL}

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
