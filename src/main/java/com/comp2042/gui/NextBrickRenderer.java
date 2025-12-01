package com.comp2042.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Handles rendering of the next brick preview panel.
 * This class displays the upcoming Tetris piece that will appear next in the game.
 * 
 * @author Sek Joe Rin
 */
public final class NextBrickRenderer {

    private static final int NEXT_BRICK_SIZE = 28;

    private NextBrickRenderer() {
    }

    /**
     * Initializes the next brick preview by clearing the panel and drawing the next brick shape.
     * 
     * @param nextBrickPanel the GridPane where the next brick will be displayed
     * @param nextBrickData the 2D array representing the next brick shape and color values
     */
    public static void initNextBrick(GridPane nextBrickPanel, int[][] nextBrickData) {
        nextBrickPanel.getChildren().clear();

        if (nextBrickData == null || nextBrickData.length == 0 || nextBrickData[0].length == 0) {
            return;
        }

        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                int colorValue = nextBrickData[i][j];
                
                // Only create and style rectangles for non-empty cells (color != 0)
                if (colorValue != 0) {
                    Rectangle rectangle = new Rectangle(NEXT_BRICK_SIZE, NEXT_BRICK_SIZE);
                    rectangle.setFill(BrickColour.getFillColor(colorValue));
                    rectangle.setOpacity(1.0);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setStrokeWidth(1.0);
                    rectangle.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
                    nextBrickPanel.add(rectangle, j, i);
                }
               
            }
        }
    }    
}

