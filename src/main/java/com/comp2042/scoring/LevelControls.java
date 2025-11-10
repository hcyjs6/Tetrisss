package com.comp2042.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages level progression in Tetris.
 * This class is responsible only for level management based on total lines cleared.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class LevelControls {
    
    private static final int LINES_PER_LEVEL = 10;
    private static final int MAX_LEVEL = 100;
    
    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    
    /**
     * Gets the current level property for UI binding.
     * 
     * @return the level property
     */
    public IntegerProperty levelProperty() {
        return currentLevel;
    }
    
    /**
     * Gets the current level value.
     * 
     * @return the current level
     */
    public int getCurrentLevel() {
        return currentLevel.getValue();
    }
    
    /**
     * Updates the level based on total lines cleared.
     * 
     * @param totalLinesCleared the total number of lines cleared in the game
     */
    public void updateLevel(int totalLinesCleared) {
        int newLevel = currentLevel.getValue() + (totalLinesCleared / (currentLevel.getValue() * LINES_PER_LEVEL));
        if (newLevel > currentLevel.getValue() && newLevel <= MAX_LEVEL) {
            currentLevel.setValue(newLevel);
        }
    }
    
    /**
     * Resets the level manager to initial state.
     */
    public void resetLevel() {
        currentLevel.setValue(1);
    }
}
