FROM eclipse-temurin:21-jdk-alpine

WORKDIR /parcialfinal

COPY target/*.jar parcialfinal.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "parcialfinal.jar"]