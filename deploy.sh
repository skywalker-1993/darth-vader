#!/bin/sh
compose_file="docker-compose.yml"
docker-compose -f "$compose_file" down -v #--rmi all
docker-compose -f "$compose_file" build --no-cache
docker-compose -f "$compose_file" up -d