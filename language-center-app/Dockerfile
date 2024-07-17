# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /usr/share/app
COPY ./pom.xml .
RUN mvn dependency:go-offline -B  # Download dependencies
COPY src ./src
RUN mvn clean install -e -X  # Enable debug output

# Stage 2: Run the application
FROM openjdk:17.0.1-jdk-slim
WORKDIR /usr/share/app
COPY --from=build /usr/share/app/target/language-center-app-0.0.1-SNAPSHOT.jar language-center-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "language-center-app.jar"]
