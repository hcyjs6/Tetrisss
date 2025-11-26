package com.comp2042.logic;

/**
 * Controls the overall game state and coordinates between different game components.
 * This class centralizes game state management for the game.
 */
public class GameStateController {
    
    private GameState currentState;

    public enum GameState {
        PLAYING, PAUSED, GAME_OVER, MAIN_MENU
    }
    
    public GameStateController() {
        this.currentState = GameState.PLAYING;
     
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

    public boolean isMainMenu() {
        return currentState == GameState.MAIN_MENU;
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

    public void resetGameState() { 
        currentState = GameState.PLAYING;
    }
    

    
}