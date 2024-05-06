package edu.school21.tanks.socket;

import edu.school21.tanks.controller.GameSceneController;
import edu.school21.tanks.controller.MainSceneController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private Socket socket;
    private BufferedReader serverReader;
    private BufferedWriter serverWriter;
    private final Stage mainStage;
    private final Stage gameStage;
    private boolean didClientDisconnect = false;
    private final MainSceneController mainController;
    private final GameSceneController gameController;
    private Thread gameThread;
    public ClientSocket(Stage mainStage, Stage gameStage,
                        MainSceneController mainController,
                        GameSceneController gameController) {
        this.mainStage = mainStage;
        this.gameStage = gameStage;
        this.mainController = mainController;
        this.mainController.setSocket(this);
        this.gameController = gameController;
        this.gameController.setSocket(this);
        this.mainStage.show();
    }

    public String getServerMessage() {
        String answer = null;
        try {
            answer = serverReader.readLine();
        } catch (IOException e) {
            disconnect();
        }
        return answer;
    }

    public void sendServerMessage(String msg) throws IOException {
        if (socket != null) {
            serverWriter.write(msg);
            serverWriter.newLine();
            serverWriter.flush();
        }
    }

    public void gameStart() {
        try {
            sendServerMessage("start");
        } catch (IOException e) {}
        Platform.runLater(mainStage::hide);
        Platform.runLater(gameStage::show);
        gameController.resetPlayField();
        gameThread = new Thread(this::readServerMessageForGameWindow);
        gameThread.start();
    }
    public boolean connect(int port) {
        boolean result = true;
        try {
            socket = new Socket("localhost", port);
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Thread thread = new Thread(this::readServerMessageForMainWindow);
            thread.start();
        } catch (Exception e) {
            result = false;
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void disconnectClient() {
        didClientDisconnect = true;
    }
    private void readServerMessageForMainWindow() {
        String msg;
        boolean hasPrintedOne = false;
        try {
            while (true) {
                msg = getServerMessage();
                if (didClientDisconnect) {
                    didClientDisconnect = false;
                    return;
                }
                if (!msg.equals("1")) {
                    mainController.changeStatus(msg);
                    return;
                } else if (!hasPrintedOne) {
                    mainController.changeStatus(msg);
                    hasPrintedOne = true;
                }
            }
        } catch (NullPointerException e) {
            mainController.changeStatus("null");
        }
    }

    public void disconnectGame() {
        disconnect();
        didClientDisconnect = false;
        Platform.runLater(gameStage::hide);
        Platform.runLater(mainStage::show);
    }
    public void readServerMessageForGameWindow() {
        while (true) {
            String msg = getServerMessage();
            if (msg == null) {
                disconnect();
                break;
            }
            switch (msg) {
                case "shoot":
                    Platform.runLater(gameController::shoot);
                    break;
                case "moveLeft":
                    Platform.runLater(() -> gameController.moveTopTank(false));
                    break;
                case "moveRight":
                    Platform.runLater(() -> gameController.moveTopTank(true));
                    break;
                case "ds":
                case "null":
                    Platform.runLater(() -> mainController.changeStatus("null"));
                    Platform.runLater(gameStage::close);
                    break;
                case "over":
                    Platform.runLater(gameController::showStatistics);
                    break;
            }
        }
    }

    public void gameOver() {
        try {
            serverWriter.close();
            serverReader.close();
            socket.close();
            mainController.changeStatus("Game over");
            Platform.runLater(gameStage::hide);
            Platform.runLater(mainStage::show);
        } catch (IOException e) {}
    }
    public void disconnect() {
        try {
            serverWriter.close();
            serverReader.close();
            mainController.disconnectFromServer();
            socket.close();
        } catch (IOException e) {}
    }
}
