package com.comp2042;

import com.comp2042.logic.GameStateManager;

/**
 * The GameController class coordinates between the game board and UI.
 * Handles input events and manages the game flow.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class GameController implements InputEventListener {

    private final GameStateManager gameStateManager;  // Manages game state
    private final GuiController viewGuiController;  // Create a new GUI controller

    /**
     * Creates a new game controller and initializes the game.
     * 
     * @param guiController the GUI controller for UI updates
     */
    public GameController(GuiController guiController) {
        this.gameStateManager = new GameStateManager();
        this.viewGuiController = guiController;
        initializeGame();
    }
    
    /**
     * Initializes the game by setting up the board, UI, and event handling.
     */
    private void initializeGame() {
        gameStateManager.getBoard().createNewBrick(); // Create the first falling brick
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(gameStateManager.getBoard().getBoardMatrix(), gameStateManager.getBoard().getViewData());
        viewGuiController.bindScore(gameStateManager.getBoard().getScore().scoreProperty());
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
        if (!gameStateManager.isPlaying()) {
            return new DownData(null, gameStateManager.getBoard().getViewData());
        }
        
        boolean canMove = gameStateManager.getBoard().moveBrickDown();
        
        if (!canMove) {
            return handleBrickLanded(event);
        } else {
            return handleBrickMoved(event);
        }
    }
    
    /**
     * Handles when a brick has landed and cannot move further.
     * 
     * @param event the move event
     * @return data about the result including cleared rows
     */
    private DownData handleBrickLanded(MoveEvent event) {
        gameStateManager.getBoard().mergeBrickToBackground();
        ClearRow clearRow = gameStateManager.getBoard().clearRows();
        
        if (clearRow.getLinesRemoved() > 0) {
            gameStateManager.getBoard().getScore().addPoints(clearRow.getScoreBonus());
            gameStateManager.totalLinesCleared(clearRow.getLinesRemoved());
        }
        
        gameStateManager.getBoard().createNewBrick();
        
        if (((SimpleBoard) gameStateManager.getBoard()).isGameOver()) {
            gameStateManager.setGameState(GameStateManager.GameState.GAME_OVER);
            viewGuiController.gameOver();
        }
        
        viewGuiController.refreshGameBackground(gameStateManager.getBoard().getBoardMatrix());
        return new DownData(clearRow, gameStateManager.getBoard().getViewData());
    }
    
    /**
     * Handles when a brick successfully moved down.
     * 
     * @param event the move event
     * @return data about the move result and the view data
     */
    private DownData handleBrickMoved(MoveEvent event) {
        if (event.getEventSource() == EventSource.USER) {
            gameStateManager.getBoard().getScore().addPoints(1);
        }
        return new DownData(null, gameStateManager.getBoard().getViewData());
    }

    /**
     * Handles the left movement event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        if (!gameStateManager.isPlaying()) {
            return gameStateManager.getBoard().getViewData();
        }
        gameStateManager.getBoard().moveBrickLeft();
        return gameStateManager.getBoard().getViewData();
    }

    /**
     * Handles the right movement event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        if (!gameStateManager.isPlaying()) {
            return gameStateManager.getBoard().getViewData();
        }
        gameStateManager.getBoard().moveBrickRight();
        return gameStateManager.getBoard().getViewData();
    }

    /**
     * Handles the rotation event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        if (!gameStateManager.isPlaying()) {
            return gameStateManager.getBoard().getViewData();
        }
        gameStateManager.getBoard().rotateLeftBrick();
        return gameStateManager.getBoard().getViewData();
    }

    /**
     * Starts a new game by resetting the board and UI.
     */
    @Override
    public void createNewGame() {
        gameStateManager.startNewGame();
        viewGuiController.refreshGameBackground(gameStateManager.getBoard().getBoardMatrix());
    }

    // Game state controls 
    public void pauseGame() {
        gameStateManager.pauseGame();
    }

    public void resumeGame() {
        gameStateManager.resumeGame();
    }

    public void togglePause() {
        if (gameStateManager.isPaused()) {
            gameStateManager.resumeGame();
        } else if (gameStateManager.isPlaying()) {
            gameStateManager.pauseGame();
        }
    }
}