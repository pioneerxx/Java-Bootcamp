package Java.edu.school21.printer.app;

import Java.edu.school21.printer.logic.CmdParser;
import Java.edu.school21.printer.logic.ImageConverter;
import com.beust.jcommander.JCommander;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;

public class Program {
    public static void main(String args[]) {
        try {
            InputStream inputStream = Program.class.getClassLoader().getResourceAsStream("resources/image.bmp");
            BufferedImage image = ImageIO.read(inputStream);
            CmdParser parser = new CmdParser();
            JCommander jcommander = new JCommander(parser);
            jcommander.parse(args);
            ImageConverter imageConverter = new ImageConverter(parser, image);
            imageConverter.printImage();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
