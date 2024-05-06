package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;

import java.io.*;
import java.net.Socket;

public class ThreadConnection extends Thread {
    private final Socket socket;
    private final UsersService usersService;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final String clientAddress;
    private boolean isConnected = false;

    public ThreadConnection(Socket socket, UsersService usersService) throws IOException {
        this.socket = socket;
        this.clientAddress = socket.getRemoteSocketAddress().toString().replace("/", "");
        this.usersService = usersService;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void run() {
        printClientAction("connected");
        isConnected = true;
        try {
            sendMessageClient("Hello from Server!");
            while(isConnected) {
                getClientAction();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            disconnect();
        }
    }

    private void printClientAction(String action) {
        System.out.println("[" + clientAddress + "]: " + action);
    }


    private String getMessageFromClient() throws Exception {
        String answer;
        answer = bufferedReader.readLine();
        if (answer == null) {
            throw new NullPointerException("[" + clientAddress + "]: stopped answering");
        }
        return answer;
    }

    private void signUp() throws Exception {
        sendMessageClient("Enter username:");
        String email = getMessageFromClient();
        sendMessageClient("Enter password:");
        String password = getMessageFromClient();
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        usersService.signUp(user);
        sendMessageClient("Successful!");
        printClientAction("signed up as " + email);
        disconnect();
    }
    private void getClientAction() throws Exception {
        String clientWord = getMessageFromClient();
        if (clientWord.equalsIgnoreCase("signUp")) {
            signUp();
        }
    }
    private void sendMessageClient(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    public void disconnect() {
        try {
            if (isConnected) {
                socket.close();
                bufferedReader.close();
                bufferedWriter.close();
                printClientAction("disconnected");
                isConnected = false;
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
}