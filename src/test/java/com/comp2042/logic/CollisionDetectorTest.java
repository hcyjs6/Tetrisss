package com.comp2042.logic;

import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

    @Test
    void checkCollision() {

         // Create a 4x4 empty board
         int[][] board = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1},
        };
        
        // Simple 2x2 brick
        int[][] brick = {
            {1, 1},
            {1, 1}
        };
        
        // No collision 
        assertFalse(CollisionDetector.checkCollision(board, brick, 0, 0));
        // hits filled row
        assertTrue(CollisionDetector.checkCollision(board, brick, 0, 2));
        assertTrue(CollisionDetector.checkCollision(board, brick, 0, 3));
        // out of bounds
        assertTrue(CollisionDetector.checkCollision(board, brick, -1, 0));
        assertTrue(CollisionDetector.checkCollision(board, brick, 3, 0));
    }

    @Test
    void canMoveBy() {
        // Create a 4x4 empty board
        int[][] board = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1},
        };
        
        // Simple 2x2 brick
        int[][] brick = {
            {1, 1},
            {1, 1}
        };
        
        // No collision 
        assertFalse(CollisionDetector.canMoveBy(board, brick, new Point(0, 0), 0, 0));
        // hits filled row
        assertTrue(CollisionDetector.canMoveBy(board, brick, new Point(0, 0), 0, 2));
        assertTrue(CollisionDetector.canMoveBy(board, brick, new Point(0, 0), 0, 3));
        // out of bounds
        assertTrue(CollisionDetector.canMoveBy(board, brick, new Point(0, 0), -1, 0));
        assertTrue(CollisionDetector.canMoveBy(board, brick, new Point(0, 0), 3, 0));

    }

    @Test
    void checkRotateCollision() {
        // Create a 4x4 empty board
        int[][] board = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 1, 1},
        };
        
        // Simple 2x2 brick
        int[][] rotatedBrick = {
            {1, 1},
            {1, 0}
        };
        
        // No collision 
        assertFalse(CollisionDetector.checkRotateCollision(board, rotatedBrick, new Point(0, 0)));
        // hits filled row
        assertTrue(CollisionDetector.checkRotateCollision(board, rotatedBrick, new Point(0, 2)));
        assertTrue(CollisionDetector.checkRotateCollision(board, rotatedBrick, new Point(0, 3)));
        // out of bounds
        assertTrue(CollisionDetector.checkRotateCollision(board, rotatedBrick, new Point(-1, 0)));
        assertTrue(CollisionDetector.checkRotateCollision(board, rotatedBrick, new Point(3, 0)));
    }

    @Test
    void checkSpawnCollision() {
        // Create a 4x4 empty board
        int[][] board = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1},
        };
        
        // Simple 2x2 brick
        int[][] brick = {
            {1, 1},
            {1, 1}
        };
        
        // No collision 
        assertFalse(CollisionDetector.checkSpawnCollision(board, brick, 0, 0));
        // hits filled row
        assertTrue(CollisionDetector.checkSpawnCollision(board, brick, 0, 2));
        assertTrue(CollisionDetector.checkSpawnCollision(board, brick, 0, 3));
        // out of bounds
        assertTrue(CollisionDetector.checkSpawnCollision(board, brick, -1, 0));
        assertTrue(CollisionDetector.checkSpawnCollision(board, brick, 3, 0));
    }
}