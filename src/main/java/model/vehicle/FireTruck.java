package model.vehicle;

import graph.Graph;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class FireTruck extends Vehicle {

    public FireTruck(String id, double x, double y, Canvas canvas, Graph graph) {
        super(
                id, x, y,
                64, 34,
                20,
                VehicleType.FIRE_TRUCK,
                load("Red_BOXTRUCK_CLEAN_All_000.png"),
                load("Red_BOXTRUCK_CLEAN_All_024.png"),
                load("Red_BOXTRUCK_CLEAN_All_036.png"),
                load("Red_BOXTRUCK_CLEAN_All_012.png"),
                canvas,
                graph
        );

    }

    private static Image load(String filename) {
        return new Image(FireTruck.class.getResourceAsStream("/vehicles/" + filename));
    }
}