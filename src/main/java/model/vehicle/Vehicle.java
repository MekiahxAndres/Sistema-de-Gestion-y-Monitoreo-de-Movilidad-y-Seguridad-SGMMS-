package model.vehicle;

import graph.Graph;
import graph.Vertex;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.incident.IncidentManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Vehicle implements Runnable {

    protected final String id;
    protected volatile double x, y;
    protected final double w, h;
    protected final int stepMillis;
    protected final VehicleType type;
    protected final Image imgRight, imgLeft, imgUp, imgDown;
    protected List<Vertex> path;
    protected int pathIndex = 0;
    protected final AtomicBoolean running = new AtomicBoolean(true);
    protected final Graph graph;
    private final Canvas canvas;

    protected Vehicle(String id, double x, double y, double w, double h,
                      int stepMillis, VehicleType type,
                      Image right, Image left, Image up, Image down,
                      Canvas canvas, Graph graph) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.stepMillis = stepMillis;
        this.type = type;
        this.imgRight = right;
        this.imgLeft = left;
        this.imgUp = up;
        this.imgDown = down;
        this.canvas = canvas;
        this.graph = graph;
    }
    protected static IncidentManager incidentManager;
    public static void setIncidentManager(IncidentManager manager) {
        incidentManager = manager;
    }


    public void setPath(List<Vertex> path) {
        this.path = path;
        this.pathIndex = 0;
    }

    public String getId() { return id; }
    public double getX() { return x; }
    public double getY() { return y; }
    public int getStepMillis() { return stepMillis; }

    public void paint(GraphicsContext gc) {
        Image im = currentImage();
        gc.drawImage(im, x - w / 2, y - h / 2, w, h);
    }

    @Override
    public void run() {
        try {
            while (running.get()) {
                moveStep();
                Thread.sleep(stepMillis);
            }
        } catch (InterruptedException ignored) {}
    }

    public void stop() {
        running.set(false);
    }

    private void moveStep() {
        if (path == null || pathIndex >= path.size()) {
            Vertex current = findClosestVertex();
            Vertex destination = graph.randomVertex();
            while (destination.equals(current)) {
                destination = graph.randomVertex();
            }
            setPath(graph.shortestPath(current.getId(), destination.getId()));
            return;
        }

        Vertex target = path.get(pathIndex);

        if (incidentManager != null && incidentManager.isDangerousZone(target.getX(), target.getY())) {
            Vertex current = findClosestVertex();
            Vertex newDest = graph.randomVertex();
            while (incidentManager.isDangerousZone(newDest.getX(), newDest.getY())) {
                newDest = graph.randomVertex();
            }
            setPath(graph.shortestPath(current.getId(), newDest.getId()));
            return;
        }

        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double dist = Math.hypot(dx, dy);

        if (dist < 2) {
            pathIndex++;
            return;
        }

        double speed = 2.0;
        x += speed * dx / dist;
        y += speed * dy / dist;
    }

    private Vertex findClosestVertex() {
        Vertex closest = null;
        double minDist = Double.MAX_VALUE;

        for (Vertex v : graph.all()) {
            double dist = Math.hypot(x - v.getX(), y - v.getY());
            if (dist < minDist) {
                minDist = dist;
                closest = v;
            }
        }
        return closest;
    }

    private Image currentImage() {
        if (path == null || pathIndex >= path.size()) return imgRight;
        Vertex t = path.get(pathIndex);
        double dx = t.getX() - x;
        double dy = t.getY() - y;

        if (Math.abs(dx) >= Math.abs(dy)) {
            return dx >= 0 ? imgRight : imgLeft;
        } else {
            return dy >= 0 ? imgDown : imgUp;
        }
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public Direction getCurrentDirection() {
        if (path == null || pathIndex >= path.size()) return Direction.RIGHT;
        Vertex target = path.get(pathIndex);
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        if (Math.abs(dx) >= Math.abs(dy)) {
            return dx >= 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return dy >= 0 ? Direction.DOWN : Direction.UP;
        }
    }

    protected static Image flipVertically(Image original) {
        javafx.scene.image.WritableImage flipped = new javafx.scene.image.WritableImage(
                (int) original.getWidth(), (int) original.getHeight());
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                flipped.getPixelWriter().setArgb(x, (int) original.getHeight() - 1 - y,
                        original.getPixelReader().getArgb(x, y));
            }
        }
        return flipped;
    }
}
