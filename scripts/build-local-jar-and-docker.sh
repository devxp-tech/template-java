#!/bin/bash

rm -rf build
./gradlew build -x test

if [ ! -f ./build/libs/application.jar ]; then
    echo "JAR not found"
      exit 1
fi

docker rmi -f ms-template
docker build -t ms-template .
