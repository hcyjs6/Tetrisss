package com.comp2042.app;

import com.comp2042.logic.event.EventSource;
import com.comp2042.logic.event.EventType;
import com.comp2042.logic.event.InputEventListener;
import com.comp2042.logic.event.MoveEvent;
import com.comp2042.logic.GameStateController;
import com.comp2042.logic.scoring.ScoringRules;
import com.comp2042.logic.scoring.GameScore;
import com.comp2042.logic.speed.DropSpeedController;
import com.comp2042.gui.GuiController;
import com.comp2042.logic.Board;
import com.comp2042.logic.SimpleBoard;
import com.comp2042.logic.ViewData;
import com.comp2042.logic.DownData;
import com.comp2042.logic.ClearRow;
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
    private final Board board;
    private final GameScore gameScore;
    /**
     * Initializes the game by setting up the board, UI, and event handling.
     */
    private void initializeGame() {

        board.createNewBrick(); // Create the first falling brick
        viewGuiController.initializeDependencies(this, this, board, gameStateController);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData()); // Initialize the game view
        viewGuiController.bindScore(gameScore.scoreProperty());
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
        this.board = new SimpleBoard(20, 10);
        this.gameScore = new GameScore();
        this.scoringRules = new ScoringRules(gameScore);
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
        
        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();

        if (clearRow.getLinesRemoved() > 0) {

            int totalComboBonus = scoringRules.getTotalComboBonus();
           
            int pointsAwarded = scoringRules.add_LineCleared_Points(clearRow.getLinesRemoved());
            // Update the ClearRow with actual points for notification
            clearRow = new ClearRow(clearRow.getLinesRemoved(), clearRow.getNewMatrix(), pointsAwarded, totalComboBonus, clearRow.getClearedRowIndex());
        } else {
            // Reset the combo system if no lines were cleared
            scoringRules.resetCombo();
        }
            
        board.createNewBrick();
        
        if (board.isGameOver()) {
            gameStateController.setGameState(GameStateController.GameState.GAME_OVER);
            viewGuiController.gameOver();
        }
        
        // Only refresh background immediately if no rows were cleared
        // If rows were cleared, the fade animation will handle the refresh after it completes
        if (clearRow.getLinesRemoved() == 0) {
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        }
        return new DownData(clearRow, board.getViewData());
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
        return new DownData(null, board.getViewData());
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
            return new DownData(null, board.getViewData());
        }
        
        int dropOffset = board.softDrop();
        
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
            return new DownData(null, board.getViewData());
        }
        
        int dropOffset = board.hardDrop();
        
        // After hard drop, the block is always at the bottom and can't move down anymore
        // Pass dropOffset to handleBrickLanded so it can award drop points and lock the block
        return handleBrickLanded(event, dropOffset);
    }
    
    /**
     * Executes a movement action if the game is playing.
     * 
     * @param moveAction the movement action to execute
     * @return updated view data
     */
    private ViewData executeMovement(Runnable moveAction) {
        if (!gameStateController.isPlaying()) {
            return board.getViewData();
        }
        moveAction.run();
        return board.getViewData();
    }
    
    /**
     * Handles the left movement event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        return executeMovement(() -> board.moveBrickLeft());
    }

    /**
     * Handles the right movement event.
     * 
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        return executeMovement(() -> board.moveBrickRight());
    }

    /**
     * Handles the rotation event (counter-clockwise).
     *
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRotateLeftEvent(MoveEvent event) {
        return executeMovement(() -> board.rotateLeftBrick());
    }

    /**
     * Handles the rotation event (clockwise).
     *
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onRotateRightEvent(MoveEvent event) {
        return executeMovement(() -> board.rotateRightBrick());
    }

    /**
     * Handles the hold event.
     * Swaps the current piece with the held piece, or stores the current piece if no piece is held.
     *
     * @param event the move event
     * @return updated view data
     */
    @Override
    public ViewData onHoldEvent(MoveEvent event) {
        
        board.holdBrick();
        return board.getViewData();
    }

    /**
     * Gets the ghost piece data for the current falling piece.
     * 
     * @return ViewData containing the ghost piece position and shape
     */
   
    public ViewData getGhostPieceData() {
        return board.getGhostPieceViewData();
    }

    /**
     * Starts a new game by resetting all game state and UI.
     * This method handles the complete game reset including:
     * - Game state and board
     * - Scoring system
     * - Brick generation
     * - UI displays
     * - Drop speed
     */
    public void createNewGame() {
        
        gameStateController.resetGameState(); // RESET the game state to playing
        board.resetBoard(); // RESET the board to a new game state (RESET the brick generator and create a new brick)
        scoringRules.resetAchievements(); // RESET the scoring system (RESET the score, level, and lines cleared)
        
        // Reset drop speed to level 1 - get it from GuiController
        resetSpeedController();
        
    }

    public void resetSpeedController() {
        DropSpeedController speedController = viewGuiController.getDropSpeedController();
        if (speedController != null) {
            speedController.resetSpeed();
        }
    }
    

}