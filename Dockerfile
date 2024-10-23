FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar

#  TODO Set up postgresql cloud connection
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=local", "app.jar"]