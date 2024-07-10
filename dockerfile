FROM maven:3.8.5-openjdk-17 As build
# Set the working directory
WORKDIR /app
COPY . . 
run  mvn clean package
# Use an official OpenJDK runtime as a parent image
FROM openjdk:17.0.1-jdk-slim


# Copy the JAR file to the container
COPY --FROM = build target/language-center-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
