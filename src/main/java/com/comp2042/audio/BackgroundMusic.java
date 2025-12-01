package com.comp2042.audio;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Handles background music playback for the game.
 * This class manages loading, playing, pausing, and stopping background music
 * that loops continuously during gameplay.
 * 
 * @author Sek Joe Rin
 */
public class BackgroundMusic {
    
    private final String resourcePath;
    private MediaPlayer bgmPlayer;
    private double volume = 0.15;

    /**
     * This method creates a new background music instance and loads the music file.
     * The music is preloaded to avoid delay when first played.
     * 
     * @param resourcePath the path to the music file in the resources folder
     */
    public BackgroundMusic(String resourcePath) {
        this.resourcePath = resourcePath;
        initBGM(); 
    }

    /**
     * This method initializes the background music by loading the music file and setting the volume.
     */
    private void initBGM() {
        URL loadBGM = getClass().getClassLoader().getResource(resourcePath);
        if (loadBGM != null) {
            Media bgm = new Media(loadBGM.toExternalForm());
            bgmPlayer = new MediaPlayer(bgm);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
            bgmPlayer.setVolume(volume);
        }
    }

    /**
     * Starts playing the background music.
     * If the music is already playing, this method does nothing.
     */
    public void playBGM() {
        if (bgmPlayer != null && bgmPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            bgmPlayer.play();
        }
    }

    /**
     * Pauses the background music playback.
     * If the music is not currently playing, this method does nothing.
     */
    public void pauseBGM() {
        if (bgmPlayer != null && bgmPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            bgmPlayer.pause();
        }
    }

    /**
     * Stops the background music playback.
     * This resets the playback position to the beginning.
     */
    public void stopBGM() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
        }
    }

 
}
