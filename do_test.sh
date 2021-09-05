#!/bin/bash

set -x

docker-compose -f mysql.yml up -d
sleep 10

docker-compose -f gate.yml up -d
sleep 10

cd artifacts
java -jar aqa-shop.jar &>/dev/null &
APP_PID=$!
sleep 20

cd ..
./gradlew test -Dselenide.headless=true

kill $APP_PID
docker-compose -f mysql.yml down
docker-compose -f gate.yml down

google-chrome file://$PWD/build/reports/tests/test/index.html

