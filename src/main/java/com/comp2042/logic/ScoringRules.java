package com.comp2042.logic;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import com.comp2042.GameScore;

/**
 * Manages the scoring rules for the Tetris game.
 * This class centralizes all scoring logic for better maintainability.
 */
public class ScoringRules {
    
    private final GameScore score;

  
    
    // Scoring constants
    private static final int points_per_move = 1;
    private static final int points_per_lineCleared = 50;
    private static final int levelUpLines = 10;
    private static final int MAX_LEVEL = 100;

    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1); // Create a new integer property with initial value 0
    private final IntegerProperty totalLinesCleared = new SimpleIntegerProperty(0); // Create a new integer property with initial value 0
    
    public ScoringRules(GameScore score) {
        this.score = score;
  
    }

    public IntegerProperty levelProperty() {
        return currentLevel;
    }

    public IntegerProperty linesClearedProperty() {
        return totalLinesCleared;
    }

    public int getTotalLinesCleared() {
        return totalLinesCleared.getValue();
    }

    public int getCurrentLevel() {
        return currentLevel.getValue();
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
        totalLinesCleared.setValue(totalLinesCleared.getValue() + linesCleared);
        
        
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
        return currentLevel.getValue();
    }
    
    /**
     * Updates the level based on lines cleared.
     */
    private void updateLevel() {
        int newLevel = currentLevel.getValue() + (totalLinesCleared.getValue() / levelUpLines); // update the current level based on the total lines cleared
        if (newLevel > currentLevel.getValue() && newLevel <= MAX_LEVEL) {
            currentLevel.setValue(newLevel);
            
        }
    }

    public void resetLinesCleared() {
        totalLinesCleared.setValue(0);
    }

    public void resetLevel() {
        currentLevel.setValue(1);
    }
    
    /**
     * Resets the scoring system for a new game.
     */
    public void reset() {
        score.resetScore();
        resetLinesCleared();
        resetLevel();
       
    }
    
    
    /**
     * Gets the current score.
     * @return the current score
     */
    public int getCurrentScore() {
        return score.getCurrentScore();
    }
    
}