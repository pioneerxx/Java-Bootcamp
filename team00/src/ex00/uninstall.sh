#!/bin/bash

cd ChaseGame
mvn clean:clean
cd ../ChaseLogic
mvn clean:clean
cd ..
rm ChaseGame/lib/ChaseLogic-1.0-SNAPSHOT.jar
rm game.jar