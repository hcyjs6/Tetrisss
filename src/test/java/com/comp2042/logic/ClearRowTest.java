package com.comp2042.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClearRowTest {

    private ClearRow clearRow;
    private int[][] matrix;

    @BeforeEach
    void setUp() {
        matrix = new int[][]{{1, 2}, 
                             {3, 4}, 
                             {5, 6}};
                             
        clearRow = new ClearRow(2, matrix, 100, 1, 50, Arrays.asList(0, 1), false);
    }

    @Test
    void getLinesRemoved() {
        assertEquals(2, clearRow.getLinesRemoved());
    }

    @Test
    void getNewMatrix() {
        int[][] result = clearRow.getNewMatrix();
        
        assertArrayEquals(matrix[0], result[0]);
        assertArrayEquals(matrix[1], result[1]);
        assertArrayEquals(matrix[2], result[2]);
        assertNotSame(matrix, result);
    }

    @Test
    void getTotalPointsAwarded() {
        assertEquals(100, clearRow.getTotalPointsAwarded());
    }

    @Test
    void getCombo() {
        assertEquals(1, clearRow.getCombo());
    }

    @Test
    void getTotalComboBonus() {
        assertEquals(50, clearRow.getTotalComboBonus());
    }

    @Test
    void getClearedRowIndex() {
        List<Integer> result = clearRow.getClearedRowIndex();
        assertEquals(2, result.size());
        assertTrue(result.contains(0));
        assertTrue(result.contains(1));
    }

    @Test
    void isLevelUp() {
        assertFalse(clearRow.isLevelUp());

        ClearRow clearRowLevelUp = new ClearRow(2, new int[][]{{0, 0}}, 0, 0, 0, Arrays.asList(0, 1), true);
        assertTrue(clearRowLevelUp.isLevelUp());
    }
}