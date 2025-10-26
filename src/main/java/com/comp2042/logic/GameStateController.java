package com.comp2042.logic;

import com.comp2042.Board;
import com.comp2042.SimpleBoard;

/**
 * Controls the overall game state and coordinates between different game components.
 * This class centralizes game state management for better maintainability.
 */
public class GameStateController {
    
    private final Board board;
    private GameState currentState;
    private int currentLevel;
    private int linesCleared;
    
    public enum GameState {
        PLAYING, PAUSED, GAME_OVER, MENU
    }
    
    public GameStateController() {
        this.board = new SimpleBoard(20, 10);
        this.currentState = GameState.PLAYING;
        this.currentLevel = 1;
        this.linesCleared = 0;
    }
    
    /**
     * This method gets the current game state.
     * @return the current game state
     */
    public GameState getCurrentState() {
        return currentState;
    }
    
    /**
     * This method sets the game state.
     * @param state the new game state
     */
    public void setGameState(GameState state) {
        this.currentState = state;
    }
    
    /**
     * This method checks if the game is currently playing.
     * @return true if game is playing
     */
    public boolean isPlaying() {
        return currentState == GameState.PLAYING;
    }
    
    /**
     * Checks if the game is paused.
     * @return true if game is paused
     */
    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }
    
    /**
     * This method checks if the game is over.
     * @return true if game is over
     */
    public boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
    }
    
    /**
     * This method pauses the game.
     */
    public void pauseGame() {
        if (currentState == GameState.PLAYING) {
            currentState = GameState.PAUSED;
        }
    }
    
    /**
     * This method resumes the game.
     */
    public void resumeGame() {
        if (currentState == GameState.PAUSED) {
            currentState = GameState.PLAYING;
        }
    }
    
    /**
     * This method starts a new game.
     */
    public void startNewGame() {
        board.newGame();
        currentState = GameState.PLAYING;
        currentLevel = 1;
        linesCleared = 0;
    }
    
    /**
     * This method handles total lines cleared when new lines are cleared and updates the current level.
     * @param linesRemoved the number of lines cleared
     */
    public void totalLinesCleared(int linesRemoved) {
        linesCleared += linesRemoved;
        updateLevel();
    }
    
    /**
     * This method updates the current level based on lines cleared.
     */
    private void updateLevel() {
        int newLevel = (linesCleared / 10) + 1;
        if (newLevel > currentLevel) {
            currentLevel = newLevel;
        }
    }
    
    public Board getBoard() {
        return board;
    }
    
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public int getLinesCleared() {
        return linesCleared;
    }
}