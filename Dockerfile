FROM adoptopenjdk/openjdk11:alpine-jre
RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot
WORKDIR /app
COPY target/dependency ./lib
COPY target/time-tracker-app-test-task-0.0.1-SNAPSHOT.jar ./application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "./lib/*:./application.jar","com.timetrackerapptesttask.TimeTrackerAppTestTaskApplication"]