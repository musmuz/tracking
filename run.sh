#!/usr/bin/env bash
if [[ "$1" == "" ]]; then
    echo "Usage: ./run.sh (local|dev|prod)"
else
    if [[ $1 == "local" ]]; then
      echo "Begin deploying"
      mvn clean package docker:build -DskipTests
      docker-compose --file docker/docker-compose.yml up -d
      docker logs --follow $(docker ps -a -q -f name=tracking-api)
      echo "End Building and deploying"
    elif [[ $1 == "dev" ]]; then
      echo "Begin deploying"
      mvn clean package docker:build -DskipTests -Dspring.profiles.active=dev -DpushImage
      docker-compose --file docker/docker-compose.yml  up -d
      docker logs --follow $(docker ps -a -q -f name=tracking-api)
      echo "End Building and deploying"
    elif [[ $1 == "prod" ]]; then
      echo "Begin deploying"
      mvn clean package docker:build -DskipTests -Dspring.profiles.active=prod -DpushImage
      echo "Copying Docker Compose file"
      docker-machine scp docker/docker-compose-prod.yml do-moitrack-swarm-1:~/docker-compose-api.yml
      echo "Deploying service on the remote docker machine"
      docker-machine ssh do-moitrack-swarm-1 "docker stack deploy -c docker-compose-api.yml prod"
      echo "End Building and deploying"
    fi
fi