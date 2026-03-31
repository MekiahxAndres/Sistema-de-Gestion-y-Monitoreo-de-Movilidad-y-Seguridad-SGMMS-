package model.vehicle;

import graph.Graph;
import graph.Vertex;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import model.incident.Incident;
import model.incident.IncidentManager;
import model.incident.IncidentRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehicleSpawner {

    private final Graph graph;
    private final Canvas canvas;
    private final List<Vehicle> vehicles;
    private final Random rnd;
    private final IncidentManager incidentManager;
    private final IncidentRenderer incidentRenderer;

    public VehicleSpawner(Graph graph, Canvas canvas, IncidentManager manager, IncidentRenderer renderer) {
        this.graph = graph;
        this.canvas = canvas;
        this.vehicles = new ArrayList<>();
        this.rnd = new Random();
        this.incidentManager = manager;
        this.incidentRenderer = renderer;

        Thread generator = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    Platform.runLater(() -> spawnVehicle(randomType()));
                } catch (InterruptedException ignored) {}
            }
        });
        generator.setDaemon(true);
        generator.start();
    }

    private VehicleType randomType() {
        return VehicleType.values()[rnd.nextInt(VehicleType.values().length)];
    }

    public void spawnVehicles(int countAmb, int countFire, int countPolice) {
        for (int i = 0; i < countAmb; i++) spawnVehicle(VehicleType.AMBULANCE);
        for (int i = 0; i < countFire; i++) spawnVehicle(VehicleType.FIRE_TRUCK);
        for (int i = 0; i < countPolice; i++) spawnVehicle(VehicleType.POLICE);
    }

    public void spawnVehicle(VehicleType type) {
        Vertex origin = graph.randomVertex();
        boolean ocupado = vehicles.stream().anyMatch(v ->
                Math.hypot(v.getX() - origin.getX(), v.getY() - origin.getY()) < 30
        );
        if (ocupado) return;

        Vertex dest = graph.randomVertex();
        while (dest.equals(origin)) dest = graph.randomVertex();

        Vehicle vehicle = switch (type) {
            case AMBULANCE -> new Ambulance("AMB-" + System.nanoTime(), origin.getX(), origin.getY(), canvas, graph);
            case FIRE_TRUCK -> new FireTruck("FIR-" + System.nanoTime(), origin.getX(), origin.getY(), canvas, graph);
            case POLICE -> new PoliceCar("POL-" + System.nanoTime(), origin.getX(), origin.getY(), canvas, graph);
        };

        vehicle.setPath(graph.shortestPath(origin.getId(), dest.getId()));
        vehicles.add(vehicle);
        new Thread(vehicle).start();
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void checkCollisions() {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v1 = vehicles.get(i);
            for (int j = i + 1; j < vehicles.size(); j++) {
                Vehicle v2 = vehicles.get(j);

                double dx = v1.getX() - v2.getX();
                double dy = v1.getY() - v2.getY();
                double dist = Math.hypot(dx, dy);

                if (dist < 26) {
                    // Detener ambos vehículos
                    v1.stop();
                    v2.stop();

                    // Posición promedio del choque
                    double x = (v1.getX() + v2.getX()) / 2;
                    double y = (v1.getY() + v2.getY()) / 2;

                    // Registrar incidentes
                    incidentManager.addIncident(new Incident("Colisión entre " + v1.getId() + " y " + v2.getId(), 3, x, y));
                    incidentRenderer.addExplosion(x, y);

                    double fireX = x + rnd.nextInt(40) - 20;
                    double fireY = y + rnd.nextInt(40) - 20;
                    incidentManager.addIncident(new Incident("Fuego por accidente", 2, fireX, fireY));
                    incidentRenderer.addFire(fireX, fireY);

                    // Retirar vehículos de la lista
                    vehicles.remove(v1);
                    vehicles.remove(v2);
                    return;
                }
            }
        }
    }
}
