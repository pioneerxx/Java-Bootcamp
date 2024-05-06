package edu.school21.tanks.app;

import edu.school21.tanks.config.ApplicationConfig;
import edu.school21.tanks.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        JCommander jCommander = new JCommander(arguments);
        jCommander.parse(args);
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Server server = context.getBean("server", Server.class);
        try {
            server.start(arguments.getPort());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParameterException e) {
            System.err.println("Port must be specified");
        }
    }
}
