# Space Wars 
## by danyellt, argoniaz & ehtelfos


### <span style="color:red">HAVE POSTGRESQL SERVER INSTALLED AND RUNNING!!!</span>

### Installation for MacOS:

1. Install java SDK 1.8 or higher
2. Go to the ex00/ directory and run ```source install.sh``` from the terminal.


### Installation for other OS:

1. Install java SDK 1.8 or higher
2. Install maven from ```https://maven.apache.org/download.cgi``` (Read the README.txt in the archive to install properly)
3. Run ```mvn -f TanksClient/pom.xml package```
4. Extract the SpaceWarsClient.jar file from the created ```target``` directory
5. Run ```mvn -f TanksServer/pom.xml package```
6. Extract the SpaceWarsServer.jar file from the created ```target``` directory


### How to play:

1. Run ```java -jar SpaceWarsServer.jar --port={specify port here}```
2. Run SpaceWarsClient.jar either by ```java -jar SpaceWarsClient.jar``` or by clicking LMB twice (may not work due to java 8).
3. In the opened window type the server port you specified earlier.
4. Connect the second client the same way.
5. Play!


### How to uninstall:

For MacOS or other unix-systems, run ```sh uninstall.sh```.
For Windows, delete Game, TanksClient/target, TanksServer/target folders and TanksClient/dependency-reduced-pom.xml file.