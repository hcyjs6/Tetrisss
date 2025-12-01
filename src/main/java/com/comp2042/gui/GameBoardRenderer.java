package com.comp2042.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Handles rendering of the background game board grid.
 * This class creates and manages the visual representation of the game board where bricks are placed.
 * 
 * @author Sek Joe Rin
 */
public final class GameBoardRenderer {

    private GameBoardRenderer() {
        
    }

    /**
     * Initializes the game board by creating a matrix of rectangles for each cell.
     * 
     * @param gamePanel the GridPane where the game board will be displayed
     * @param boardMatrix the 2D array representing the current state of the game board
     * @param brickSize the size in pixels for each board cell
     * @return a 2D array of Rectangle objects representing the board cells
     */
    public static Rectangle[][] initGameBoard(GridPane gamePanel, int[][] boardMatrix, int brickSize) {
        Rectangle[][] displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];

        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {

                Rectangle cell = new Rectangle(brickSize, brickSize);
                displayMatrix[i][j] = cell;
                gamePanel.add(cell, j, i);
                drawGameBoardCell(boardMatrix[i][j], cell);
            }
        }

        return displayMatrix;
    }

    /**
     * Draws a single game board cell with the specified color.
     * 
     * @param color the numeric value representing the cell color (0 for empty, 1-7 for brick colors)
     * @param cell the Rectangle to draw
     */
    public static void drawGameBoardCell(int color, Rectangle cell) {
        
        if (color == 0) {
            cell.setFill(Color.BLACK); // Empty cells: black background
            cell.setOpacity(1);
            cell.setStroke(Color.GREY); // Grey grid border
            cell.setStrokeWidth(0.2);
            cell.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        } else {
            cell.setFill(BrickColour.getFillColor(color));
            cell.setOpacity(1.0);
            cell.setStroke(Color.BLACK);
            cell.setStrokeWidth(0.8);
            cell.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        }
    }
}

