package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Tracks the total number of lines cleared in the game.
 * This class is responsible only for counting and tracking the number of lines cleared.
 * 
 * @author Sek Joe Rin
 */
public class LineTracker {
    
    private final IntegerProperty totalLinesCleared = new SimpleIntegerProperty(0);
    
    /**
     * Returns the lines cleared property for UI binding.
     * 
     * @return the lines cleared property.
     */
    public IntegerProperty linesClearedProperty() {
        return totalLinesCleared;
    }
    
    /**
     * Returns the current total number of lines cleared value.
     * 
     * @return the current total number of lines cleared.
     */
    public int getTotalLinesCleared() {
        return totalLinesCleared.getValue();
    }
    
    /**
     * Adds the number of lines cleared to the total count.
     * 
     * @param linesCleared the number of lines to be added
     */
    public void addLinesCleared(int linesCleared) {
        if (linesCleared > 0) {
            totalLinesCleared.setValue(totalLinesCleared.getValue() + linesCleared);
        }
    }
    
    /**
     * Resets the total number of lines cleared to zero.
     */
    public void resetLineTracker() {
        totalLinesCleared.setValue(0);
    }
}