# Utiliser une image de base avec Maven 3.8 et OpenJDK 17
FROM maven:3.8.4-openjdk-17-slim AS build

# Copier le pom.xml et les sources
COPY pom.xml /usr/src/classic/
COPY src /usr/src/classic/src

# Construire l'application
WORKDIR /usr/src/classic
RUN mvn clean package

# Utiliser une image de base OpenJDK 17
FROM openjdk:17-jdk-slim

# Copier le fichier jar
COPY --from=build /usr/src/classic/target/classic-0.0.1-SNAPSHOT.jar /usr/app/classic.jar

# Exposer le port 8585
EXPOSE 8383

# Commande pour démarrer l'application
CMD ["java", "-jar", "/usr/app/classic.jar"]
