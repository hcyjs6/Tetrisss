package com.comp2042.logic.scoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveDownScoringTest {

    private MoveDownScoring moveDownScoring;

    @BeforeEach
    void setUp() {
        moveDownScoring = new MoveDownScoring();
    }

    @Test
    void calculateSoftDropPoints() {
        assertEquals(1, moveDownScoring.calculateSoftDropPoints(1, 1));
        assertEquals(2, moveDownScoring.calculateSoftDropPoints(2, 1));
        assertEquals(3, moveDownScoring.calculateSoftDropPoints(3, 1));
       
    }

    @Test
    void calculateHardDropPoints() {
        assertEquals(2, moveDownScoring.calculateHardDropPoints(1, 1));
        assertEquals(4, moveDownScoring.calculateHardDropPoints(2, 1));
        assertEquals(6, moveDownScoring.calculateHardDropPoints(3, 1));
    }
}