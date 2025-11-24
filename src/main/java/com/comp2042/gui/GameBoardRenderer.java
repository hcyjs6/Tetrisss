package com.comp2042.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Utility class responsible for rendering the background game board grid.
 */
public final class GameBoardRenderer {

    private GameBoardRenderer() {
        
    }

    
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

