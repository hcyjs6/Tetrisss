package com.comp2042.logic.scoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelControlsTest {

    private LevelControls levelControls;

    @BeforeEach
    void setUp() {
        levelControls = new LevelControls();
    }

    @Test
    void levelProperty() {
        assertEquals(1, levelControls.levelProperty().getValue());
    }

    @Test
    void getCurrentLevel() {
        assertEquals(1, levelControls.getCurrentLevel());
    }

    @Test
    void updateLevel() {
        assertEquals(1, levelControls.getCurrentLevel());

        assertTrue(levelControls.updateLevel(10));
        assertEquals(2, levelControls.getCurrentLevel());
  
        assertFalse(levelControls.updateLevel(10));
        assertEquals(2, levelControls.getCurrentLevel());

        assertTrue(levelControls.updateLevel(20));
        assertEquals(3, levelControls.getCurrentLevel());
    }

    @Test
    void setLevelValue() {
        assertEquals(1, levelControls.getCurrentLevel());
        levelControls.setLevelValue(5);
        assertEquals(5, levelControls.getCurrentLevel());
    }

    @Test
    void resetLevel() {
        assertEquals(1, levelControls.getCurrentLevel());
        levelControls.setLevelValue(10);
        assertEquals(10, levelControls.getCurrentLevel());
        levelControls.resetLevel();
        assertEquals(1, levelControls.getCurrentLevel());
    }
}