package com.comp2042.ghostpieces;

import com.comp2042.ViewData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Holds ghost piece rendering utilities.
 */
public final class GhostPieceRenderer {

    private GhostPieceRenderer() {
        // Utility class
    }

    // Initialize the ghost piece
    public static Rectangle[][] initGhostPiece(GridPane ghostPanel, int brickSize) {
        ghostPanel.getChildren().clear();
        Rectangle[][] ghostRectangles = new Rectangle[4][4];
        for (int i = 0; i < ghostRectangles.length; i++) {
            for (int j = 0; j < ghostRectangles[i].length; j++) {
                Rectangle ghostGrid = new Rectangle(brickSize, brickSize);
                ghostGrid.setFill(Color.TRANSPARENT);
                ghostGrid.setStroke(Color.WHITE);
                ghostGrid.setStrokeWidth(3.5);
                ghostGrid.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
                ghostRectangles[i][j] = ghostGrid;
                ghostPanel.add(ghostGrid, j, i);
            }
        }
        return ghostRectangles;
    }

    // Refresh the ghost piece
    public static void refreshGhostPiece(ViewData ghostData, ViewData currentPieceData, Rectangle[][] ghostRectangles, GridPane ghostPanel, BorderPane gameBoard, int brickSize) {
        if (ghostRectangles == null || ghostData == null || currentPieceData == null) {
            return;
        }

        for (Rectangle[] row : ghostRectangles) {
            for (Rectangle rect : row) {
                rect.setOpacity(0.0);
            }
        }

        if (ghostData.getyPosition() > currentPieceData.getyPosition()) {
            ghostPanel.setLayoutX(gameBoard.getLayoutX() + 10 + ghostData.getxPosition() * brickSize);
            ghostPanel.setLayoutY(gameBoard.getLayoutY() + 10 + ghostData.getyPosition() * brickSize);

            int[][] shape = ghostData.getBrickData();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] != 0) {
                        ghostRectangles[i][j].setOpacity(0.35);
                    }
                }
            }
        }
    }
}

