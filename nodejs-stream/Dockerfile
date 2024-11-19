# Utiliser une image officielle Node.js
FROM node:18.18.1-alpine

# Créer un répertoire pour l'application
WORKDIR /usr/src/app

# Copier le fichier package.json et package-lock.json (si disponible)
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier les fichiers source de l'application
COPY . .

# Exposer le port que votre application utilise (par exemple 3000)
EXPOSE 3000

# Commande pour démarrer votre application
CMD ["npm", "start"]
