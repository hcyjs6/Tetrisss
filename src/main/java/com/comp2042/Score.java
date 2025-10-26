package com.comp2042;

import javafx.beans.property.IntegerProperty; // Import for JavaFX integer property
import javafx.beans.property.SimpleIntegerProperty;  // Import for creating integer properties

// This class tracks the player's score.
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0); // Create a new integer property with initial value 0

    public IntegerProperty scoreProperty() { // Method to get the score property
        return score; // Return the score property
    } 
    public void add(int i){ // Method to add points to score
        score.setValue(score.getValue() + i); // Add the given value to current score and update the score property
    }

    public void reset() { // Method to reset score to zero
        score.setValue(0); // Set the score to zero
    }
}

