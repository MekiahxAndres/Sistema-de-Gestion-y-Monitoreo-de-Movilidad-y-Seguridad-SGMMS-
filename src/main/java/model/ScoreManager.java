package model;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class ScoreManager {

    private int score = 0;
    private Label scoreLabel;

    public ScoreManager(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
        updateLabel();
    }

    public void addPoints(int pts) {
        score += pts;
        updateLabel();
    }

    public void subtractPoints(int pts) {
        score = Math.max(0, score - pts);
        updateLabel();
    }

    public int getScore() {
        return score;
    }

    private void updateLabel() {
        if (scoreLabel != null) {
            Platform.runLater(() -> scoreLabel.setText("Puntaje: " + score));
        }
    }
}
