package com.comp2042.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Holds ghost piece rendering utilities.
 */
public final class GhostPieceRenderer {

    private GhostPieceRenderer() {
       
    }

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

    public static void drawGhostCell(Rectangle cell, int colorValue) {
        cell.setFill(Color.TRANSPARENT);
        //cell.setFill(BrickColour.getFillColor(colorValue));
        cell.setStroke(BrickColour.getFillColor(colorValue));
        cell.setStrokeWidth(3);
        cell.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        cell.setOpacity(0.0);
        

        if (colorValue != 0) {
            //cell.setFill(BrickColour.getFillColor(colorValue));
            cell.setOpacity(0.5);
        }


    }
}


