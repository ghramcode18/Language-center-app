FROM openjdk:17.0.1-jdk-slim
WORKDIR /usr/share/app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]