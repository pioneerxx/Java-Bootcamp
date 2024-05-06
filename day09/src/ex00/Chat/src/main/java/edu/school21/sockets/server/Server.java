package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Component("server")
@DependsOn("usersService")
public class Server {
    private final UsersService service;
    private ServerSocket serverSocket;
    private final List<ThreadConnection> connectionList = new LinkedList<>();
    @Autowired
    public Server(@Qualifier("usersService")UsersServiceImpl service) {
        this.service = service;
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Thread thread = new Thread(this::readServerInput);
        thread.start();
        while(!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                ThreadConnection connection = new ThreadConnection(socket, service);
                connectionList.add(connection);
                connection.start();
            } catch (IOException e) {
                System.out.println("Server shutdown");
                break;
            }
        }
    }

    private void readServerInput() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            if (input != null && input.equalsIgnoreCase("exit")) {
                shutdown();
                break;
            }
        }
        scanner.close();
    }

    private synchronized void shutdown() {
        for (ThreadConnection threadConnection : connectionList) {
            threadConnection.disconnect();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }
}
