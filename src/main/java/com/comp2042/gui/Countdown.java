package com.comp2042.gui;

import com.comp2042.audio.SoundEffect;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


/**
 * This class manages the countdown animation displayed before gameplay starts.
 * 
 * @author Sek Joe Rin
 */
public class Countdown {

    private final StackPane overlay;
    private final Label countdownLabel;
    private final GridPane brickPanel;
    private final GridPane ghostPanel;
    private final GridPane nextBrickPanel;
    private final Runnable isCountDownEnd;
    private final SoundEffect countdownSFX;

    private Timeline countdownTimeline;

    /**
     * Creates a new Countdown instance.
     * 
     * @param overlay the StackPane overlay that contains the countdown
     * @param countdownLabel the Label that displays the countdown numbers
     * @param brickPanel the GridPane for the current brick
     * @param ghostPanel the GridPane for the ghost piece
     * @param nextBrickPanel the GridPane for the next brick preview
     * @param isCountDownEnd the Runnable callback to execute when countdown finishes
     */
    public Countdown(StackPane overlay, Label countdownLabel, GridPane brickPanel, GridPane ghostPanel, GridPane nextBrickPanel, Runnable isCountDownEnd) {
        this.overlay = overlay;
        this.countdownLabel = countdownLabel;
        this.brickPanel = brickPanel;
        this.ghostPanel = ghostPanel;
        this.nextBrickPanel = nextBrickPanel;
        this.isCountDownEnd = isCountDownEnd;
        this.countdownSFX = new SoundEffect("Audio/countdownSFX.wav");
    }

    /**
     * Starts the countdown sequence from 3 to GO!.
     * This method displays the countdown overlay and animates the countdown numbers.
     *
     * @param isResume indicates whether the countdown is resuming an existing game
     */
    public void start(boolean isResume) {
        overlay.setVisible(true);

        if (!isResume) {
            brickPanel.setVisible(false);
            ghostPanel.setVisible(false);
            nextBrickPanel.setVisible(false);
        }
        
        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }

        countdownSFX.playSFX();

        countdownTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, e -> countdownLabel.setText("3")),
            new KeyFrame(Duration.seconds(1), e -> countdownLabel.setText("2")),
            new KeyFrame(Duration.seconds(2), e -> countdownLabel.setText("1")),
            new KeyFrame(Duration.seconds(3), e -> countdownLabel.setText("GO!")),
            new KeyFrame(Duration.seconds(3.8), e -> finishCountdown())
        );

        countdownTimeline.playFromStart();
    }


    private void finishCountdown() {
        ghostPanel.setVisible(true);
        nextBrickPanel.setVisible(true);
        overlay.setVisible(false);
        brickPanel.setVisible(true);
        countdownLabel.setText("");

        isCountDownEnd.run();
    }
}

