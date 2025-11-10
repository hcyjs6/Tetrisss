package com.comp2042.scoring;

/**
 * Handles scoring calculations for line clearing in Tetris.
 * This class is responsible only for calculating points based on lines cleared.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class LineClearScoring {
    
    private static final int points_per_lineCleared = 50;
    
    /**
     * Calculates points for clearing lines with level multiplier.
     * 
     * @param linesCleared the number of lines cleared
     * @param level the current level (acts as multiplier)
     * @return the total points for this line clear
     */
    public int calculatePoints(int linesCleared, int levelMultiplier) {
        if (linesCleared <= 0) {
            return 0;
        }
        
        int basePoints = calculateLineClearedPoints(linesCleared);
        return basePoints * levelMultiplier;
    }
    
    /**
     * Calculates base points for line clearing without level multiplier.
     * 
     * @param linesCleared the number of lines cleared
     * @return the base points for this line clear
     */
    private int calculateLineClearedPoints(int linesCleared) {
        switch (linesCleared) {
            case 1:
                return points_per_lineCleared;           // 50 points
            case 2:
                return points_per_lineCleared * 3;       // 150 points
            case 3:
                return points_per_lineCleared * 5;       // 250 points
            case 4:
                return points_per_lineCleared * 8;       // 400 points 
            case 5:
                return points_per_lineCleared * 12;      // 600 points 
            case 6:
                return points_per_lineCleared * 16;      // 800 points 
            default:
                return points_per_lineCleared * (8 + (linesCleared - 4) * 4);
        }
    }
}