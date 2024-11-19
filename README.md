# Streams 

## A quoi sert ce dépôt ?

Ce dépôt a pour but de présenter différents exemples d'implémentation des reactive streams.

Vous y trouverez des exemples d'implémentation des reactive streams avec les librairies suivantes :
- [Project Reactor](https://projectreactor.io/)
- [Akka Streams](https://doc.akka.io/docs/akka/current/stream/index.html)
- [Node.js Streams](https://nodejs.org/api/stream.html)

## Installation

Les versions utilisées sont : 

- Java 21
- Scala 2.13.11
- Node 21.6.2
- Docker 26.0.0

## Docker compose

Le fichier `docker-compose.yml` permet de lancer une base de données MongoDB ainsi qu'un conteneur par version 
de l'application.

Pour lancer les conteneurs, il suffit de lancer la commande suivante :

```bash
docker-compose up
```
ou 

```bash
docker compose up
```

suivant votre version de docker.

### Front 

Le module front implémente une webapp en angular qui permet de contacter un déploiement 
des applications de data stream. 

Pour exploiter cette interface il est nécéssaire de renseigner les urls des applications de data stream dans 
le fichier `src/app/home/home.component.ts` du module front.

### Gatling

Le dossier Gatling contient des scénarios de test. Ces scénarios sont configurés pour contacter les applications 
déployées via le docker compose.
Similairement au front si vous voulez tester vos propres conteneurs, vous devrez modifier les adresses dans ces scénarios.


### MongoDB
Le conteneur mongoDb est initialisé avec un script `initInventory.js`.
