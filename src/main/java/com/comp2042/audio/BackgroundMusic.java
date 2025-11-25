package com.comp2042.audio;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Handles background music playback for the game.
 */
public class BackgroundMusic {
    
    private final String resourcePath;
    private MediaPlayer bgmPlayer;
    private double volume = 0.5;

    public BackgroundMusic(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    private void initBGM() {
        
       
            URL loadBGM = getClass().getClassLoader().getResource(resourcePath);
            Media bgm = new Media(loadBGM.toExternalForm());
            bgmPlayer = new MediaPlayer(bgm);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
            bgmPlayer.setVolume(volume);
        
    }

    public void playBGM() {

        if (bgmPlayer == null) {
            initBGM();
        }
        if (bgmPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            bgmPlayer.play();
        }
    }

    public void pauseBGM() {
        if (bgmPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            bgmPlayer.pause();
        }
    }

    public void stopBGM() {
        bgmPlayer.stop();
    }

 
}
