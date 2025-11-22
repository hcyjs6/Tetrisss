package com.comp2042.logic.ghostpieces;

import com.comp2042.logic.ViewData;
import com.comp2042.logic.CollisionDetector;

import java.awt.Point;

/**
 * Ghost piece logic for position calculations.
 */
public final class GhostPieceLogic {

    private GhostPieceLogic() {
        // Utility class
    }

    public static int calculateGhostPieceY(int[][] boardMatrix, int[][] currentShape, Point currentOffset) {
        int ghostPieceY = (int) currentOffset.getY();
        int ghostPieceX = (int) currentOffset.getX();

        // Check if the ghost piece can move down
        while (!CollisionDetector.canMoveBy(boardMatrix, currentShape, new Point(ghostPieceX, ghostPieceY), 0, 1)) {
            ghostPieceY++;
        }
        return ghostPieceY;
    }

    public static ViewData createGhostPieceViewData(int[][] currentShape, Point currentOffset, int ghostPieceY) {
        return new ViewData(currentShape, (int) currentOffset.getX(), ghostPieceY);
    }
}

