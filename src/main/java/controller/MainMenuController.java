package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainMenuController {

    @FXML private Button startButton;
    @FXML private Button exitButton;
    @FXML private ImageView backgroundImage;

    private final SoundPlayer soundPlayer = new SoundPlayer();

    @FXML
    public void initialize() {

        backgroundImage.setImage(new Image(getClass().getResourceAsStream("/Images/Fondo_Menu.jpg")));


        soundPlayer.playLoop("/sounds/soothing-spa-182790.wav");


        startButton.setOnAction(e -> openGameScene());
        exitButton.setOnAction(e -> {
            soundPlayer.stop();
            System.exit(0);
        });


        startButton.setOnMouseEntered(e -> soundPlayer.playOnce("/sounds/switchButton.wav"));
        exitButton.setOnMouseEntered(e -> soundPlayer.playOnce("/sounds/switchButton.wav"));


        Platform.runLater(() -> {
            backgroundImage.fitWidthProperty().bind(startButton.getScene().widthProperty());
            backgroundImage.fitHeightProperty().bind(startButton.getScene().heightProperty());
        });
    }

    @FXML
    private void zoomIn(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.1);
        btn.setScaleY(1.1);
    }

    @FXML
    private void zoomOut(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.0);
        btn.setScaleY(1.0);
    }


    private void openGameScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameView.fxml"));
            Parent gameRoot = loader.load();

            Stage mainStage = (Stage) startButton.getScene().getWindow();
            Scene gameScene = new Scene(gameRoot);
            mainStage.setScene(gameScene);
            mainStage.setFullScreen(true);
            mainStage.setTitle("SGMMS - Simulador");
            mainStage.show();

            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.millis(100));
            delay.setOnFinished(e -> showInfoPopup(mainStage));
            delay.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInfoPopup(Stage ownerStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/InfoPopup.fxml"));
            Parent infoRoot = loader.load();

            Stage infoStage = new Stage(StageStyle.UTILITY);
            infoStage.setTitle("Información del Juego");
            infoStage.setScene(new Scene(infoRoot));
            infoStage.initOwner(ownerStage);
            infoStage.initModality(Modality.WINDOW_MODAL);
            infoStage.setResizable(false);

            infoRoot.setOnKeyPressed(event -> {
                if (event.getCode().toString().equals("ESCAPE")) {
                    infoStage.close();
                }
            });

            infoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
