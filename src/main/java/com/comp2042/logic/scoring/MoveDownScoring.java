package com.comp2042.logic.scoring;

/**
 * Handles scoring calculations for piece moving down by user input in Tetris.
 * This class is responsible only for calculating points for piece moving down by user input.
 * 
 * @author Sek Joe Rin
 */
public class MoveDownScoring {

    /**
     * Calculates points for a soft drop movement with level multiplier.
     * 
     * @param levelMultiplier the current level acts as the multiplier
     * @param pointsPerMove the points for each move
     * @return the points for this soft drop movement
     */
    public int calculateSoftDropPoints(int levelMultiplier, int pointsPerMove) {
        return pointsPerMove * levelMultiplier;
    }

    /**
     * Calculates points for a hard drop movement with level multiplier and drop distance.
     * 
     * @param levelMultiplier the current level acts as the multiplier
     * @param dropDistance the number of cells the piece dropped
     * @return the points for this hard drop movement
     */
    public int calculateHardDropPoints(int levelMultiplier, int dropDistance) {
        return  dropDistance * 2 * levelMultiplier ; // Double points for hard drop
    }
    
}