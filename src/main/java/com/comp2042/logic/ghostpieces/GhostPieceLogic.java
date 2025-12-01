package com.comp2042.logic.ghostpieces;

import com.comp2042.logic.ViewData;
import com.comp2042.logic.CollisionDetector;

import java.awt.Point;

/**
 * Handles ghost piece logic for position calculations.
 * This class calculates where the current piece will land on the board,
 * displaying a preview of the landing position.
 * 
 * @author Sek Joe Rin
 */
public final class GhostPieceLogic {

    private GhostPieceLogic() {
        // Utility class
    }

    /**
     * Calculates the y coordinate where the ghost piece will land on the board.
     * 
     * @param boardMatrix the current state of the game board
     * @param currentShape the 2D array representing the current brick shape
     * @param currentOffset the current position of the brick on the board
     * @return the y coordinate where the ghost piece will land
     */
    public static int calculateGhostPieceY(int[][] boardMatrix, int[][] currentShape, Point currentOffset) {
        int ghostPieceY = (int) currentOffset.getY();
        int ghostPieceX = (int) currentOffset.getX();

        // Check if the ghost piece can move down
        while (!CollisionDetector.canMoveBy(boardMatrix, currentShape, new Point(ghostPieceX, ghostPieceY), 0, 1)) {
            ghostPieceY++;
        }
        return ghostPieceY;
    }

    /**
     * Creates ViewData for the ghost piece preview.
     * 
     * @param currentShape the 2D array representing the current brick shape
     * @param currentOffset the current position of the brick on the board
     * @param ghostPieceY the y coordinate where the ghost piece will land
     * @return ViewData containing the ghost piece information for rendering
     */
    public static ViewData createGhostPieceViewData(int[][] currentShape, Point currentOffset, int ghostPieceY) {
        // Create ghost piece ViewData with shape in ghostPieceData field, not brickData
        return new ViewData(null, (int) currentOffset.getX(), ghostPieceY, null, null, currentShape);
    }
}

