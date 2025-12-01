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
import com.comp2042.audio.SoundEffect;

/**
 * The GameController class coordinates between the game board and UI.
 * Handles input events, manages the game flow, and processes game logic including
 * brick movements, scoring, level progression, and game over conditions.
 * 
 * This controller acts as the central coordinator that receives input events from the UI,
 * processes them through the game board, and updates the scoring system accordingly.
 * It implements the InputEventListener interface to handle various game actions such as
 * movement, rotation, holding pieces, and dropping bricks.
 * 
 * @author Sek Joe Rin
 */
public class GameController implements InputEventListener {

    private final GameStateController gameStateController;  // Controls game state
    private final ScoringRules scoringRules;  // Manages scoring logic
    private final GuiController viewGuiController;  // Create a new GUI controller
    private final Board board;
    private final GameScore gameScore;
    private final SoundEffect gameOverSFX;

    /**
     * Creates a new game controller and initializes the game.
     * Sets up the game board, scoring system, GUI controller, and game state.
     * The game is initialized with the specified starting level.
     * 
     * @param guiController the GUI controller for UI updates and rendering
     * @param selectedLevel the initial level to start the game at (must be between 1 and 100)
     */
    public GameController(GuiController guiController, int selectedLevel) {
        this.gameStateController = new GameStateController();
        this.board = new SimpleBoard(20, 10);
        this.gameScore = new GameScore();
        this.scoringRules = new ScoringRules(gameScore);
        this.viewGuiController = guiController;
        this.gameOverSFX = new SoundEffect("Audio/gameOverSFX.wav");

        // Set the initial level before initializing the game
        if (selectedLevel >= 1 && selectedLevel <= 100) {
            scoringRules.setLevelValue(selectedLevel);
        }
        
        initializeGame();
    }

