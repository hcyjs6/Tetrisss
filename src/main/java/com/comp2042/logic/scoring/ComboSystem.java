package com.comp2042.logic.scoring;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ComboSystem {

    private final IntegerProperty baseComboBonus = new SimpleIntegerProperty(50);
    private final IntegerProperty comboMultiplier = new SimpleIntegerProperty(0);

    public IntegerProperty baseComboBonusProperty() {
        return baseComboBonus;
    }

    public IntegerProperty comboMultiplierProperty() {
        return comboMultiplier;
    }

    public int getBaseComboBonus() {
        return baseComboBonus.getValue();
    }

    public int getComboMultiplier() {
        return comboMultiplier.getValue();
    }
    
    public void incrementCombo() {
        comboMultiplier.setValue(comboMultiplier.getValue() + 1);
    }

    public void resetCombo() {
        comboMultiplier.setValue(0);
    }

    
  
}
