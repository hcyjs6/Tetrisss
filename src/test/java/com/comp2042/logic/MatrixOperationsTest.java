package com.comp2042.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void intersect() {

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
        assertFalse(MatrixOperations.intersect(board, brick, 0, 0));
        
        // hits filled row
        assertTrue(MatrixOperations.intersect(board, brick, 0, 2));
        assertTrue(MatrixOperations.intersect(board, brick, 0, 3));
        // out of bounds
        assertTrue(MatrixOperations.intersect(board, brick, -1, 0));
        assertTrue(MatrixOperations.intersect(board, brick, 3, 0));
            
    }

    @Test
    void copy() {
        int[][] original = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        int[][] copied = MatrixOperations.copy(original);
        
        // Check values are equal
        assertArrayEquals(original[0], copied[0]);
        assertArrayEquals(original[1], copied[1]);
        assertArrayEquals(original[2], copied[2]);
        
        // original remains unchanged after modifying copy
        copied[0][0] = 30;
        assertEquals(30, copied[0][0]);
        assertEquals(1, original[0][0]);
        assertNotEquals(original[0][0], copied[0][0]);
        assertNotSame(original, copied);
    }

    @Test
    void merge() {
        int[][] board = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        
        int[][] brick = {
            {1, 1},
            {1, 0}
        };
        
        int[][] filledFields = MatrixOperations.merge(board, brick, 0, 0);
        
        // Check brick was placed correctly
        assertEquals(1, filledFields[0][0]);
        assertEquals(1, filledFields[0][1]);
        assertEquals(1, filledFields[1][0]);
        assertEquals(0, filledFields[1][1]);  
        assertEquals(0, filledFields[2][3]);
    }

    @Test
    void checkRemoving() {
        int[][] board = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 1, 1}
        };

        ClearRow result = MatrixOperations.checkRemoving(board);

        assertEquals(1, result.getLinesRemoved());
        assertEquals(0, result.getNewMatrix()[1][2]);
       
    }
}