package sockets.Client;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private Socket socket;
    private BufferedReader clientReader;
    private BufferedReader serverReader;
    private BufferedWriter serverWriter;

    private boolean isCommandInProgress = false;

    public void start(int port) throws IOException {
        socket = new Socket("localhost", port);
        clientReader = new BufferedReader(new InputStreamReader(System.in));
        serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        while(socket.isConnected()) {
//            try {
//                getMessageFromServer();
//                sendMessageToServer();
//            } catch (IOException e) {
//                System.err.println(e.getMessage());
//                break;
//            }
//        }
        getMessageFromServer();
        startClientWork();
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
        if (serverMessage.equals("Successful!")) {
            disconnect();
        }
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

    private void executeCommand(String message) throws IOException {
        switch (message) {
            case "exit":
                disconnect();
                break;
            case "signUp":
                signUp();
                break;
        }
        isCommandInProgress = false;
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
