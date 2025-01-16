FROM openjdk:21-slim AS build

ARG FIREBASE_KEY
ARG FIREBASE_URL

ENV FIREBASE_KEY=${FIREBASE_KEY}
ENV FIREBASE_URL=${FIREBASE_URL}

RUN apt-get update && apt-get install -y maven

# Copy the necessary files, including the pom.xml
COPY authUser/pom.xml /app/authUser/pom.xml
COPY authUser/ /app/authUser/

# Set the working directory to the folder where pom.xml is located
WORKDIR /app/authUser

# Run Maven to build the project
RUN mvn clean package -DFIREBASE_KEY=${FIREBASE_KEY} -DFIREBASE_URL=${FIREBASE_URL}

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/authUser/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
