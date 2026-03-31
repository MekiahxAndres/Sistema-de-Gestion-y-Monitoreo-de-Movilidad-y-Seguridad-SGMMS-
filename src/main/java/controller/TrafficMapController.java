
package controller;

import graph.Graph;
import graph.Vertex;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.incident.Incident;
import model.incident.IncidentManager;

import java.util.*;

public class TrafficMapController {

    @FXML private Canvas miniCanvas;

    private GameController gameController;
    private Graph graph;
    private IncidentManager incidentManager;
    private GraphicsContext gc;
    private Vertex pointA = null, pointB = null;
    private Image background;

    public void init(GameController gameController, Graph graph, IncidentManager manager) {
        this.gameController = gameController;
        this.graph = graph;
        this.incidentManager = manager;
        this.gc = miniCanvas.getGraphicsContext2D();
        this.background = new Image(getClass().getResourceAsStream("/Map/Mapa (2).png"));
        drawMap();
    }

    private void drawMap() {
        gc.clearRect(0, 0, miniCanvas.getWidth(), miniCanvas.getHeight());
        gc.drawImage(background, 0, 0, miniCanvas.getWidth(), miniCanvas.getHeight());

        drawIncidents();

        if (pointA != null) drawPoint(pointA, Color.GREEN);
        if (pointB != null) drawPoint(pointB, Color.BLUE);

        if (pointA != null && pointB != null) {
            List<Vertex> path = bfsAvoidingDanger(pointA, pointB);
            drawPath(path);
        }
    }

    private void drawPoint(Vertex v, Color color) {
        gc.setFill(color);
        gc.fillOval(scaleX(v.getX()) - 5, scaleY(v.getY()) - 5, 10, 10);
    }

    private void drawPath(List<Vertex> path) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex v1 = path.get(i);
            Vertex v2 = path.get(i + 1);
            gc.strokeLine(scaleX(v1.getX()), scaleY(v1.getY()), scaleX(v2.getX()), scaleY(v2.getY()));
        }
    }

    private void drawIncidents() {
        gc.setFill(Color.ORANGE);
        for (Incident i : incidentManager.getAllIncidentsSorted()) {
            String desc = i.getDescription().toLowerCase();
            if (desc.contains("choque") || desc.contains("colisi")) {
                gc.fillOval(scaleX(i.getX()) - 4, scaleY(i.getY()) - 4, 8, 8);
            }
        }
    }

    private double scaleX(double x) {
        return x * miniCanvas.getWidth() / background.getWidth();
    }

    private double scaleY(double y) {
        return y * miniCanvas.getHeight() / background.getHeight();
    }

    @FXML
    public void handleClick(MouseEvent e) {
        double clickX = e.getX() * background.getWidth() / miniCanvas.getWidth();
        double clickY = e.getY() * background.getHeight() / miniCanvas.getHeight();

        Vertex closest = null;
        double minDist = Double.MAX_VALUE;
        for (Vertex v : graph.all()) {
            double dx = v.getX() - clickX;
            double dy = v.getY() - clickY;
            double dist = Math.hypot(dx, dy);
            if (dist < minDist) {
                minDist = dist;
                closest = v;
            }
        }

        if (pointA == null) {
            pointA = closest;
        } else if (pointB == null) {
            pointB = closest;
        } else {
            pointA = closest;
            pointB = null;
        }
        drawMap();
    }

    private List<Vertex> bfsAvoidingDanger(Vertex start, Vertex goal) {
        List<Vertex> empty = new ArrayList<>();
        if (start == null || goal == null) return empty;

        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Vertex> pred = new HashMap<>();
        Queue<Vertex> queue = new ArrayDeque<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            if (current.equals(goal)) break;

            for (Vertex neighbor : current.getNeighbours()) {
                if (!visited.contains(neighbor) &&
                        !incidentManager.isDangerousZone(neighbor.getX(), neighbor.getY())) {
                    visited.add(neighbor);
                    pred.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (!pred.containsKey(goal)) return empty;

        List<Vertex> path = new ArrayList<>();
        for (Vertex at = goal; at != null; at = pred.get(at)) {
            path.add(0, at);
        }
        return path;
    }

    @FXML
    public void goBack() {
        gameController.loadMainGameView();
    }
}
