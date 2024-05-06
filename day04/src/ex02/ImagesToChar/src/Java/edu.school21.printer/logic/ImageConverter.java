package Java.edu.school21.printer.logic;

import java.awt.Color;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import java.awt.image.BufferedImage;

public class ImageConverter {
    private CmdParser parser;
    private BufferedImage image;

    public ImageConverter(CmdParser parser, BufferedImage image) {
        this.parser = parser;
        this.image = image;
    }

    public void printImage() {
        Color color;
        int height = image.getHeight();
        int width = image.getWidth();
        ColoredPrinter printer = new ColoredPrinter();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = new Color(image.getRGB(j, i));
                if (color.equals(Color.WHITE)) {
                    printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(parser.getColorForWhite()));
                } else if (color.equals(Color.BLACK)) {
                    printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(parser.getColorForBlack()));
                }
            }
            System.out.println();
        }
    }
}