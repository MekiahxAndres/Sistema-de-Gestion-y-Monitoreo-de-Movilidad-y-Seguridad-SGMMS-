package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.incident.Incident;
import model.incident.IncidentManager;

import java.io.IOException;
import java.util.List;

public class IncidentPanelController {

    @FXML private VBox incidentList;

    private GameController gameController;
    private IncidentManager incidentManager;
    private Timeline updateTimer;

    public void init(GameController gameController, IncidentManager incidentManager) {
        this.gameController = gameController;
        this.incidentManager = incidentManager;

        startRealtimeUpdates();
    }

    private void startRealtimeUpdates() {
        updateTimer = new Timeline(new KeyFrame(Duration.seconds(2), e -> updateIncidentList()));
        updateTimer.setCycleCount(Timeline.INDEFINITE);
        updateTimer.play();
        updateIncidentList();
    }

    private void updateIncidentList() {
        List<Incident> all = incidentManager.getAllIncidentsSorted();
        incidentList.getChildren().clear();

        if (all.isEmpty()) {
            Label noIncidents = new Label("No hay incidentes registrados.");
            noIncidents.setStyle("-fx-font-size: 16px;");
            incidentList.getChildren().add(noIncidents);
            return;
        }

        for (Incident i : all) {
            HBox row = new HBox(10);
            row.setStyle("-fx-alignment: CENTER_LEFT;");

            ImageView icon = new ImageView(getIconFor(i));
            icon.setFitWidth(24);
            icon.setFitHeight(24);

            Label text = new Label( i.getDescription() +
                    " | 🧭 (" + (int) i.getX() + ", " + (int) i.getY() + ")" +
                    " | 🔥 Severidad: " + i.getSeverity());
            text.setStyle("-fx-font-size: 15px;");

            row.getChildren().addAll(icon, text);
            incidentList.getChildren().add(row);
        }
    }

    private Image getIconFor(Incident i) {
        String desc = i.getDescription().toLowerCase();
        if (desc.contains("fuego")) {
            return new Image(getClass().getResourceAsStream("/resources/fire.png"));
        } else if (desc.contains("robo")) {
            return new Image(getClass().getResourceAsStream("/resources/robo.png"));
        } else {
            return new Image(getClass().getResourceAsStream("/resources/choque.png"));
        }
    }

    @FXML
    public void goBack() {
        updateTimer.stop();
        gameController.loadMainGameView();
    }
}
