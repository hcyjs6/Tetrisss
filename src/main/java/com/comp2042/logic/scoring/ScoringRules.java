package com.comp2042.logic.scoring;

import com.comp2042.logic.event.EventType;
import javafx.beans.property.IntegerProperty;

/**
 * Coordinates all scoring logic of the Tetris game.
 * This class acts as the central point for all scoring logic.
 * 
 * @author Sek Joe Rin
 */
public class ScoringRules {
    
    private final GameScore score;
    private final LineClearScoring lineClearScoring;
    private final LevelControls levelControls;
    private final MoveDownScoring moveDownScoring;
    private final LineTracker lineTracker;
    private final ComboSystem comboSystem;
    private boolean isLevelUp = false;

    /**
     * Initializes the scoring rules with the necessary components.
     * @param score the score component
     */
    public ScoringRules(GameScore score) {
        this.score = score;
        this.lineClearScoring = new LineClearScoring();
        this.levelControls = new LevelControls();
        this.moveDownScoring = new MoveDownScoring();
        this.lineTracker = new LineTracker();
        this.comboSystem = new ComboSystem();
    }

    /**
     * Returns the level property for UI binding.
     * @return the level property.
     */
    public IntegerProperty levelProperty() {
        return levelControls.levelProperty();
    }

    /**
     * Returns the lines cleared property for UI binding.
     * @return the lines cleared property.
     */
    public IntegerProperty linesClearedProperty() {
        return lineTracker.linesClearedProperty();
    }

    /**
     * Gets the total number of lines cleared.
     * @return the current total number of lines cleared.
     */
    public int getTotalLinesCleared() {
        return lineTracker.getTotalLinesCleared();
    }

    /**
     * Gets the current level value.
     * @return the current level value.
     */
    public int getCurrentLevel() {
        return levelControls.getCurrentLevel();
    }

    /**
     * Gets the current score value.
     * @return the current score value.
     */
    public int getCurrentScore() {
        return score.getCurrentScore();
    }
    
    /**
     * Adds points for a successful move down by user input with level bonus.
     * @param eventType the type of move down event (SOFT_DROP or HARD_DROP)
     * @param dropDistance the distance the piece moved down
     */
    public void add_MoveDown_Points(EventType eventType, int dropDistance) {
        int levelMultiplier = levelControls.getCurrentLevel();
        int pointsAwarded = 0;

        if(eventType == EventType.SOFT_DROP) {
            pointsAwarded = moveDownScoring.calculateSoftDropPoints(levelMultiplier, dropDistance);
       
        } else if(eventType == EventType.HARD_DROP) {
            pointsAwarded = moveDownScoring.calculateHardDropPoints(levelMultiplier, dropDistance);
        }
        score.addPoints(pointsAwarded);
    }

    /**
     * Adds points for clearing lines with level multiplier and combo bonus.
     * @param linesCleared the number of lines cleared
     * @return the total points awarded for this line clear with level multiplier and combo bonus.
     */
    public int add_LineCleared_Points(int linesCleared) {
        if (linesCleared <= 0) {
            return 0;
        }
        
        int levelMultiplier = levelControls.getCurrentLevel();
        int totalComboBonus = getTotalComboBonus(); // total combo bonus
        int totalPointsAwarded = lineClearScoring.calculatePoints(linesCleared, levelMultiplier) + totalComboBonus; // total points with combo bonus
        score.addPoints(totalPointsAwarded);
        
        // Update line tracking
        lineTracker.addLinesCleared(linesCleared);

        // Update level progression and track if level increased
        int totalLinesCleared = lineTracker.getTotalLinesCleared();
        isLevelUp = levelControls.updateLevel(totalLinesCleared);
        
        comboSystem.incrementCombo();

        return totalPointsAwarded;
    }

    /**
     * Checks if the level increased during the last line clear.
     * @return true if the level increased, false otherwise
     */
    public boolean isLevelUp() {
        return isLevelUp;
    }

    /**
     * Gets the current combo multiplier value.
     * @return the current combo multiplier value.
     */
    public int getComboMultiplier() {
        return comboSystem.getComboMultiplier();
    }

    /**
     * Gets the total combo bonus value.
     * @return the total combo bonus value.
     */
    public int getTotalComboBonus() {
        return comboSystem.getBaseComboBonus() * comboSystem.getComboMultiplier();
    }
    
    /**
     * Resets the combo system.
     */
    public void resetCombo() {
        comboSystem.resetCombo();
    }
    
    /**
     * Resets the achievements for a new game.
     */
    public void resetAchievements() {
        score.resetScore();
        levelControls.resetLevel();
        lineTracker.resetLineTracker();
    }
    
    /**
     * Sets the initial level for the game.
     * @param selectedLevel the selected level to be set (between 1 and 100)
     */
    public void setLevelValue(int selectedLevel) {
        levelControls.setLevelValue(selectedLevel);
    }
    
}