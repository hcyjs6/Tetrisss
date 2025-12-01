package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty; 
import javafx.beans.property.SimpleIntegerProperty;  

/**
 * Handles the player's score of the game. 
 * This class works with JavaFX properties for UI binding.
 * 
 * @author Sek Joe Rin
 */
public final class GameScore {

    private final IntegerProperty score = new SimpleIntegerProperty(0); // Create a new integer property with initial value 0

    /**
     * Returns the score property for UI binding.
     * This allows the UI to automatically update when the score changes.
     * 
     * @return the score property.
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds points to the current score.
     * 
     * @param points the number of points to be added to the current score
     */
    public void addPoints(int points) {
        score.setValue(score.getValue() + points);
    }

    /**
     * Gets the current score value.
     * 
     * @return the current score value
     */
    public int getCurrentScore() {
        return score.getValue();
    }

    /**
     * Resets the score to zero.
     */
    public void resetScore() {
        score.setValue(0);
    }
    
    
}