FROM openjdk:17-jdk-slim

COPY target/financial-management-service-0.0.1.jar /usr/local/app/app.jar

WORKDIR /usr/local/app

CMD ["java", "-jar", "app.jar"]
