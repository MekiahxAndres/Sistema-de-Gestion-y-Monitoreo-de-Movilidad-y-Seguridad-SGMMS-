package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import model.ScoreManager;
import model.incident.Incident;
import model.incident.IncidentManager;

public class MonitoringCenterController {

    @FXML private Label descriptionLabel;

    private IncidentManager incidentManager;
    private ScoreManager scoreManager;
    private GameController gameController;
    private Incident currentIncident;

    public void init(GameController controller, IncidentManager manager, ScoreManager scoreManager) {
        this.gameController = controller;
        this.incidentManager = manager;
        this.scoreManager = scoreManager;
        loadNextIncident();
    }

    private void loadNextIncident() {
        if (!incidentManager.hasIncidents()) {
            currentIncident = null;
            descriptionLabel.setText("No hay incidentes activos.");
            return;
        }


        currentIncident = incidentManager.peekIncident();
        descriptionLabel.setText("📍 " + currentIncident.getDescription() +
                "\n🧭 Coordenadas: (" + (int) currentIncident.getX() + ", " + (int) currentIncident.getY() + ")" +
                "\n🔥 Severidad: " + currentIncident.getSeverity());
    }

    @FXML
    private void assignAmbulance() {
        checkAssignment("ambulancia");
    }

    @FXML
    private void assignPolice() {
        checkAssignment("policía");
    }

    @FXML
    private void assignFiretruck() {
        checkAssignment("bomberos");
    }

    private void checkAssignment(String type) {
        if (currentIncident == null) return;

        String desc = currentIncident.getDescription().toLowerCase();

        boolean correcto = switch (type) {
            case "ambulancia" -> desc.contains("colisión") || desc.contains("choque");
            case "policía"    -> desc.contains("robo");
            case "bomberos"   -> desc.contains("fuego");
            default -> false;
        };

        if (correcto) {
            scoreManager.addPoints(20);
            showMessage("Bien hecho", "Has asignado correctamente el vehículo.");
        } else {
            scoreManager.subtractPoints(10);
            showMessage("Incorrecto", "Ese vehículo no es adecuado para este incidente.");
        }

        incidentManager.getNextIncident();
        loadNextIncident();
    }

    @FXML
    private void goBack() {
        gameController.loadMainGameView();
    }

    private void showMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Centro de Monitoreo");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
