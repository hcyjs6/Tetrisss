package com.comp2042.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Handles rendering of the ghost piece preview.
 * This class displays a semi-transparent preview showing where the current piece will land.
 * 
 * @author Sek Joe Rin
 */
public final class GhostPieceRenderer {

    private GhostPieceRenderer() {
       
    }

    /**
     * Initializes the ghost piece by creating a matrix of rectangles.
     * 
     * @param ghostPanel the GridPane where the ghost piece will be displayed
     * @param ghostData the 2D array representing the ghost piece shape and color values
     * @param brickSize the size in pixels for each ghost piece cell
     * @return a 2D array of Rectangle objects representing the ghost piece cells
     */
    public static Rectangle[][] initGhostPiece(GridPane ghostPanel, int[][] ghostData, int brickSize) {
        ghostPanel.getChildren().clear();


        Rectangle[][] ghostMatrix = new Rectangle[ghostData.length][ghostData[0].length];

        for (int i = 0; i < ghostData.length; i++) {
            for (int j = 0; j < ghostData[i].length; j++) {
                
                Rectangle cell = new Rectangle(brickSize, brickSize);
                drawGhostCell(cell, ghostData[i][j]);
                ghostMatrix[i][j] = cell;
                ghostPanel.add(cell, j, i);
            }
        }
        return ghostMatrix;
    }

    /**
     * Draws a single ghost piece cell with semi-transparent appearance.
     * 
     * @param cell the Rectangle to draw
     * @param colorValue the numeric value representing the color of the ghost piece cell
     */
    public static void drawGhostCell(Rectangle cell, int colorValue) {
        cell.setFill(Color.TRANSPARENT);
        cell.setStroke(Color.GREY);
        cell.setStrokeWidth(3.4);
        cell.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        cell.setOpacity(0.0);
        

        if (colorValue != 0) {
            cell.setOpacity(0.6);
        }


    }
}


