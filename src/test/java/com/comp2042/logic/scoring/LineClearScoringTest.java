package com.comp2042.logic.scoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineClearScoringTest {

    private LineClearScoring lineClearScoring;

    @BeforeEach
    void setUp() {
        lineClearScoring = new LineClearScoring();
    }

    @Test
    void calculatePoints() {
        assertEquals(0, lineClearScoring.calculatePoints(0, 1));
        assertEquals(40, lineClearScoring.calculatePoints(1, 1));
        assertEquals(120, lineClearScoring.calculatePoints(2, 1));
        assertEquals(200, lineClearScoring.calculatePoints(3, 1));
        assertEquals(320, lineClearScoring.calculatePoints(4, 1));
        assertEquals(80, lineClearScoring.calculatePoints(1, 2));
        assertEquals(240, lineClearScoring.calculatePoints(2, 2));

    }
}