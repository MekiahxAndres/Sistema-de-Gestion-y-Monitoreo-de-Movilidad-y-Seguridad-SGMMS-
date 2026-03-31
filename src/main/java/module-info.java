module org.example.integradora2apo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    opens controller to javafx.fxml;
    opens gui to javafx.fxml;

    exports gui;
    exports controller;
    exports model;
    exports model.vehicle;
    exports graph;
}
