package com.comp2042.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * Handles rendering of the current active brick within the game panel.
 */
public final class BrickRenderer {

    private BrickRenderer() {
        
    }

    public static Rectangle[][] initCurrentBrick(GridPane brickPanel, int[][] brickData, int brickSize) {
        Rectangle[][] currentBrickMatrix = new Rectangle[brickData.length][brickData[0].length];

        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {

                Rectangle cell = new Rectangle(brickSize, brickSize);
                drawBrickCell(cell, brickData[i][j]);
                currentBrickMatrix[i][j] = cell;
                brickPanel.add(cell, j, i);
            }
        }
        return currentBrickMatrix;
    }

    public static void drawBrickCell(Rectangle cell, int colorValue) {

        cell.setFill(BrickColour.getFillColor(colorValue));
        
        if (colorValue != 0) {
            cell.setOpacity(1.0);
            cell.setStroke(Color.BLACK);
            cell.setStrokeWidth(0.8);
            cell.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        } else {
            cell.setStroke(null);
        }
    }
}

