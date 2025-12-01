package com.comp2042.logic;

/**
 * Handles the overall game state of the Tetris game.
 * This class contains the current game state and methods to change the game state.
 * 
 * @author Sek Joe Rin
 */
public class GameStateController {
    
    private GameState currentState;

    /**
     * Represents the possible states of the game.
     */
    public enum GameState {
        PLAYING, PAUSED, GAME_OVER, MAIN_MENU
    }
    
    /**
     * Creates a new GameStateController with the initial state set to PLAYING.
     */
    public GameStateController() {
        this.currentState = GameState.PLAYING;
     
    }
    
    /**
     * Gets the current game state.
     * 
     * @return the current game state
     */
    public GameState getCurrentState() {
        return currentState;
    }
    
    /**
     * Sets the game state to the specified state.
     * 
     * @param state the new game state
     */
    public void setGameState(GameState state) {
        this.currentState = state;
    }

    /**
     * Checks if the game is currently playing.
     * 
     * @return true if game is playing, false otherwise
     */
    public boolean isPlaying() {
        return currentState == GameState.PLAYING;
    }

    /**
     * Checks if the game is in the main menu state.
     * 
     * @return true if game is in main menu, false otherwise
     */
    public boolean isMainMenu() {
        return currentState == GameState.MAIN_MENU;
    }
    
    /**
     * Checks if the game is paused.
     * 
     * @return true if game is paused, false otherwise
     */
    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }
    
    /**
     * Checks if the game is over.
     * 
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver() { 
        return currentState == GameState.GAME_OVER;
    }
    
    /**
     * Pauses the game from playing state.
     */
    public void pauseGame() { 
        if (currentState == GameState.PLAYING) {
            currentState = GameState.PAUSED;
        }
    }
    
    /**
     * Resumes the game from paused state.
     */
    public void resumeGame() { 
        if (currentState == GameState.PAUSED) {
            currentState = GameState.PLAYING;
        }
    }

    /**
     * Resets the game state to PLAYING.
     * This is called when starting a new game.
     */
    public void resetGameState() { 
        currentState = GameState.PLAYING;
    }
    

    
}