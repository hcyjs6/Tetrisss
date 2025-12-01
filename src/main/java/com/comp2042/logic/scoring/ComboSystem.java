package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages the combo system for scoring in Tetris.
 * This class tracks consecutive line clears and calculates combo bonus points.
 * 
 * @author Sek Joe Rin
 */
public class ComboSystem {

    private final IntegerProperty baseComboBonus = new SimpleIntegerProperty(50);
    private final IntegerProperty comboMultiplier = new SimpleIntegerProperty(0);

    /**
     * Returns the base combo bonus property for UI binding.
     * 
     * @return the base combo bonus property.
     */
    public IntegerProperty baseComboBonusProperty() {
        return baseComboBonus;
    }

    /**
     * Returns the combo multiplier property for UI binding.
     * 
     * @return the combo multiplier property.
     */
    public IntegerProperty comboMultiplierProperty() {
        return comboMultiplier;
    }

    /**
     * Gets the base combo bonus value.
     * 
     * @return the base combo bonus value
     */
    public int getBaseComboBonus() {
        return baseComboBonus.getValue();
    }

    /**
     * Gets the current combo multiplier value.
     * 
     * @return the current combo multiplier value
     */
    public int getComboMultiplier() {
        return comboMultiplier.getValue();
    }
    
    /**
     * Increments the combo multiplier by one.
     * This is called when consecutive lines are cleared.
     */
    public void incrementCombo() {
        comboMultiplier.setValue(comboMultiplier.getValue() + 1);
    }

    /**
     * Resets the combo multiplier to zero.
     * This is called when no lines are cleared.
     */
    public void resetCombo() {
        comboMultiplier.setValue(0);
    }

    
  
}
