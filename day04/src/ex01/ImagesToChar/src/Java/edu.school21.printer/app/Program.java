package Java.edu.school21.printer.app;

import Java.edu.school21.printer.logic.ImageConverter;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class Program {
    public static void main(String args[]) {
        String black;
        String white;
        ImageConverter imageConverter;
        BufferedImage image;
        try {
            if (args.length != 2 || !args[0].startsWith("--black=") 
            || !args[1].startsWith("--white=")
            || args[0].split("=")[1].length() != 1
            || args[1].split("=")[1].length() != 1) {
                System.err.println("Wrong input");
                System.err.println("Usage: java Java.edu.school.printer.app.Program --black=*char* --white=*char*");
                System.exit(-1);
            }
            black = args[0].split("=")[1];
            white = args[1].split("=")[1];
            InputStream inputStream = Program.class.getClassLoader().getResourceAsStream("resources/image.bmp");
            image = ImageIO.read(inputStream);
            // image = ImageIO.read(new File("resources/image.bmp"));
            if (image.getHeight() == 16 && image.getWidth() == 16) {
                imageConverter = new ImageConverter(black, white, image);
                imageConverter.printImage();
            } else {
                System.err.println("The image must be 16x16");
                System.exit(-1);
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Wrong input");
            System.err.println("Usage: java Java.edu.school.printer.app.Program --black=*char* --white=*char*");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Image not found");
            System.exit(-1);
        }
    }
}
