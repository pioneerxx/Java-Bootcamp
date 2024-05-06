Move to ex00/ImagesToChar directory.
In order to convert image, delete "target" folder if it exists and create again. Then compile the program using
javac -d target src/Java/edu.school21.printer/*/*.java
Then launch the program using
java -classpath target Java.edu.school21.printer.app.Program --black=*char* --white=*char* --imagePath=*path.bmp*