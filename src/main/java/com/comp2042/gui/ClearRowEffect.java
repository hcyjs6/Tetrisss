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


/**
 * Handles the visual effect that plays when rows are cleared.
 */
public class ClearRowEffect {

    private final Rectangle[][] displayMatrix;
    private final StackPane notificationLayer;
    private final GuiController guiController;

    public ClearRowEffect(Rectangle[][] displayMatrix, StackPane notificationLayer, GuiController guiController) { 
        this.displayMatrix = displayMatrix;
        this.notificationLayer = notificationLayer;
        this.guiController = guiController;
       
    }

    /**
     * Plays the fade effect for the cleared rows and triggers UI updates when complete.
     *
     * @param clearRow data describing the cleared rows
     * @param downData data describing the resulting board state
     */
    public void play(ClearRow clearRow, DownData downData) {
      
        List<Integer> clearedRowIndex = clearRow.getClearedRowIndex();
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

        parallelFade.setOnFinished(e -> {
            guiController.refreshGameBoard(clearRow.getNewMatrix());
            showNotification(clearRow);
            guiController.refreshBrick(downData.getViewData());
        });

        parallelFade.play();
    }

    private void showNotification(ClearRow clearRow) {
        int totalPoints = clearRow.getTotalPointsAwarded();
        int comboBonus = clearRow.getTotalComboBonus();
        int basePoints = totalPoints - comboBonus;

        String notificationText = " + " + basePoints + "points (+ " + comboBonus + " combo bonus)";
        NotificationPanel notificationPanel = new NotificationPanel(notificationText);
        StackPane.setAlignment(notificationPanel, Pos.CENTER);
        notificationLayer.getChildren().add(notificationPanel);
        notificationPanel.showScore(notificationLayer.getChildren());
    }
}

