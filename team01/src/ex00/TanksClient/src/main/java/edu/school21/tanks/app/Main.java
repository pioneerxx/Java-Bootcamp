package edu.school21.tanks.app;

import edu.school21.tanks.controller.GameSceneController;
import edu.school21.tanks.controller.MainSceneController;
import edu.school21.tanks.socket.ClientSocket;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Setting up main stage
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setResizable(false);
        Scene primaryScene = new Scene(root);
        primaryScene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(primaryScene);

        //Setting up game stage
        Stage gameStage = new Stage();
        FXMLLoader loader2 = new FXMLLoader();
        URL xmlUrl2 = getClass().getResource("/gameScene.fxml");
        loader2.setLocation(xmlUrl2);
        Parent root2 = loader2.load();
        GameSceneController controller = loader2.getController();
        gameStage.setResizable(false);
        Scene gameScene = new Scene(root2);
        gameScene.getStylesheets().addAll(this.getClass().getResource("/gameStyle.css").toExternalForm());

        //Creating key events for game stage
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.botTankAction(keyEvent);
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.stopShoot(keyEvent);
            }
        });


        //Start
        gameStage.setScene(gameScene);
        ClientSocket socket = new ClientSocket(primaryStage, gameStage, loader.getController(), loader2.getController());

        gameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                socket.disconnectGame();
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            MainSceneController ctr = loader.getController();
            @Override
            public void handle(WindowEvent windowEvent) {
                ctr.disconnectFromServer();
            }
        });
    }
}