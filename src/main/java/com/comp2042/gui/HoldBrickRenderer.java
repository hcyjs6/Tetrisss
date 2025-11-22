package com.comp2042.gui;
import javafx.scene.layout.GridPane;

public class HoldBrickRenderer {
    
    /**
     * Refreshes the hold brick panel display.
     * 
     * @param brick the view data containing hold brick information
     */
    public static void initHoldBrick(GridPane holdBrickPanel, int[][] holdBrickData) {
        NextBrickRenderer.initNextBrick(holdBrickPanel, holdBrickData);
    }
}

