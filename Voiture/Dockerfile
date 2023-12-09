FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/Voiture-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} Voiture-0.0.1-SNAPSHOT.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "/Voiture-0.0.1-SNAPSHOT.jar"]