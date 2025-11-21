package com.comp2042.speed;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.util.Duration;

/**
 * Controls the automatic drop speed of the active tetromino based on the current level.
 */
public class DropSpeedController {

    private static final double STARTING_DELAY = 800.0;
    private static final double MIN_DELAY = 10.0;
    private static final double DELAY_STEP = 80.0;

    private final Timeline timeline; // the timer that controls the drop speed
    private final Runnable dropTask; // what to do when move piece down

    public DropSpeedController(Runnable dropTask) {
        this.dropTask = dropTask;
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE); // timeline that repeats forever
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void bindLevel(IntegerProperty levelProperty) {
        updateForLevel(levelProperty.get());
        levelProperty.addListener((obs, oldVal, newVal) -> updateForLevel(newVal.intValue()));
    }

    public void updateSpeed(int level) {
        updateForLevel(level);
    }

    private void updateForLevel(int level) {
        
        if (level <= 0) {
            level = 1;
        }

        double delay = STARTING_DELAY - (level - 1) * DELAY_STEP;
        delay = Math.max(MIN_DELAY, delay);

        timeline.stop(); // stop the old timer
        timeline.getKeyFrames().setAll(new KeyFrame(Duration.millis(delay), _ -> dropTask.run())); // set the new timer with the new delay
        timeline.play(); // start the new timer
    }

    public void resetSpeed() {
        updateSpeed(1);
    }
}
