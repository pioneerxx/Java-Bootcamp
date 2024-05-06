package Java.edu.school21.printer.logic;

import java.awt.Color;

import java.awt.image.BufferedImage;

public class ImageConverter {
    private String black;
    private String white;
    private BufferedImage image;

    public ImageConverter(String black, String white, BufferedImage image) {
        this.black = black;
        this.white = white;
        this.image = image;
    }

    public void printImage() {
        Color color;
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = new Color(image.getRGB(j, i));
                if (color.equals(Color.WHITE)) {
                    System.out.print(white);
                } else if (color.equals(Color.BLACK)) {
                    System.out.print(black);
                }
            }
            System.out.println();
        }
    }
}