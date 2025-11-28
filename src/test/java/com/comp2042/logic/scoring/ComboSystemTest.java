package com.comp2042.logic.scoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComboSystemTest {

    private ComboSystem comboSystem;

    @BeforeEach
    void setUp() {
        comboSystem = new ComboSystem();
    }

    @Test
    void baseComboBonusProperty() {
        assertEquals(50, comboSystem.baseComboBonusProperty().getValue());
    }

    @Test
    void comboMultiplierProperty() {
        assertEquals(0, comboSystem.comboMultiplierProperty().getValue());
    }

    @Test
    void getBaseComboBonus() {
        assertEquals(50, comboSystem.getBaseComboBonus());
    }

    @Test
    void getComboMultiplier() {
        assertEquals(0, comboSystem.getComboMultiplier());
    }

    @Test
    void incrementCombo() {
        assertEquals(0, comboSystem.getComboMultiplier());
        comboSystem.incrementCombo();
        assertEquals(1, comboSystem.getComboMultiplier());
        comboSystem.incrementCombo();
        assertEquals(2, comboSystem.getComboMultiplier());
    }

    @Test
    void resetCombo() {
        assertEquals(0, comboSystem.getComboMultiplier());
        comboSystem.incrementCombo();
        assertEquals(1, comboSystem.getComboMultiplier());
        comboSystem.resetCombo();
        assertEquals(0, comboSystem.getComboMultiplier());
    }
}