package com.comp2042;

import com.comp2042.logic.GameStateController;
import com.comp2042.logic.ScoringRules;

/**
 * The GameController class coordinates between the game board and UI.
 * Handles input events and manages the game flow.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class GameController implements InputEventListener {

    private final GameStateController gameStateController;  // Controls game state
    private final ScoringRules scoringRules;  // Manages scoring logic
    private final GuiController viewGuiController;  // Create a new GUI controller

    /**
     * Handles when a brick has landed and cannot move further.
     * 
     * @param event the move event
     * @return data about the result including cleared rows
     */
    private DownData handleBrickLanded(MoveEvent event) {
        gameStateController.getBoard().mergeBrickToBackground();
        ClearRow clearRow = gameStateController.getBoard().clearRows();
        
        if (clearRow.getLinesRemoved() > 0) {
         
            scoringRules.add_LineCleared_Points(clearRow.getLinesRemoved());
           
        }
        
        gameStateController.getBoard().createNewBrick();
        
        if (((SimpleBoard) gameStateController.getBoard()).isGameOver()) {
            gameStateController.setGameState(GameStateController.GameState.GAME_OVER);
            viewGuiController.gameOver();
        }
        
        viewGuiController.refreshGameBackground(gameStateController.getBoard().getBoardMatrix());
        return new DownData(clearRow, gameStateController.getBoard().getViewData());
    }
    
    /**
     * Handles when a brick successfully moved down.
     * 
     * @param event the move event
     * @return data about the move result and the view data
     */
    private DownData handleBrickMoved(MoveEvent event) {
        if (event.getEventSource() == EventSource.USER) {
            scoringRules.add_MoveDown_Points();  // Use ScoringRules instead of direct score
        }
        return new DownData(null, gameStateController.getBoard().getViewData());
    }

    /**
     * Initializes the game by setting up the board, UI, and event handling.
     */
    private void initializeGame() {
        gameStateController.getBoard().createNewBrick(); // Create the first falling brick
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(gameStateController.getBoard().getBoardMatrix(), gameStateController.getBoard().getViewData());
        viewGuiController.bindScore(gameStateController.getBoard().getScore().scoreProperty());
    }

    /**
     * Creates a new game controller and initializes the game.
     * 
     * @param guiController the GUI controller for UI updates
     */
    public GameController(GuiController guiController) {
        this.gameStateController = new GameStateController();
        this.scoringRules = new ScoringRules(gameStateController.getBoard().getScore());
        this.viewGuiController = guiController;
        initializeGame();
    }
    

    /**
     * Handles the down movement event (brick falling).
     * 
     * @param event the move event
     * @return data about the move result including any cleared rows and the view data
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        // Only process if game is playing
        if (!gameStateController.isPlaying()) {
            return new DownData(null, gameStateController.getBoard().getViewData());
        }
        
        boolean canMove = gameStateController.getBoard().moveBrickDown();
        
        if (!canMove) {
            return handleBrickLanded(event);
        } else {
            return handleBrickMoved(event);
        }
    }
    
    
    /**
     * Handles the left movement event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        if (!gameStateController.isPlaying()) {
            return gameStateController.getBoard().getViewData();
        }
        gameStateController.getBoard().moveBrickLeft();
        return gameStateController.getBoard().getViewData();
    }

    /**
     * Handles the right movement event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        if (!gameStateController.isPlaying()) {
            return gameStateController.getBoard().getViewData();
        }
        gameStateController.getBoard().moveBrickRight();
        return gameStateController.getBoard().getViewData();
    }

    /**
     * Handles the rotation event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        if (!gameStateController.isPlaying()) {
            return gameStateController.getBoard().getViewData();
        }
        gameStateController.getBoard().rotateLeftBrick();
        return gameStateController.getBoard().getViewData();
    }

    /**
     * Starts a new game by resetting the board and UI.
     */
    @Override
    public void createNewGame() {
        gameStateController.startNewGame();
        scoringRules.reset();  // Reset scoring system for new game
        viewGuiController.refreshGameBackground(gameStateController.getBoard().getBoardMatrix());
    }

    // Game state controls 
    public void pauseGame() {
        gameStateController.pauseGame();
    }

    public void resumeGame() {
        gameStateController.resumeGame();
    }

    public void togglePause() {
        if (gameStateController.isPaused()) {
            gameStateController.resumeGame();
        } else if (gameStateController.isPlaying()) {
            gameStateController.pauseGame();
        }
    }
    
    // Scoring information for UI display
    public int getCurrentLevel() {
        return scoringRules.getCurrentLevel();
    }
    
    public int getTotalLinesCleared() {
        return scoringRules.getTotalLinesCleared();
    }
    
    public int getCurrentScore() {
        return scoringRules.getCurrentScore();
    }
}