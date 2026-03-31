package model.incident;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class IncidentRenderer {
    private final List<ExplosionSprite> explosions = new ArrayList<>();
    private final List<FireIcon> fires = new ArrayList<>();
    private final List<RobberyIcon> robberies = new ArrayList<>();
    private final List<CrashIcon> crashes = new ArrayList<>();

    private final Image explosionImage;
    private final Image fireImage;
    private final Image roboImage;
    private final Image choqueImage;

    private final IncidentManager incidentManager;
    private final Random random = new Random();

    private long lastFireTime = 0;
    private long lastRoboTime = 0;

    public IncidentRenderer(IncidentManager manager) {
        this.incidentManager = manager;
        this.explosionImage = new Image(getClass().getResourceAsStream("/resources/explosion1.png"));
        this.fireImage = new Image(getClass().getResourceAsStream("/resources/fire.png"));
        this.roboImage = new Image(getClass().getResourceAsStream("/resources/robo.png"));
        this.choqueImage = new Image(getClass().getResourceAsStream("/resources/choque.png"));
    }

    public void addExplosion(double x, double y) {
        explosions.add(new ExplosionSprite(x, y));
        crashes.add(new CrashIcon(x, y));
    }

    public void addFire(double x, double y) {
        fires.add(new FireIcon(x, y));
        incidentManager.addIncident(new Incident("Fuego por accidente", 2, x, y));
    }

    public void addRobbery(double x, double y) {
        robberies.add(new RobberyIcon(x, y));
        incidentManager.addIncident(new Incident("Robo en progreso", 2, x, y));
    }

    public void render(GraphicsContext gc) {
        Iterator<ExplosionSprite> it = explosions.iterator();
        while (it.hasNext()) {
            ExplosionSprite exp = it.next();
            if (exp.isExpired()) {
                it.remove();
            } else {
                exp.render(gc);
            }
        }

        for (FireIcon f : fires) f.render(gc);
        for (RobberyIcon r : robberies) r.render(gc);
        for (CrashIcon c : crashes) c.render(gc);

        generateRandomFires();
        generateRandomRobberies();
    }

    private void generateRandomFires() {
        long now = System.currentTimeMillis();
        if (now - lastFireTime > 9000 + random.nextInt(4000)) {
            lastFireTime = now;
            double x = 100 + random.nextInt(1100);
            double y = 100 + random.nextInt(700);
            addFire(x, y);
        }
    }

    private void generateRandomRobberies() {
        long now = System.currentTimeMillis();
        if (now - lastRoboTime > 12000 + random.nextInt(5000)) {
            lastRoboTime = now;
            double x = 200 + random.nextInt(800);
            double y = 200 + random.nextInt(600);
            addRobbery(x, y);
        }
    }

    private class ExplosionSprite {
        private final double x, y;
        private int lifetime = 60;

        public ExplosionSprite(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void render(GraphicsContext gc) {
            gc.drawImage(explosionImage, x - 32, y - 32, 64, 64);
            lifetime--;
        }

        public boolean isExpired() {
            return lifetime <= 0;
        }
    }

    private class FireIcon {
        private final double x, y;

        public FireIcon(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void render(GraphicsContext gc) {
            gc.drawImage(fireImage, x - 16, y - 16, 32, 32);
        }
    }

    private class RobberyIcon {
        private final double x, y;

        public RobberyIcon(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void render(GraphicsContext gc) {
            gc.drawImage(roboImage, x - 16, y - 16, 32, 32);
        }
    }

    private class CrashIcon {
        private final double x, y;

        public CrashIcon(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void render(GraphicsContext gc) {
            gc.drawImage(choqueImage, x - 20, y - 20, 40, 40);
        }
    }
}
