package Game.Main;

import Game.Backend.Arguments;
import com.beust.jcommander.JCommander;
import java.io.*;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try {
            Arguments arguments = new Arguments();
            JCommander jCommander = new JCommander(arguments);
            jCommander.parse(args);
            if (!arguments.getProfile().equals("develop") && !arguments.getProfile().equals("production")) {
                throw new IllegalArgumentException("Profile not found: " + arguments.getProfile());
            }
            InputStream inputStream = Program.class.getResourceAsStream("/application-" + arguments.getProfile() + ".properties");
            Scanner scanner = new Scanner(inputStream);
            String[] propertiesString = new String[10];
            String line;
            for (int i = 0; i < 10; i++) {
                line = scanner.nextLine();
                String[] parts = line.split(" = ");
                if (parts.length > 1) {
                    propertiesString[i] = parts[1];
                } else {
                    propertiesString[i] = "";
                }
            }
            scanner.close();
            GameInterface gameInterface = new GameInterface(arguments, propertiesString);
            gameInterface.gameBootUp();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}