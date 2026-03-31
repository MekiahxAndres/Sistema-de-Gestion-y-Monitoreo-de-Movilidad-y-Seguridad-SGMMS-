package model.vehicle;

import graph.Graph;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class PoliceCar extends Vehicle {

    public PoliceCar(String id, double x, double y, Canvas canvas, Graph graph) {
        super(
                id, x, y,
                56, 30,
                25,
                VehicleType.POLICE,
                load("POLICE_CLEAN_ALLD0000.png"),
                load("POLICE_CLEAN_ALLD0024 (1).png"),
                load("POLICE_CLEAN_ALLD0036.png"),
                load("POLICE_CLEAN_ALLD0012.png"),
                canvas,
                graph
        );


    }

    private static Image load(String filename) {
        return new Image(PoliceCar.class.getResourceAsStream("/vehicles/" + filename));
    }
}