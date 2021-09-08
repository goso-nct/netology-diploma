#!/bin/bash

set -x

DB=mysql

if [ -n "$1" ]
then
  DB=$1
fi

echo DB:$DB

docker-compose -f $DB.yml up -d
sleep 10

docker-compose -f gate.yml up -d
sleep 10

cd artifacts
cp application.properties.$DB application.properties
java -jar aqa-shop.jar &>/dev/null &
APP_PID=$!
sleep 20

cd ..
./gradlew test -Dselenide.headless=true

kill $APP_PID
docker-compose -f $DB.yml down
docker-compose -f gate.yml down

google-chrome file://$PWD/build/reports/tests/test/index.html

