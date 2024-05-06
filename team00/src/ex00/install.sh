#!bin/bash

cd ChaseLogic
mvn clean
mvn package
cd target
mv ChaseLogic-1.0-SNAPSHOT.jar ../../ChaseGame/lib/ChaseLogic-1.0-SNAPSHOT.jar
cd ../../ChaseGame
mvn clean
mvn compile
mvn package
cd ..
mv ChaseGame/target/ChaseGame-1.0-SNAPSHOT-jar-with-dependencies.jar game.jar