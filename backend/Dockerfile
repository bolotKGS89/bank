FROM openjdk:17-alpine

WORKDIR /app

COPY ./target/backend-0.0.1-SNAPSHOT.jar /app/java.jar

ENTRYPOINT ["java", "-jar","java.jar"]
EXPOSE 8080