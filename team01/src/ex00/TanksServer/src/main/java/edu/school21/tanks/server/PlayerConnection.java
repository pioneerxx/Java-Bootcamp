package edu.school21.tanks.server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerConnection {
    private final Socket socket;
    private PlayerConnection secondPlayer;
    private final AtomicInteger playersConnected;
    private final BufferedReader clientReader;
    private final BufferedWriter clientWriter;

    public PlayerConnection(Socket socket, AtomicInteger playersConnected) throws IOException {
        this.socket = socket;
        this.playersConnected = playersConnected;
        clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clientWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Thread thread = new Thread(this::listenToDisconnectFromMainWindow);
        thread.start();
    }

    public void sendMessageToClient(String msg) throws IOException {
        clientWriter.write(msg);
        clientWriter.newLine();
        clientWriter.flush();
    }

    public String getMessageFromClient() {
        String msg;
        try {
            msg = clientReader.readLine();
        } catch (NullPointerException | IOException e) {
            msg = null;
        }
        return msg;
    }
    public void listenToMoves() {
        String msg;
        while (true) {
            msg = getMessageFromClient();
            if (msg.equals("ds")) {
                disconnect();
                return;
            } else {

            }
        }
    }

    public void setSecondPlayer(PlayerConnection player) {
        secondPlayer = player;
    }

    public PlayerConnection getSecondPlayer() {
        return secondPlayer;
    }

    public void disconnect() {
        try {
            sendMessageToClient("ds");
            clientWriter.close();
            clientReader.close();
            socket.close();
            playersConnected.decrementAndGet();
        } catch (IOException e) {}
    }

    private void listenToDisconnectFromMainWindow() {
        String msg;
        while (true) {
            msg = getMessageFromClient();
            if (msg.equals("ds")) {
                disconnect();
                return;
            }
            if (msg.equals("start")) {
                return;
            }
        }
    }
}
