package com.comp2042.audio;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Light-weight helper for loading and replaying short audio clips.
 */
public class SoundEffect {

    private final String resourcePath;
    private MediaPlayer sfxPlayer;
    private double volume = 1.0;

    public SoundEffect(String resourcePath) {
        this.resourcePath = resourcePath;
        initSFX(); // Preload audio to avoid delay on first play
    }

    private void initSFX() {
       
            URL loadSFX = getClass().getClassLoader().getResource(resourcePath);
            if (loadSFX != null) {
                Media sfx = new Media(loadSFX.toExternalForm());
                sfxPlayer = new MediaPlayer(sfx);
                sfxPlayer.setVolume(volume);
            }
       
    }
    
    public void playSFX() {

        if (sfxPlayer != null) {
            sfxPlayer.seek(Duration.ZERO); // rewind to the start before replaying
            sfxPlayer.play();
        }
    }

   
} 
