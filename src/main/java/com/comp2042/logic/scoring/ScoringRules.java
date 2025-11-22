package com.comp2042.logic.scoring;

import com.comp2042.logic.event.EventType;
import javafx.beans.property.IntegerProperty;

/**
 * Coordinates all scoring logic for the Tetris game.
 * This class acts as a facade that delegates to specialized scoring classes.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class ScoringRules {
    
    private final GameScore score;
    private final LineClearScoring lineClearScoring;
    private final LevelControls levelControls;
    private final MoveDownScoring moveDownScoring;
    private final LineTracker lineTracker;
    private final ComboSystem comboSystem;

    public ScoringRules(GameScore score) {
        this.score = score;
        this.lineClearScoring = new LineClearScoring();
        this.levelControls = new LevelControls();
        this.moveDownScoring = new MoveDownScoring();
        this.lineTracker = new LineTracker();
        this.comboSystem = new ComboSystem();
    }

    public IntegerProperty levelProperty() {
        return levelControls.levelProperty();
    }

    public IntegerProperty linesClearedProperty() {
        return lineTracker.linesClearedProperty();
    }

    public int getTotalLinesCleared() {
        return lineTracker.getTotalLinesCleared();
    }

    public int getCurrentLevel() {
        return levelControls.getCurrentLevel();
    }

    /**
     * Gets the current score.
     * @return the current score
     */
    public int getCurrentScore() {
        return score.getCurrentScore();
    }

    
    /**
     * Adds points for a successful move down with level bonus.
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
     * Calculates and adds points for clearing lines.
     * @param linesCleared the number of lines cleared
     * @return the points awarded for this line clear
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

        // Update level progression
        int totalLinesCleared = lineTracker.getTotalLinesCleared();
        levelControls.updateLevel(totalLinesCleared);
        
        comboSystem.incrementCombo();

        return totalPointsAwarded;
    }

    /**
     * Gets the total combo bonus.
     * @return the total combo bonus
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
     * Resets the scoring system for a new game.
     */
    public void resetAchievements() {
        score.resetScore();
        levelControls.resetLevel();
        lineTracker.resetLineTracker();
    }
    
}