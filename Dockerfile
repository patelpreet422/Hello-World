FROM openjdk:17
LABEL authors="Preet Patel <preet.patel@kotak.com>"
ARG JAR_FILE=build/libs/hello-world-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/app.jar"]
