package com.comp2042.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameStateControllerTest {

    private GameStateController controller; // shared controller instance for all tests

    @BeforeEach
    void setUp() {
        controller = new GameStateController();
    }

    @Test
    void getCurrentState() {
        assertEquals(GameStateController.GameState.PLAYING, controller.getCurrentState());
    }

    @Test
    void setGameState() {
        assertEquals(GameStateController.GameState.PLAYING, controller.getCurrentState());

        controller.setGameState(GameStateController.GameState.PAUSED);
        assertEquals(GameStateController.GameState.PAUSED, controller.getCurrentState());

        controller.setGameState(GameStateController.GameState.GAME_OVER);
        assertEquals(GameStateController.GameState.GAME_OVER, controller.getCurrentState());

        controller.setGameState(GameStateController.GameState.MAIN_MENU);
        assertEquals(GameStateController.GameState.MAIN_MENU, controller.getCurrentState());
    }

    @Test
    void isPlaying() {
        assertTrue(controller.isPlaying()); // initial state should be true

        controller.setGameState(GameStateController.GameState.PAUSED);
        assertFalse(controller.isPlaying());
    }

    @Test
    void isMainMenu() {
        assertFalse(controller.isMainMenu()); // initial state should be false

        controller.setGameState(GameStateController.GameState.MAIN_MENU);
        assertTrue(controller.isMainMenu());
    }

    @Test
    void isPaused() {
        assertFalse(controller.isPaused()); // initial state should be false

        controller.setGameState(GameStateController.GameState.PAUSED);
        assertTrue(controller.isPaused());

        controller.setGameState(GameStateController.GameState.PLAYING);
        assertFalse(controller.isPaused());
    }

    @Test
    void isGameOver() {
        assertFalse(controller.isGameOver()); // initial state should be false

        controller.setGameState(GameStateController.GameState.GAME_OVER);
        assertTrue(controller.isGameOver());

        controller.setGameState(GameStateController.GameState.PLAYING);
        assertFalse(controller.isGameOver());
    }

    @Test
    void pauseGame() {
        assertFalse(controller.isPaused()); // initial state should be false

        controller.setGameState(GameStateController.GameState.PLAYING);
        assertFalse(controller.isPaused());

        controller.pauseGame();
        assertTrue(controller.isPaused());

        controller.setGameState(GameStateController.GameState.GAME_OVER);
        controller.pauseGame();
        assertFalse(controller.isPaused());

    }

    @Test
    void resumeGame() {
        assertFalse(controller.isPaused()); // initial state should be false

        controller.setGameState(GameStateController.GameState.PAUSED);
        assertTrue(controller.isPaused());

        controller.resumeGame();
        assertTrue(controller.isPlaying());

        controller.setGameState(GameStateController.GameState.GAME_OVER);
        controller.resumeGame();
        assertFalse(controller.isPlaying());

    }

    @Test
    void resetGameState() {
        assertTrue(controller.isPlaying()); // initial state should be true

        controller.setGameState(GameStateController.GameState.PAUSED);
        assertFalse(controller.isPlaying());
        controller.resetGameState();
        assertTrue(controller.isPlaying());

        controller.setGameState(GameStateController.GameState.GAME_OVER);
        assertFalse(controller.isPlaying());
        controller.resetGameState();
        assertTrue(controller.isPlaying());
    }
}