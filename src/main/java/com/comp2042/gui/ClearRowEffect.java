package com.comp2042.gui;

import com.comp2042.logic.ClearRow;
import com.comp2042.logic.DownData;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import com.comp2042.audio.SoundEffect;


/**
 * Handles the visual effect that plays when rows are cleared.
 * This class manages the fade animation when rows are cleared and displays notifications
 * showing points earned, combos, and level-up messages.
 * 
 * @author Sek Joe Rin
 */
public class ClearRowEffect {

    private final Rectangle[][] displayMatrix;
    private final StackPane notificationLayer;
    private final GuiController guiController;
    private final SoundEffect clearRowSFX;
    private final SoundEffect notificationSFX;
    private final SoundEffect levelUpSFX;

    /**
     * Creates a new ClearRowEffect instance.
     * 
     * @param displayMatrix the 2D array of rectangles representing the game board cells
     * @param notificationLayer the StackPane where notifications will be displayed
     * @param guiController the GUI controller for refreshing the game board and brick
     */
    public ClearRowEffect(Rectangle[][] displayMatrix, StackPane notificationLayer, GuiController guiController) { 
        this.displayMatrix = displayMatrix;
        this.notificationLayer = notificationLayer;
        this.guiController = guiController;
        this.clearRowSFX = new SoundEffect("Audio/clearRowSFX.wav");
        this.notificationSFX = new SoundEffect("Audio/notificationSFX.mp3");
        this.levelUpSFX = new SoundEffect("Audio/levelUpSFX.wav");
       
    }

    /**
     * Plays the fade effect for the cleared rows and updates the game board and brick after the effect is complete.
     * This method animates the cleared rows and shows a notification with points and combo information.
     *
     * @param clearRow data describing the cleared rows
     * @param downData data describing the new view data after the cleared rows are merged to the background
     */
    public void play(ClearRow clearRow, DownData downData) {
        List<Integer> clearedRowIndex = clearRow.getClearedRowIndex();

        if (clearedRowIndex.isEmpty()) {
            guiController.refreshGameBoard(clearRow.getNewMatrix());
            guiController.refreshBrick(downData.getViewData());
            return;
        }

        List<FadeTransition> fadeTransitions = new ArrayList<>();

        for (Integer rowIndex : clearedRowIndex) {
            if (rowIndex >= 0 && rowIndex < displayMatrix.length) {
                for (int columnIndex = 0; columnIndex < displayMatrix[rowIndex].length; columnIndex++) {
                    Rectangle rectangle = displayMatrix[rowIndex][columnIndex];
                    FadeTransition fade = new FadeTransition(Duration.millis(100), rectangle);
                    fade.setFromValue(1.0);
                    fade.setToValue(0.7);
                    fade.setCycleCount(4);
                    fadeTransitions.add(fade);
                }
            }
        }


        ParallelTransition parallelFade = new ParallelTransition();
        parallelFade.getChildren().addAll(fadeTransitions);

        guiController.pauseAutoDrop(); // pause auto drop for effect

        parallelFade.setOnFinished(e -> {
            guiController.refreshGameBoard(clearRow.getNewMatrix());
            showNotification(clearRow);
            guiController.refreshBrick(downData.getViewData());
            guiController.resumeAutoDrop(); // resume auto drop after effect
        });

        parallelFade.play();
        clearRowSFX.playSFX(); // play clear row sound effect
    }

    /**
     * Shows a notification with points and combo information.
     * This method creates a notification panel and displays the points and combo information on the notification layer.
     * 
     * @param clearRow data describing the cleared rows and the points and combo information
     */
    private void showNotification(ClearRow clearRow) {

        String linesRemovedText = "";
        String comboText = "";
        String totalPointsText = "";
        String levelUpText = "";
        String text = "";
        String notificationText = "";

        int totalPoints = clearRow.getTotalPointsAwarded(); // total points awarded for the cleared rows
        int combo = clearRow.getCombo(); // combo multiplier
        int linesRemoved = clearRow.getLinesRemoved(); // number of rows removed
        boolean levelUp = clearRow.isLevelUp(); // whether level increased

        if (linesRemoved == 1) {
            linesRemovedText = "Single";

        } else if (linesRemoved == 2) {
            linesRemovedText = "Double";

        } else if (linesRemoved == 3) {
            linesRemovedText = "Triple";

        } else if (linesRemoved == 4) {
            linesRemovedText = "Tetris";

        }

        totalPointsText = " + " + totalPoints + " points";
        levelUpText = "LEVEL UP!";

        if (combo > 0) {
            comboText = combo + "x Combo";
            text = linesRemovedText + "\n" + comboText + "\n" + totalPointsText;
            
        } else if (combo == 0) {
            text = linesRemovedText + "\n" + totalPointsText;

        } else {
            return;
        }

        // Add level up text at the end if level increased
        if (levelUp) {
            levelUpSFX.playSFX();
            notificationText = levelUpText + "\n\n\n" + text;

        }else {
            notificationText = text;
        }

        NotificationPanel notificationPanel = new NotificationPanel(notificationText);
        StackPane.setAlignment(notificationPanel, Pos.CENTER);
        notificationLayer.getChildren().add(notificationPanel);
        notificationSFX.playSFX();
        notificationPanel.shownotification(notificationLayer.getChildren());
    }
}

