# Utiliser une image de base avec Maven 3.8 et OpenJDK 17
FROM maven:3.8.4-openjdk-17-slim AS build

# Copier le pom.xml et les sources
COPY pom.xml /usr/src/spring-stream-demo/
COPY src /usr/src/spring-stream-demo/src

# Construire l'application
WORKDIR /usr/src/spring-stream-demo
RUN mvn clean package

# Utiliser une image de base OpenJDK 17
FROM openjdk:17-jdk-slim

# Copier le fichier jar
COPY --from=build /usr/src/spring-stream-demo/target/spring-stream-demo-0.0.1-SNAPSHOT.jar /usr/app/spring-stream-demo.jar

# Exposer le port 8585
EXPOSE 8585

# Commande pour démarrer l'application
CMD ["java", "-jar", "/usr/app/spring-stream-demo.jar"]
