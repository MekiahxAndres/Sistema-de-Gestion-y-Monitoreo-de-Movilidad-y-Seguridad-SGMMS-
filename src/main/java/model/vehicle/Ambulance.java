package model.vehicle;

import graph.Graph;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Ambulance extends Vehicle {

    public Ambulance(String id, double x, double y, Canvas canvas, Graph graph) {
        super(
                id, x, y,
                58, 32,
                15,
                VehicleType.AMBULANCE,
                load("AMBULANCE_CLEAN_ALLD0000.png"), // ➡️ derecha
                load("AMBULANCE_CLEAN_ALLD0024.png"), // ⬅️ izquierda
                load("AMBULANCE_CLEAN_ALLD0036.png"), // ⬆️ arriba
                load("AMBULANCE_CLEAN_ALLD0012.png"), // ⬇️ abajo
                canvas,
                graph
        );
    }

    private static Image load(String filename) {
        return new Image(Ambulance.class.getResourceAsStream("/vehicles/" + filename));
    }
}