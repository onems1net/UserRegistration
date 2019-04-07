#!/bin/sh

echo Preparing build and run for User Registration application in Docker

echo Building Docker image file
docker build -f Dockerfile -t spring-boot-user-registration .

echo Running User Registration application in Docker
docker run -p 8085:8085 spring-boot-user-registration
