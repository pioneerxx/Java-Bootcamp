package edu.school21.tanks.controller;

import edu.school21.tanks.socket.ClientSocket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainSceneController {

    @FXML
    private Label statusLabel;
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    private ClientSocket socket;
    @FXML
    private TextField textField;

    public void setSocket(ClientSocket socket) {
        this.socket = socket;
    }
    @FXML
    private void limitCharacters() {
        String text = textField.getText();
        if (!text.isEmpty()) {
            char c = text.charAt(text.length() - 1);
            if (!Character.isDigit(c)) {
                textField.setText(text.substring(0, text.length() - 1));
                return;
            }
        }
        if (text.length() > 5) {
            textField.setText(text.substring(0, 5));
            textField.positionCaret(5);
        }
    }

    @FXML
    private void connectToServer() {
        if (!textField.getText().isEmpty() && socket.connect(Integer.parseInt(textField.getText()))) {
            connectButton.setVisible(false);
            disconnectButton.setVisible(true);
        } else {
            changeStatus("null");
        }
    }

    public void changeStatus(String status) {
        switch (status) {
            case "1":
                Platform.runLater(() -> statusLabel.setText("Waiting another player..."));
                Platform.runLater(() -> statusLabel.setTextFill(Color.WHITE));
                break;
            case "2":
                Platform.runLater(() -> statusLabel.setText("Game starts!"));
                Platform.runLater(() -> statusLabel.setTextFill(Color.WHITE));
                socket.gameStart();
                break;
            case "null":
                Platform.runLater(() -> statusLabel.setText("Server unavailable"));
                Platform.runLater(() -> statusLabel.setTextFill(Color.DARKRED));
                disconnectButton.setVisible(false);
                connectButton.setVisible(true);
                break;
            case "ds":
                Platform.runLater(() -> statusLabel.setText("Disconnected"));
                Platform.runLater(() -> statusLabel.setTextFill(Color.WHITE));
                break;
            case "Game over":
                Platform.runLater(() -> statusLabel.setText("Game over"));
                Platform.runLater(() -> statusLabel.setTextFill(Color.WHITE));
                break;
        }
    }

    @FXML
    public void disconnectFromServer() {
        changeStatus("ds");
        socket.disconnectClient();
        try {
            socket.sendServerMessage("ds");
        } catch (IOException e) {}
        disconnectButton.setVisible(false);
        connectButton.setVisible(true);
    }

}
