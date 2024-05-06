package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UsersServiceImpl;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class ThreadConnection extends Thread {
    private final Socket socket;

    private final Server server;
    private User currentUser;
    private final UsersServiceImpl usersService;
    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final String clientAddress;
    private String currentName;
    public boolean isConnected = false;
    public boolean isChatting = false;

    public ThreadConnection(Socket socket, UsersServiceImpl usersService,
                            UsersRepository usersRepository,
                            MessageRepository messageRepository, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.clientAddress = socket.getRemoteSocketAddress().toString().replace("/", "");
        currentName = clientAddress;
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void run() {
        printClientAction("connected");
        isConnected = true;
        try {
            sendMessageToClient("Hello from Server!");
            while(isConnected) {
                getClientAction();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            disconnect();
        }
    }

    private void printClientAction(String action) {
        System.out.println("[" + currentName + "]: " + action);
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
        sendMessageToClient("Enter username:");
        String email = getMessageFromClient();
        sendMessageToClient("Enter password:");
        String password = getMessageFromClient();
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        usersService.signUp(user);
        sendMessageToClient("Successful!");
        printClientAction("signed up as " + email);
    }

    private void signIn() throws Exception {
        sendMessageToClient("Enter username:");
        String email = getMessageFromClient();
        sendMessageToClient("Enter password:");
        String password = getMessageFromClient();
        Optional<User> user = usersRepository.findByEmail(email);
        if (!user.isPresent()) {
            sendMessageToClient("No such user detected");
            return;
        }
        if (!usersService.encoder.matches(password, user.get().getPassword())) {
            sendMessageToClient("PasswordMismatch");
            return;
        }
        currentUser = user.get();
        printClientAction("signed in as " + email);
        currentName = currentUser.getEmail();
        isChatting = true;
        sendMessageToClient("Start messaging");
        startMessaging();

    }
    private void signOut() throws Exception {
        printClientAction("logged out");
        currentName = clientAddress;
        currentUser = null;
        isChatting = false;
        sendMessageToClient("You have left the chat");
    }
    private void startMessaging() throws Exception {
        String userMessage = getMessageFromClient();
        while (!userMessage.equals("exit")) {
            server.sendMessageOut(String.format("[%s]: %s", currentName, userMessage), this);
            messageRepository.save(new Message(currentUser.getId(), userMessage));
            userMessage = getMessageFromClient();
        }
        signOut();
    }
    private void getClientAction() throws Exception {
        String clientWord = getMessageFromClient();
        switch (clientWord) {
            case "signUp":
                signUp();
                break;
            case "signIn":
                signIn();
                break;
        }
    }
    public void sendMessageToClient(String message) throws IOException {
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