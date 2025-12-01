package com.comp2042.audio;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Handles sound effect playback for the game.
 * This class manages loading and playing short audio clips such as countdown, click sounds,
 * piece rotations, and game events.
 * 
 * @author Sek Joe Rin
 */
public class SoundEffect {

    private final String resourcePath;
    private MediaPlayer sfxPlayer;
    private double volume = 1.0;

    /**
     * This method creates a new sound effect instance and loads the sound effect file.
     * The sound is preloaded to avoid delay when first played.
     * 
     * @param resourcePath the path to the sound effect file in the resources folder
     */
    public SoundEffect(String resourcePath) {
        this.resourcePath = resourcePath;
        initSFX(); // Preload audio to avoid delay on first play
    }
    
    /**
     * This method initializes the sound effect by loading the sound effect file and setting the volume.
     */
    private void initSFX() {
       
            URL loadSFX = getClass().getClassLoader().getResource(resourcePath);
            if (loadSFX != null) {
                Media sfx = new Media(loadSFX.toExternalForm());
                sfxPlayer = new MediaPlayer(sfx);
                sfxPlayer.setVolume(volume);
            }
       
    }
    
    /**
     * This method plays the sound effect from the beginning.
     */
    public void playSFX() {

        if (sfxPlayer != null) {
            sfxPlayer.seek(Duration.ZERO); // rewind to the start before replaying
            sfxPlayer.play();
        }
    }

   
} 
