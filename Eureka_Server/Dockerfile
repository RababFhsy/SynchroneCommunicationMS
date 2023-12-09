FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/Eureka_Server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} Eureka_Server-0.0.1-SNAPSHOT.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/Eureka_Server-0.0.1-SNAPSHOT.jar"]