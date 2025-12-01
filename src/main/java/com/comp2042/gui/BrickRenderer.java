package com.comp2042.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * Handles rendering of the current active brick within the game panel.
 * This class initializes and draws the falling Tetris piece on the game board.
 * 
 * @author Sek Joe Rin
 */
public final class BrickRenderer {

    private BrickRenderer() {
        
    }

    /**
     * Initializes the current falling brick by creating a matrix of rectangles.
     * 
     * @param brickPanel the GridPane where the brick will be displayed
     * @param brickData the 2D array representing the brick shape and color values
     * @param brickSize the size in pixels for each brick cell
     * @return a 2D array of Rectangle objects representing the brick cells
     */
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

    /**
     * Draws a single brick cell with the specified color value.
     * 
     * @param cell the Rectangle to be drawn
     * @param colorValue the numeric value representing the color (0 for transparent)
     */
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

