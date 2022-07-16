FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ADD src/main/resources/application.properties application.properties
ENTRYPOINT ["java" ,"-jar","app.jar"]