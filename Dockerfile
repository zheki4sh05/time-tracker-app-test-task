
FROM openjdk:17
ARG JAR_FILE=target/time-tracker-app-test-task-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

