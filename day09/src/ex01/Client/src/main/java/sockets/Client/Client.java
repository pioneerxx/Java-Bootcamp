package sockets.Client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private Socket socket;
    private BufferedReader clientReader;
    private BufferedReader serverReader;
    private BufferedWriter serverWriter;
    private String currentUsername;
    private final String ANSI_CLEAR_LINE = "\033[H\033[2J";

    public void start(int port) throws IOException {
        socket = new Socket("localhost", port);
        clientReader = new BufferedReader(new InputStreamReader(System.in));
        serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        getMessageFromServer();
        while (true) {
            startClientWork();
        }
    }

    private void startClientWork() {
        try {
            System.out.print("> ");
            String message = clientReader.readLine();
            while (!Arrays.asList(ChatCommands.commands).contains(message)) {
                System.out.print("> ");
                message = clientReader.readLine();
            }
            sendMessageToServer(message);
            executeCommand(message);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void sendMessageToServer(String message) throws IOException {
        serverWriter.write(message);
        serverWriter.newLine();
        serverWriter.flush();
    }
    private void getMessageFromServer() throws IOException {
        String serverMessage = serverReader.readLine();
        System.out.println(serverMessage);
    }

    private void signUp() throws IOException {
        getMessageFromServer();
        System.out.print("> ");
        sendMessageToServer(clientReader.readLine());
        getMessageFromServer();
        System.out.print("> ");
        sendMessageToServer(clientReader.readLine());
        getMessageFromServer();
    }

    private void signIn() throws IOException {
        getMessageFromServer();
        System.out.print("> ");
        String username = clientReader.readLine();
        sendMessageToServer(username);
        getMessageFromServer();
        System.out.print("> ");
        sendMessageToServer(clientReader.readLine());
        String answer = serverReader.readLine();
        System.out.println(answer);
        if (!answer.equals("Start messaging")) {
            return;
        }
        currentUsername = username;
        Thread threadReceivingMessages = new Thread(this::receiveMessages);
        threadReceivingMessages.start();
        sendMessages();
        try {
            threadReceivingMessages.join();
        } catch (InterruptedException e) {

        }
    }

    private void receiveMessages() {
        try {
            String message = serverReader.readLine();
            while (!message.equals("You have left the chat")) {
                System.out.print(ANSI_CLEAR_LINE);
                System.out.println(message);
                System.out.print(String.format("[%s]: ", currentUsername));
                message = serverReader.readLine();
            }
            System.out.println(message);
        } catch (IOException e) {

        }
    }
    private void sendMessages() {
        try {
            System.out.print(String.format("[%s]: ", currentUsername));
            String message = clientReader.readLine();
            while (!message.equals("exit")) {
                sendMessageToServer(message);
                System.out.print(String.format("[%s]: ", currentUsername));
                message = clientReader.readLine();
            }
            sendMessageToServer(message);
        } catch (IOException e) {

        }
    }
    private void executeCommand(String message) throws IOException {
        switch (message) {
            case "exit":
                disconnect();
                break;
            case "signUp":
                signUp();
                break;
            case "signIn":
                signIn();
                break;
        }
    }

    private void disconnect() {
        try {
            serverReader.close();
            serverWriter.close();
            socket.close();
            clientReader.close();
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        System.exit(0);
    }
}
