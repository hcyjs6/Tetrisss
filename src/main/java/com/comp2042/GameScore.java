package com.comp2042;

import javafx.beans.property.IntegerProperty; // Import for JavaFX integer property
import javafx.beans.property.SimpleIntegerProperty;  // Import for creating integer properties

/**
 * The GameScore class manages the player's score for the game.
 * This is a simple data container that works with JavaFX properties for UI binding.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public final class GameScore {

    private final IntegerProperty score = new SimpleIntegerProperty(0); // Create a new integer property with initial value 0

    /**
     * Gets the score property for binding to UI components.
     * This allows the UI to automatically update when the score changes.
     * 
     * @return the score property that can be bound to UI elements
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
     * Resets the score to zero.
     */
    public void resetScore() {
        score.setValue(0);
    }
    
    /**
     * Gets the current score value.
     * 
     * @return the current score value
     */
    public int getCurrentScore() {
        return score.getValue();
    }
}