    /**
     * Initializes the game by setting up the board, UI, and event handling.
     * Creates the first falling brick, initializes dependencies between controllers,
     * sets up the game view, and binds score, level, and lines cleared properties to the UI.
     */
    private void initializeGame() {
        board.createNewBrick(); // Create the first falling brick
        viewGuiController.initializeDependencies(this, this, board, gameStateController);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData()); // Initialize the game view
        viewGuiController.bindScore(gameScore.scoreProperty());
        viewGuiController.bindLevel(scoringRules.levelProperty()); 
        viewGuiController.bindLinesCleared(scoringRules.linesClearedProperty());
        
    }

     /**
     * Handles when a brick has landed and cannot move further down.
     * Merges the brick to the background, clears completed rows, awards points, creates a new brick, and checks for game over conditions.
     * 
     * @param event the move event that triggered this landing
     * @param dropOffset the number of rows the brick was dropped (used for awarding drop points on hard drops)
     * @return DownData containing information about cleared rows and the new view data
     */
    private DownData handleBrickLanded(MoveEvent event, int dropOffset) {
        // Award drop points if this is a hard drop with a valid drop distance
        if (dropOffset > 0 && event.getEventSource() == EventSource.USER && event.getEventType() == EventType.HARD_DROP) {
            scoringRules.add_MoveDown_Points(event.getEventType(), dropOffset);
        }
        
        board.mergeBrickToBackground();
        viewGuiController.refreshGameBoard(board.getBoardMatrix());
        viewGuiController.refreshBrick(board.getViewData());
        ClearRow clearRow = board.clearRows();

        if (clearRow.getLinesRemoved() > 0) {

            int combo = scoringRules.getComboMultiplier();
            int totalComboBonus = scoringRules.getTotalComboBonus();
            int pointsAwarded = scoringRules.add_LineCleared_Points(clearRow.getLinesRemoved());
            boolean levelUp = scoringRules.isLevelUp();
            
            // Update the ClearRow with actual points for notification
            clearRow = new ClearRow(clearRow.getLinesRemoved(), clearRow.getNewMatrix(), pointsAwarded, combo, totalComboBonus, clearRow.getClearedRowIndex(), levelUp);
        } else {
            // Reset the combo system if no lines were cleared
            scoringRules.resetCombo();
        }
            
        board.createNewBrick();
        
        if (board.isGameOver()) {
            // Display the last brick at spawn position before showing game over
            viewGuiController.refreshGameBoard(board.getBoardMatrix());
            viewGuiController.refreshBrick(board.getViewData());
            
            gameOverSFX.playSFX();
            gameStateController.setGameState(GameStateController.GameState.GAME_OVER);
            viewGuiController.gameOver();
        }
        
        // Only refresh background immediately if no rows were cleared
        // If rows were cleared, the fade animation will handle the refresh after it completes
        if (clearRow.getLinesRemoved() == 0) {
            viewGuiController.refreshGameBoard(board.getBoardMatrix());
        }
        return new DownData(clearRow, board.getViewData());
    }
    
    /**
     * Handles when a brick successfully moved down without landing.
     * Awards points for the drop movement if handled by the user.
     * 
     * @param event the move event that triggered this movement
     * @param dropOffset the number of cells the brick moved down
     * @return DownData containing the updated view data (no cleared rows in this case)
     */
    private DownData handleBrickMoved(MoveEvent event, int dropOffset) {
        if (event.getEventSource() == EventSource.USER) {
            scoringRules.add_MoveDown_Points(event.getEventType(), dropOffset);  // Add points for drop
        }
        return new DownData(null, board.getViewData());
    }
    
    /**
     * Handles the soft drop event where the piece moves down one step.
     * Only processes the event if the game is currently playing.
     * If the brick lands after the drop movement, handles the landing logic; otherwise, handles the movement.
     * 
     * @param event the move event containing information about the soft drop action
     * @return DownData containing information about the result (cleared rows if landed, or just view data)
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
     * Handles the hard drop event where the piece drops to the bottom instantly.
     * Only processes the event if the game is currently playing.
     * 
     * @param event the move event containing information about the hard drop action
     * @return DownData containing information about cleared rows and the new view data
     */
    @Override
    public DownData onHardDropEvent(MoveEvent event) {
        // Only process if game is playing
        if (!gameStateController.isPlaying()) {
            return new DownData(null, board.getViewData());
        }
        
        int dropOffset = board.hardDrop();
    
        return handleBrickLanded(event, dropOffset);
    }
    
    /**
     * Executes a movement action if the game is currently playing.
     * This is a helper method that ensures movements only occur during active gameplay.
     * 
     * @param moveAction the movement action to execute on the board
     * @return updated view data after the movement, or current view data if game is not playing
     */
    private ViewData executeMovement(Runnable moveAction) {
        if (!gameStateController.isPlaying()) {
            return board.getViewData();
        }
        moveAction.run();
        return board.getViewData();
    }
    
    /**
     * Handles the left movement event where the piece moves one position to the left.
     * Only processes if the game is currently playing.
     * 
     * @param event the move event containing information about the left movement action
     * @return updated view data after the left movement action of the brick
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        return executeMovement(() -> board.moveBrickLeft());
    }

    /**
     * Handles the right movement event where the piece moves one position to the right.
     * Only processes if the game is currently playing.
     * 
     * @param event the move event containing information about the right movement action
     * @return updated view data after the right movement action of the brick
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        return executeMovement(() -> board.moveBrickRight());
    }

    /**
     * Handles the rotation event where the piece rotates anti-clockwise (left).
     * Only processes if the game is currently playing.
     * 
     * @param event the move event containing information about the rotation action
     * @return updated view data after the left rotation action of the brick
     */
    @Override
    public ViewData onRotateLeftEvent(MoveEvent event) {
        return executeMovement(() -> board.rotateLeftBrick());
    }

    /**
     * Handles the rotation event where the piece rotates clockwise (right).
     * Only processes if the game is currently playing.
     * 
     * @param event the move event containing information about the rotation action
     * @return updated view data after the right rotation action of the brick
     */
    @Override
    public ViewData onRotateRightEvent(MoveEvent event) {
        return executeMovement(() -> board.rotateRightBrick());
    }

    /**
     * Handles the hold event where the current piece is stored or swapped with a previously held piece.
     * If no piece is currently held, the current piece is stored in the hold area.
     * If a piece is already held, it swaps the current piece with the held piece.
     * Only processes if the game is currently playing.
     * 
     * @param event the move event containing information about the hold action
     * @return updated view data after the hold action of the brick
     */
    @Override
    public ViewData onHoldEvent(MoveEvent event) {
        
        board.holdBrick();
        return board.getViewData();
    }
    

    /**
     * Starts a new game by resetting all game state and UI.
     * This method handles the complete game reset including:
     * - Game state and board
     * - Scoring system
     * - Brick generation
     * - Drop speed
     */
    public void createNewGame() {
        
        gameStateController.resetGameState(); // RESET the game state to playing
        board.resetBoard(); // RESET the board to a new game state (RESET the brick generator and create a new brick)
        scoringRules.resetAchievements(); // RESET the scoring system (RESET the score, level, and lines cleared)
        
        // Reset drop speed to level 1 - get it from GuiController
        resetSpeedController();
        
    }

    /**
     * Resets the drop speed controller to its initial state.
     * This is called when starting a new game to reset the falling speed
     * to match the current level. Retrieves the speed controller from the GUI controller
     * and resets it if available.
     */
    public void resetSpeedController() {
        DropSpeedController speedController = viewGuiController.getDropSpeedController();
        if (speedController != null) {
            speedController.resetSpeed();
        }
    }
    

}