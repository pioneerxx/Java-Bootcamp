Move to ex02/ImagesToChar directory.
In order to convert image, delete "target" folder if it exists and create again using
rm -rf target mkdir target
Download JCommander and JCDP libraries using
1 curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar
2 curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar
Then compile the program using
javac -d target -sourcepath src/Java -cp lib/\* src/Java/edu.school21.printer/app/Program.java src/Java/edu.school21.printer/logic/CmdParser.java src/Java/edu.school21.printer/logic/ImageConverter.java
Unzip the jar libraries into target folder using
cd target
jar xf ../lib/jcommander-1.72.jar
jar xf ../lib/JCDP-4.0.0.jar
Copy resources and libraries to target folder using
cp -R src/resources target
Then archive resources and compiled files into JAR archive using
jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target .
Change privileges to the .jar file
chmod u+x ./target/images-to-chars-printer.jar
After that, you can execute the program by using
java -jar target/images-to-chars-printer.jar --black=*char* --white=*char*