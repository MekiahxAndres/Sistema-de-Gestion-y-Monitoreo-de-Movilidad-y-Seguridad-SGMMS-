package controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {

    private Clip clip;

    public void playLoop(String path) {
        try {
            URL soundURL = getClass().getResource(path);
            if (soundURL == null) {
                System.err.println("Archivo no encontrado: " + path);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playOnce(String path) {
        new Thread(() -> {
            try {
                URL soundURL = getClass().getResource(path);
                if (soundURL == null) {
                    System.err.println("Archivo no encontrado: " + path);
                    return;
                }

                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                Clip singleClip = AudioSystem.getClip();
                singleClip.open(audioIn);
                singleClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
