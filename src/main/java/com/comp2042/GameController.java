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
     * Initializes the game by setting up the board, UI, and event handling.
     */
    private void initializeGame() {
        gameStateController.getBoard().createNewBrick(); // Create the first falling brick
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(gameStateController.getBoard().getBoardMatrix(), gameStateController.getBoard().getViewData());
        viewGuiController.bindScore(gameStateController.getBoard().getScore().scoreProperty());
        // Initialize level and lines cleared display
        viewGuiController.bindLevel(scoringRules.levelProperty());
        viewGuiController.bindLinesCleared(scoringRules.linesClearedProperty());
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
     * Handles when a brick has landed and cannot move further.
     * 
     * @param event the move event
     * @param dropOffset optional drop distance for awarding drop points (used for hard drops)
     * @return data about the result including cleared rows
     */
    private DownData handleBrickLanded(MoveEvent event, int dropOffset) {
        // Award drop points if this is a hard drop with a valid drop distance
        if (dropOffset > 0 && event.getEventSource() == EventSource.USER && 
            event.getEventType() == EventType.HARD_DROP) {
            scoringRules.add_MoveDown_Points(event.getEventType(), dropOffset);
        }
        
        gameStateController.getBoard().mergeBrickToBackground();
        ClearRow clearRow = gameStateController.getBoard().clearRows();
        

        if (clearRow.getLinesRemoved() > 0) {
            
            int pointsAwarded = scoringRules.add_LineCleared_Points(clearRow.getLinesRemoved());
            // Update the ClearRow with actual points for notification
            clearRow = new ClearRow(clearRow.getLinesRemoved(), clearRow.getNewMatrix(), pointsAwarded);
        
        }else{
            // Reset the combo system if no lines were cleared
            scoringRules.resetCombo();
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
    private DownData handleBrickMoved(MoveEvent event, int dropOffset) {
        if (event.getEventSource() == EventSource.USER) {
            scoringRules.add_MoveDown_Points(event.getEventType(), dropOffset);  // Add points for drop
        }
        return new DownData(null, gameStateController.getBoard().getViewData());
    }
    
    /**
     * Handles the soft drop event (piece moves down one step).
     * 
     * @param event the move event
     * @return data about the move result including any cleared rows and the view data
     */
    @Override
    public DownData onSoftDropEvent(MoveEvent event) {
        // Only process if game is playing
        if (!gameStateController.isPlaying()) {
            return new DownData(null, gameStateController.getBoard().getViewData());
        }
        
        int dropOffset = gameStateController.getBoard().softDrop();
        
        if (dropOffset == 0) {
            return handleBrickLanded(event, dropOffset);
        } else {
            return handleBrickMoved(event, dropOffset);
        }
    }

    /**
     * Handles the hard drop event (piece drops to bottom instantly).
     * 
     * @param event the move event
     * @return data about the move result including any cleared rows and the view data
     */
    @Override
    public DownData onHardDropEvent(MoveEvent event) {
        // Only process if game is playing
        if (!gameStateController.isPlaying()) {
            return new DownData(null, gameStateController.getBoard().getViewData());
        }
        
        int dropOffset = gameStateController.getBoard().hardDrop();
        
        // After hard drop, the block is always at the bottom and can't move down anymore
        // Pass dropOffset to handleBrickLanded so it can award drop points and lock the block
        return handleBrickLanded(event, dropOffset);
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

    @Override
    public ViewData getGhostPieceData() { // get the ghost piece data for the current brick
        return gameStateController.getBoard().getGhostPieceViewData();
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