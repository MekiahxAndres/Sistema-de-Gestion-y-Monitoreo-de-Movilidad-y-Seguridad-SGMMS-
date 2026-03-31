package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import model.map.MapMatrix;

public class Player {
    private double x, y;
    private final double speed = 2;
    private final MapMatrix map;

    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction currentDirection = Direction.DOWN;

    private final Image[][] frames = new Image[4][8]; // 4 direcciones × 8 frames
    private int frameIndex = 0;
    private int frameCounter = 0;
    private final int frameSpeed = 6;

    public Player(double x, double y, MapMatrix map) {
        this.x = x;
        this.y = y;
        this.map = map;
        loadFrames();
    }

    private void loadFrames() {
        for (int i = 0; i < 8; i++) {
            frames[0][i] = load("/Player/up/run_up_" + i + ".png");
            frames[1][i] = load("/Player/down/run_down_" + i + ".png");
            frames[2][i] = load("/Player/left/run_left_" + i + ".png");
            frames[3][i] = load("/Player/right/run_right_" + i + ".png");
        }
    }

    private Image load(String path) {
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            System.err.println("⚠ Imagen no encontrada: " + path);
        }
        return new Image(stream);
    }

    public void handleKey(KeyCode code) {
        boolean moved = false;

        switch (code) {
            case W -> {
                currentDirection = Direction.UP;
                moved = move(0, -speed);
            }
            case S -> {
                currentDirection = Direction.DOWN;
                moved = move(0, speed);
            }
            case A -> {
                currentDirection = Direction.LEFT;
                moved = move(-speed, 0);
            }
            case D -> {
                currentDirection = Direction.RIGHT;
                moved = move(speed, 0);
            }
        }

        if (moved) {
            frameCounter++;
            if (frameCounter >= frameSpeed) {
                frameIndex = (frameIndex + 1) % 8;
                frameCounter = 0;
            }
        } else {
            frameIndex = 0;
        }
    }

    public boolean move(double dx, double dy) {
        double nextX = x + dx;
        double nextY = y + dy;

        int col = (int) (nextX / map.getTileSize());
        int row = (int) (nextY / map.getTileSize());

        if (map.isWithinBounds(row, col) && map.isWalkable(row, col)) {
            x = nextX;
            y = nextY;
            return true;
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        int dirIndex = switch (currentDirection) {
            case UP -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 3;
        };
        gc.drawImage(frames[dirIndex][frameIndex], x, y);
    }

    public double getX() { return x; }

    public double getY() { return y; }
}