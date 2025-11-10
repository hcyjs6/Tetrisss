package com.comp2042.scoring;

/**
 * Handles scoring calculations for piece movements in Tetris.
 * This class is responsible only for calculating points for piece movements.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class MoveDownScoring {

    /**
     * Calculates points for a piece movement with level multiplier.
     * 
     * @param level the current level (acts as multiplier)
     * @return the points for this movement
     */
    public int calculateSoftDropPoints(int levelMultiplier, int pointsPerMove) {
        return pointsPerMove * levelMultiplier;
    }

    /**
     * Calculates points for a hard drop with level multiplier and drop distance.
     * 
     * @param levelMultiplier the current level (acts as multiplier)
     * @param dropDistance the number of cells the piece dropped
     * @return the points for this movement
     */
    public int calculateHardDropPoints(int levelMultiplier, int dropDistance) {
        return  dropDistance * 2 * levelMultiplier ; // Double points for hard drop
    }
    
}