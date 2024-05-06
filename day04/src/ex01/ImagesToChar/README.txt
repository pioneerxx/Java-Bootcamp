Move to ex01/ImagesToChar directory.
In order to convert image, delete "target" folder if it exists and create again. Then compile the program using
javac -d target src/Java/edu.school21.printer/*/*.java
Copy resources to target folder using
cp -R src/resources target
Then archive resources and compiled files into JAR archive using
jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target .
Change privileges to the .jar file
chmod u+x ./target/images-to-chars-printer.jar
After that, you can execute the program by using
java -jar target/images-to-chars-printer.jar --black=*char* --white=*char*