#!/bin/sh

./mvnw package

docker build -t reece-address-book-system .
docker run --name reece-address-book-system -p 8080:8080 reece-address-book-system