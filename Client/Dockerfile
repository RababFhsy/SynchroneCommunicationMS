FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/Client-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} Client-0.0.1-SNAPSHOT.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/Client-0.0.1-SNAPSHOT.jar"]