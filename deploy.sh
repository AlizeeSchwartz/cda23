#!/bin/bash

# Mettre à jour le code source
git pull

# Construire le projet avec Maven
bash mvnw package -P prod,sysadmin  --settings /home/debian/.m2/settings.xml

# Construire l'image Docker
docker build --no-cache -t image-spring-demo-alizee .

# Arreter le conteneur existant
docker stop conteneur-spring-demo-alizee

# Supprimer le conteneur existant
docker rm conteneur-spring-demo-alizee

# Lancer un nouveau conteneur
docker run -d --net backend --ip 172.18.0.4 --name=conteneur-spring-demo-alizee -p 8080:8080 -v uploaded_files:/uploads image-spring-demo-alizee