# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /usr/share/app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Run Maven to clean and package the project
RUN mvn clean package

# Stage 2: Run the application
FROM openjdk:17.0.1-jdk-slim
WORKDIR /usr/share/app

# Copy the packaged jar file from the build stage
COPY --from=build /usr/share/app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
