// Basic line cleared tracking system
package com.comp2042.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Tracks the total number of lines cleared in the game.
 * This class is responsible only for counting and tracking lines cleared.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class LineTracker {
    
    private final IntegerProperty totalLinesCleared = new SimpleIntegerProperty(0);
    
    /**
     * Gets the total lines cleared property for UI binding.
     * 
     * @return the lines cleared property
     */
    public IntegerProperty linesClearedProperty() {
        return totalLinesCleared;
    }
    
    /**
     * Gets the current total lines cleared value.
     * 
     * @return the total lines cleared
     */
    public int getTotalLinesCleared() {
        return totalLinesCleared.getValue();
    }
    
    /**
     * Adds lines to the total count.
     * 
     * @param lines the number of lines to add
     */
    public void addLinesCleared(int linesCleared) {
        if (linesCleared > 0) {
            totalLinesCleared.setValue(totalLinesCleared.getValue() + linesCleared);
        }
    }
    
    /**
     * Resets the line tracker to zero.
     */
    public void resetLineTracker() {
        totalLinesCleared.setValue(0);
    }
}