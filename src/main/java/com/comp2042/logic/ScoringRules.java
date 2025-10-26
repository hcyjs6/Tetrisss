package com.comp2042.logic;

import com.comp2042.GameScore;

/**
 * Manages the scoring rules for the Tetris game.
 * This class centralizes all scoring logic for better maintainability.
 */
public class ScoringRules {
    
    private final GameScore score;
    private int totalLinesCleared;
    private int currentLevel;
    
    // Scoring constants
    private static final int points_per_move = 1;
    private static final int points_per_lineCleared = 50;
    private static final int levelUpLines = 10;
    private static final int MAX_LEVEL = 100;
    
    public ScoringRules(GameScore score) {
        this.score = score;
        this.totalLinesCleared = 0;
        this.currentLevel = 1;
    }
    
    /**
     * Adds points for a successful move down with level bonus.
     */
    public void add_MoveDown_Points() {
        int basePoints = points_per_move;
        int levelMultiplier = calculateLevelBonus();
        int totalPoints = basePoints * levelMultiplier;
        
        score.addPoints(totalPoints);
    }
    
    /**
     * Calculates and adds points for clearing lines.
     * @param linesCleared the number of lines cleared
     * @return the points awarded for this line clear
     */
    public int add_LineCleared_Points(int linesCleared) {
        // If no lines are cleared, return 0
        if (linesCleared <= 0) {
            return 0;
        }
        
        // Calculate points based on lines cleared and level
        int basePoints = calculateLineClearedPoints(linesCleared);
        int levelMultiplier = calculateLevelBonus();
        int totalPoints = basePoints * levelMultiplier;
        
        score.addPoints(totalPoints);
        totalLinesCleared += linesCleared;
        
        // Check for level up
        updateLevel();
        
        return totalPoints;
    }
    
    /**
     * Calculates base points for line clearing.
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
    
    /**
     * Gets the level multiplier for scoring.
     * @return the current level multiplier
     */
    private int calculateLevelBonus() {
        return currentLevel;
    }
    
    /**
     * Updates the level based on lines cleared.
     */
    private void updateLevel() {
        int newLevel = currentLevel + (totalLinesCleared / levelUpLines); // update the current level based on the total lines cleared
        if (newLevel > currentLevel && newLevel <= MAX_LEVEL) {
            currentLevel = newLevel;
        }
    }
    
    /**
     * Resets the scoring system for a new game.
     */
    public void reset() {
        score.resetScore();
        totalLinesCleared = 0;
        currentLevel = 1;
    }
    
    /**
     * Gets the current level.
     * @return the current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Gets the total lines cleared.
     * @return the total lines cleared
     */
    public int getTotalLinesCleared() {
        return totalLinesCleared;
    }
    
    /**
     * Gets the current score.
     * @return the current score
     */
    public int getCurrentScore() {
        return score.getCurrentScore();
    }
    
}