package controller;

import graph.Graph;
import graph.MapGraphBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Player;
import model.ScoreManager;
import model.incident.IncidentManager;
import model.incident.IncidentRenderer;
import model.map.MapMatrix;
import model.map.TileType;
import model.vehicle.Vehicle;
import model.vehicle.VehicleSpawner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private Canvas canvas;
    @FXML private Label scoreLabel;
    @FXML private AnchorPane panelContent;

    private double zoom = 1.5;

    private Graph graph;
    private Image background;
    private GraphicsContext gc;
    private VehicleSpawner spawner;
    private IncidentManager incidentManager;
    private IncidentRenderer incidentRenderer;
    private Player player;
    private MapMatrix mapMatrix;
    private ScoreManager scoreManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graph = MapGraphBuilder.createCityGraph();
        incidentManager = new IncidentManager();
        incidentRenderer = new IncidentRenderer(incidentManager);
        Vehicle.setIncidentManager(incidentManager);

        spawner = new VehicleSpawner(graph, canvas, incidentManager, incidentRenderer);
        spawner.spawnVehicles(3, 3, 3);

        gc = canvas.getGraphicsContext2D();
        background = new Image(getClass().getResourceAsStream("/Map/Mapa (2).png"));
        mapMatrix = new MapMatrix("/Map/MapaLogico.png", 50, 60);

        player = new Player(532, 312, mapMatrix);
        scoreManager = new ScoreManager(scoreLabel);

        Platform.runLater(() -> {
            canvas.widthProperty().bind(canvas.getScene().widthProperty());
            canvas.heightProperty().bind(canvas.getScene().heightProperty());

            canvas.getScene().setOnKeyPressed(e -> {
                KeyCode code = e.getCode();
                player.handleKey(code);
            });
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                render();
                spawner.checkCollisions();
                checkPlayerActions();
            }
        }.start();
    }

    private void render() {
        gc.setTransform(1, 0, 0, 1, 0, 0);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double canvasW = canvas.getWidth();
        double canvasH = canvas.getHeight();
        double mapW = background.getWidth();
        double mapH = background.getHeight();

        double playerX = player.getX();
        double playerY = player.getY();

        double viewW = canvasW / zoom;
        double viewH = canvasH / zoom;

        double offsetX = playerX - viewW / 2;
        double offsetY = playerY - viewH / 2;

        double maxOffsetX = mapW - viewW;
        double maxOffsetY = mapH - viewH;

        offsetX = Math.max(0, Math.min(offsetX, maxOffsetX));
        offsetY = Math.max(0, Math.min(offsetY, maxOffsetY));

        gc.scale(zoom, zoom);
        gc.translate(-offsetX, -offsetY);

        gc.drawImage(background, 0, 0);
        for (Vehicle v : spawner.getVehicles()) {
            v.paint(gc);
        }
        player.render(gc);
        incidentRenderer.render(gc);
    }

    private void checkPlayerActions() {
        double px = player.getX();
        double py = player.getY();

        int row = (int)(py / mapMatrix.getTileSize());
        int col = (int)(px / mapMatrix.getTileSize());

        if (mapMatrix.isWithinBounds(row, col) && mapMatrix.getTileType(row, col) == TileType.CROSSWALK) {
            scoreManager.addPoints(10);
        }

        for (Vehicle v : spawner.getVehicles()) {
            double dx = v.getX() - px;
            double dy = v.getY() - py;
            double dist = Math.hypot(dx, dy);

            if (dist < 24) {
                scoreManager.subtractPoints(15);
                break;
            }
        }
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public IncidentManager getIncidentManager() {
        return incidentManager;
    }

    public Graph getGraph() {
        return graph;
    }

    public AnchorPane getPanelContent() {
        return panelContent;
    }

    @FXML
    public void loadMonitoringCenter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MonitoringCenter.fxml"));
            Parent view = loader.load();

            MonitoringCenterController controller = loader.getController();
            controller.init(this, incidentManager, scoreManager);

            panelContent.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void loadIncidentPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/IncidentPanel.fxml"));
            Parent view = loader.load();

            IncidentPanelController controller = loader.getController();
            controller.init(this, incidentManager);

            panelContent.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadTrafficMap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TrafficMap.fxml"));
            Parent view = loader.load();

            TrafficMapController controller = loader.getController();
            controller.init(this, graph, incidentManager);

            panelContent.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMainGameView() {
        panelContent.getChildren().clear();
    }
}
