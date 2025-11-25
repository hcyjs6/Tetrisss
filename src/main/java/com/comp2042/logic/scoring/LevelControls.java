// Basic level management system
package com.comp2042.logic.scoring;

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
    
    private static final int LINES_PER_LEVEL = 5;
    
    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    private int levelCustomized = 1;
    
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
     * Level increases by 1 for every 5 lines cleared, starting from the initial level.
     * 
     * @param totalLinesCleared the total number of lines cleared in the game
     */
    public void updateLevel(int totalLinesCleared) {
        int newLevel = levelCustomized + (totalLinesCleared / LINES_PER_LEVEL);
        if (newLevel > currentLevel.getValue()) {
            currentLevel.setValue(newLevel);
        }
    }
    
    /**
     * Resets the level manager to initial state.
     */
    public void resetLevel() {
        levelCustomized = 1;
        currentLevel.setValue(1);
    }
    
    /**
     * Sets the initial level for the game.
     * 
     * @param selectedLevel the selected level to set (should be between 1 and 100)
     */
    public void setLevelValue(int selectedLevel) {
        if (selectedLevel >= 1 && selectedLevel <= 100) {
            levelCustomized = selectedLevel;
            currentLevel.setValue(selectedLevel);
        }
    }
}
