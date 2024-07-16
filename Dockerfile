FROM ubuntu:latest AS build

RUN apt-get update
# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /usr/share/app
COPY . .
RUN mvn clean package


FROM openjdk:17.0.1-jdk-slim
WORKDIR /usr/share/app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]