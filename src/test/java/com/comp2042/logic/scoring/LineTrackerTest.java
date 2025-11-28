package com.comp2042.logic.scoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTrackerTest {

    private LineTracker lineTracker;

    @BeforeEach
    void setUp() {
        lineTracker = new LineTracker();
    }

    @Test
    void linesClearedProperty() {
        assertEquals(0, lineTracker.linesClearedProperty().getValue());
    }

    @Test
    void getTotalLinesCleared() {
        assertEquals(0, lineTracker.getTotalLinesCleared());
    }

    @Test
    void addLinesCleared() {
        assertEquals(0, lineTracker.getTotalLinesCleared());
        lineTracker.addLinesCleared(1);
        assertEquals(1, lineTracker.getTotalLinesCleared());
        lineTracker.addLinesCleared(4);
        assertEquals(5, lineTracker.getTotalLinesCleared());
       
    }

    @Test
    void resetLineTracker() {
        assertEquals(0, lineTracker.getTotalLinesCleared());
        lineTracker.addLinesCleared(1);
        assertEquals(1, lineTracker.getTotalLinesCleared());
        lineTracker.addLinesCleared(4);
        assertEquals(5, lineTracker.getTotalLinesCleared());
        lineTracker.resetLineTracker();
        assertEquals(0, lineTracker.getTotalLinesCleared());
    }
}