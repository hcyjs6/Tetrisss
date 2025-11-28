package com.comp2042.logic.ghostpieces;

import com.comp2042.logic.ViewData;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

class GhostPieceLogicTest {

    @Test
    void calculateGhostPieceY() {
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1}
        };

        int[][] shape = {
                {1, 1}
        };

        Point start = new Point(1, 0);

        int ghostY = GhostPieceLogic.calculateGhostPieceY(board, shape, start);

        assertEquals(2, ghostY);
    }

    @Test
    void createGhostPieceViewData() {
        int[][] shape = {
                {0, 1},
                {1, 1}
        };
        Point offset = new Point(5, 2);
        int ghostY = 10;

        ViewData viewData = GhostPieceLogic.createGhostPieceViewData(shape, offset, ghostY);

        assertEquals(5, viewData.getxPosition());
        assertEquals(ghostY, viewData.getyPosition());
        assertArrayEquals(shape, viewData.getGhostPieceData());
    }
}