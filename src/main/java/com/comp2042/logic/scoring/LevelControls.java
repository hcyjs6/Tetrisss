package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Handles the level progression of the game.
 * This class is responsible only for the level management based on the total lines cleared.
 * 
 * @author Sek Joe Rin
 */
public class LevelControls {
    
    private static final int LINES_PER_LEVEL = 10;
    
    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    private int levelCustomized = 1;
    
    /**
     * Returns the current level property for UI binding.
     * This allows the UI to automatically update when the level changes.
     * 
     * @return the current level property.
     */
    public IntegerProperty levelProperty() {
        return currentLevel;
    }
    
    /**
     * Returns the current level value.
     * 
     * @return the current level.
     */
    public int getCurrentLevel() {
        return currentLevel.getValue();
    }
    
    /**
     * Updates the level based on the total lines cleared.
     * Level increases by 1 for every 10 lines cleared, starting from the initial level.
     * 
     * @param totalLinesCleared the total number of lines cleared in the game
     * @return true if the level increased, false otherwise
     */
    public boolean updateLevel(int totalLinesCleared) {
        int newLevel = levelCustomized + (totalLinesCleared / LINES_PER_LEVEL);
        if (newLevel > currentLevel.getValue()) {
            currentLevel.setValue(newLevel);
            return true;
        }
        return false;
    }
    
    /**
     * Sets the initial level for the game.
     * 
     * @param selectedLevel the selected level to be set (between 1 and 100)
     */
    public void setLevelValue(int selectedLevel) {
        if (selectedLevel >= 1 && selectedLevel <= 100) {
            levelCustomized = selectedLevel;
            currentLevel.setValue(selectedLevel);
        }
    }
    
    /**
     * Resets the level to the initial state of 1.
     */
    public void resetLevel() {
        levelCustomized = 1;
        currentLevel.setValue(1);
    }
    
    
}
