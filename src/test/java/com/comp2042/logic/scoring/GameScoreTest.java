package com.comp2042.logic.scoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameScoreTest {

    private GameScore gameScore;

    @BeforeEach
    void setUp() {
        gameScore = new GameScore();
    }

    @Test
    void scoreProperty() {
        assertEquals(0, gameScore.scoreProperty().getValue());
    }

    @Test
    void addPoints() {
        assertEquals(0, gameScore.getCurrentScore());
        gameScore.addPoints(100);
        assertEquals(100, gameScore.getCurrentScore());
        gameScore.addPoints(200);
        assertEquals(300, gameScore.getCurrentScore());
    }

    @Test
    void getCurrentScore() {
        assertEquals(0, gameScore.getCurrentScore());
        gameScore.addPoints(100);
        assertEquals(100, gameScore.getCurrentScore());
    }

    @Test
    void resetScore() {
        assertEquals(0, gameScore.getCurrentScore());
        gameScore.addPoints(100);
        assertEquals(100, gameScore.getCurrentScore());
        gameScore.resetScore();
        assertEquals(0, gameScore.getCurrentScore());
    }
}