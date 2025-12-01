package com.comp2042.gui;
import javafx.scene.layout.GridPane;

/**
 * Handles rendering of the hold brick panel.
 * This class displays the Tetris piece that is currently being held by the player.
 * 
 * @author Sek Joe Rin
 */
public class HoldBrickRenderer {
    
    /**
     * Initializes the hold brick panel by displaying the held brick.
     * 
     * @param holdBrickPanel the GridPane where the hold brick will be displayed
     * @param holdBrickData the 2D array representing the hold brick shape and color values
     */
    public static void initHoldBrick(GridPane holdBrickPanel, int[][] holdBrickData) {
        NextBrickRenderer.initNextBrick(holdBrickPanel, holdBrickData);
    }
}

