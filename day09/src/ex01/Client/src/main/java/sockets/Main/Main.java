package sockets.Main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import sockets.Client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        JCommander jCommander = new JCommander(arguments);
        jCommander.parse(args);
        Client client = new Client();
        try {
            client.start(arguments.getPort());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParameterException e) {
            System.err.println("Port must be specified");
        }
    }
}
