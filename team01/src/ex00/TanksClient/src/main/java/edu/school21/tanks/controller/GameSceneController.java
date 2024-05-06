package edu.school21.tanks.controller;
import edu.school21.tanks.socket.ClientSocket;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class GameSceneController implements Initializable {

    private boolean isBulletShot = false;
    private boolean isGameOn = true;
    private ClientSocket socket;
    private AtomicInteger botHealth;
    private AtomicInteger topHealth;
    @FXML
    private StackPane pane;
    @FXML
    private ProgressBar topHealthBar;
    @FXML
    private ProgressBar botHealthBar;
    @FXML
    private ImageView topTank;
    @FXML
    private ImageView botTank;
    private int bulletsShot;
    private int bulletsHit;
    private int bulletsMissed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        botHealth = new AtomicInteger(100);
        topHealth = new AtomicInteger(100);
    }

    public void setSocket(ClientSocket socket) {
        this.socket = socket;
    }

    public void moveTopTank(boolean way) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), topTank);
        if (way && topTank.getTranslateX() - 100 > -700) {
            transition.setByX(-100);
            transition.setAutoReverse(false);
        } else if (!way && topTank.getTranslateX() + 100 < 700 ) {
            transition.setByX(100);
            transition.setAutoReverse(false);
        }
        transition.play();
    }
    public void botTankAction(KeyEvent e) {
        if (e.getCode() == KeyCode.A && botTank.getTranslateX() - 100 > -700 && isGameOn) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), botTank);
            transition.setByX(-100);
            transition.setAutoReverse(false);
            transition.play();
            try {
                socket.sendServerMessage("moveLeft");
            } catch (IOException exception) {}
        } else if (e.getCode() == KeyCode.D && botTank.getTranslateX() + 100 < 700 && isGameOn) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), botTank);
            transition.setByX(100);
            transition.setAutoReverse(false);
            transition.play();
            try {
                socket.sendServerMessage("moveRight");
            } catch (IOException exception) {}
        } else if (e.getCode() == KeyCode.SPACE && !isBulletShot && isGameOn) {
            shoot(botTank, topHealth, topHealthBar, 1);
            try {
                socket.sendServerMessage("shoot");
            } catch (IOException exception) {}
        }
    }

    public void stopShoot(KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE) {
            isBulletShot = false;
        }
    }

    private void shoot(ImageView tank, AtomicInteger health, ProgressBar healthbar, int direction) {
        ImageView view = new ImageView();
        view.setImage(new Image("/bullet.png"));
        view.setFitHeight(20);
        view.setFitWidth(10);
        pane.getChildren().add(view);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), view);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setFromX(tank.getTranslateX());
        transition.setFromY(direction * 140);
        transition.setToY(direction * -140);
        transition.setAutoReverse(false);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ImageView tankview;
                if (tank.equals(botTank)) {
                    tankview = topTank;
                } else {
                    tankview = botTank;
                }
                checkCollision(tankview, view, health, healthbar);
                pane.getChildren().remove(view);

            }
        });
        transition.play();
        bulletsShot++;
        isBulletShot = direction == 1;
    }

    private void checkCollision(ImageView tank, ImageView bullet, AtomicInteger health, ProgressBar healthbar) {
        if (bullet.getTranslateX() > (tank.getTranslateX() - 50)
                && bullet.getTranslateX() < (tank.getTranslateX() + 50)) {
            health.set(health.intValue() == 0 ? 0 : health.intValue() - 5);
            healthbar.setProgress((double)health.intValue() / 100);
            bulletsHit++;
            try {
                socket.sendServerMessage("hit");
            } catch (IOException e) {}
            checkHealth();
        } else
            bulletsMissed++;
    }

    public void shoot() {
        shoot(topTank, botHealth, botHealthBar, -1);
    }

    private void checkHealth() {
        ImageView view;
        if (botHealth.intValue() == 0) {
            view = new ImageView(new Image("/gameover.png"));
            view.setFitHeight(600);
            view.setFitWidth(1400);
            pane.getChildren().add(view);
            isGameOn = false;
            try {
                socket.sendServerMessage("over");
            } catch (IOException e) {}
        } else if (topHealth.intValue() == 0) {
            view = new ImageView(new Image("/youwin.png"));
            view.setFitHeight(400);
            view.setFitWidth(600);
            pane.getChildren().add(view);
            try {
                socket.sendServerMessage("over");
            } catch (IOException e) {}
            isGameOn = false;
        }
    }

    public void resetPlayField() {
        botHealth.set(100);
        topHealth.set(100);
        botHealthBar.setProgress(1);
        topHealthBar.setProgress(1);
        isGameOn = true;
        isBulletShot = false;
        botTank.setTranslateX(0);
        topTank.setTranslateX(0);
        bulletsHit = 0;
        bulletsMissed = 0;
        bulletsShot = 0;
    }

    public void showStatistics() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Statistics");

        Button closeButton = new Button("Close");
        Label label = new Label(String.format("Overall shots:\t%s;\nMissed:\t\t\t%s;\nHit:\t\t\t\t%s;",
                bulletsShot, bulletsMissed, bulletsHit));
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                modalStage.close();
                socket.gameOver();
            }
        });
        BorderPane modalLayout = new BorderPane();
        modalLayout.setCenter(label);
        modalStage.setResizable(false);
        modalLayout.setBottom(closeButton);
        BorderPane.setAlignment(closeButton, Pos.CENTER_RIGHT);
        Scene modalScene = new Scene(modalLayout, 150, 150);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
        modalStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                socket.gameOver();
            }
        });
    }
}
