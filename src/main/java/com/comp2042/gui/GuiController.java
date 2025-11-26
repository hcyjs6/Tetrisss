package com.comp2042.gui;

import com.comp2042.audio.BackgroundMusic;
import com.comp2042.audio.SoundEffect;
import com.comp2042.logic.event.EventSource;
import com.comp2042.logic.event.EventType;
import com.comp2042.logic.event.MoveEvent;
import com.comp2042.logic.event.InputEventListener;
import com.comp2042.logic.GameStateController;
import com.comp2042.logic.speed.DropSpeedController;
import com.comp2042.logic.Board;
import com.comp2042.logic.ViewData;
import com.comp2042.logic.DownData;
import com.comp2042.app.GameController;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 28;

    @FXML
    private GridPane gamePanel;

    @FXML
    private StackPane groupNotification;

    @FXML
    private StackPane countdownOverlay;

    @FXML
    private Label countdownLabel;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane ghostPanel;

    @FXML
    private GridPane nextBrickPanel;

    @FXML
    private GridPane holdBrickPanel;

    @FXML
    private VBox pausePanel;

    @FXML
    private VBox gameOverPanel;

    @FXML
    private Label gameOverScoreLabel;

    @FXML
    private Label pauseScoreLabel;

    @FXML
    private VBox confirmPanel;

    @FXML
    private Label confirmMessage;

    @FXML
    private Label alertMessage;

    @FXML
    private Pane darkOverlay;
    
    

    @FXML
    private BorderPane gameBoard;

    @FXML
    private VBox scorePanel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label linesLabel;

    @FXML
    private Pane controlPanel;

    private GameController gameController;
    private GameStateController gameStateController;
    private Board board;
    private Stage stage;
    private Rectangle[][] gameBoardMatrix;
    private ClearRowEffect clearRowEffect;
    private InputEventListener eventListener;
    private Rectangle[][] currentBrickMatrix;
    private Rectangle[][] ghostPieceMatrix;
    private DropSpeedController dropSpeedController;
    private Timeline timeLine;
    private boolean isResume = false;
    private boolean isCountDownEnd;
    private Countdown countdown;
    private final BackgroundMusic backgroundMusic = new BackgroundMusic("Audio/tetrisBGM.mp3");
    private final SoundEffect buttonClickSFX = new SoundEffect("Audio/clickButtonSFX.wav");
    private final SoundEffect crossButtonSFX = new SoundEffect("Audio/crossButtonSFX.wav");
    private boolean dropPaused;
    private String actionSelected; // Tracks which action needs confirmation
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                
                if (isCountDownEnd && !gameStateController.isPaused() && !gameStateController.isGameOver() && gameStateController.isPlaying()) {
                    
                    if (eventListener == null) {
                        return;
                    }
                    
                    if (keyEvent.getCode() == KeyCode.LEFT) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP) {
                        refreshBrick(eventListener.onRotateRightEvent(new MoveEvent(EventType.ROTATE_RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.Z) {
                        refreshBrick(eventListener.onRotateLeftEvent(new MoveEvent(EventType.ROTATE_LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN) { // Soft drop
                        // Soft drop: move down one step
                        moveDown(new MoveEvent(EventType.SOFT_DROP, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.SPACE) { // Hard drop
                        // Hard drop: drop to bottom instantly
                        moveDown(new MoveEvent(EventType.HARD_DROP, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.ESCAPE) { // Toggle Pause
                        pauseGame(null);
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.C) { // Hold
                        refreshBrick(eventListener.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER)));
                        HoldBrickRenderer.initHoldBrick(holdBrickPanel, eventListener.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER)).getHoldBrickData());
                        keyEvent.consume();
                    } 
                }
 
            }
        });
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false); // Initially hide the pause panel
        controlPanel.setVisible(false); // Initially hide the control panel
        confirmPanel.setVisible(false); // Initially hide the confirmation panel
        darkOverlay.setVisible(false); // Initially hide the dark overlay
        nextBrickPanel.setVisible(false);
        countdownOverlay.setVisible(false); 
        ghostPanel.setVisible(false);
        countdown = new Countdown(countdownOverlay, countdownLabel, brickPanel, ghostPanel, nextBrickPanel, this::isCountdownFinished);
    }

    private void moveDown(MoveEvent event) {
        if (!isCountDownEnd || gameStateController.isPaused()) {
            gamePanel.requestFocus();
            return;
        }
        if (eventListener == null) {
            gamePanel.requestFocus();
            return;
        }
        if (gameStateController.isPlaying()) {
            DownData downData;
            
            // Handle drop types only
            if (event.getEventType() == EventType.SOFT_DROP) {
                downData = eventListener.onSoftDropEvent(event);
            } else if (event.getEventType() == EventType.HARD_DROP) {
                downData = eventListener.onHardDropEvent(event);
            } else {
                // No other down events handled
                return;
            } 
               
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                playClearRowEffect(downData);
            } else {
                refreshBrick(downData.getViewData());
            }
        }
        gamePanel.requestFocus();
    }

    // Initialize the game view from the GAMECONTROLLER
    public void initGameView(int[][] boardMatrix, ViewData objectData) {

        // Display the game board background with all grid rectangles
        gameBoardMatrix = GameBoardRenderer.initGameBoard(gamePanel, boardMatrix, BRICK_SIZE);

        clearRowEffect = new ClearRowEffect(gameBoardMatrix, groupNotification, this);

        // Display the current brick with all grid rectangles
        currentBrickMatrix = BrickRenderer.initCurrentBrick(brickPanel, objectData.getBrickData(), BRICK_SIZE);
        brickPanel.setLayoutX(gameBoard.getLayoutX() + 10 + objectData.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(gameBoard.getLayoutY() + 10 + objectData.getyPosition() * BRICK_SIZE);

        // Display the next brick with all grid rectangles
        NextBrickRenderer.initNextBrick(nextBrickPanel, objectData.getNextBrickData());

        // Display the hold brick (if any)
        HoldBrickRenderer.initHoldBrick(holdBrickPanel, objectData.getHoldBrickData());

        // Initialize the drop speed controller
        dropSpeedController = new DropSpeedController(() -> moveDown(new MoveEvent(EventType.SOFT_DROP, EventSource.THREAD)));
        timeLine = dropSpeedController.getTimeline();
           
        ghostPieceMatrix = GhostPieceRenderer.initGhostPiece(ghostPanel, objectData.getGhostPieceData(), BRICK_SIZE);
        refreshGhostPiece(board.getGhostPieceViewData());
            
        // Display the countdown
        startCountdown();
    }

    //  Refresh the brick display when the brick moves 
    public void refreshBrick(ViewData objectData) {
        if (!gameStateController.isPaused() && !gameStateController.isGameOver() && gameStateController.isPlaying()) {
           
            brickPanel.setLayoutX(gameBoard.getLayoutX() + 10 + objectData.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(gameBoard.getLayoutY() + 10 + objectData.getyPosition() * BRICK_SIZE);
            
            for (int i = 0; i < objectData.getBrickData().length; i++) {
                for (int j = 0; j < objectData.getBrickData()[i].length; j++) {
                    BrickRenderer.drawBrickCell(currentBrickMatrix[i][j], objectData.getBrickData()[i][j]);
                }
            }
            
            // Refresh ghost piece after current piece moves   
            refreshGhostPiece(board.getGhostPieceViewData());
            NextBrickRenderer.initNextBrick(nextBrickPanel, objectData.getNextBrickData());
            // Refresh hold brick display
            HoldBrickRenderer.initHoldBrick(holdBrickPanel, objectData.getHoldBrickData());
        }
    }

    private void refreshGhostPiece(ViewData objectData) {
        if (ghostPieceMatrix == null || objectData == null || objectData.getGhostPieceData() == null) {
            return;
        }

        ghostPanel.setLayoutX(gameBoard.getLayoutX() + 10 + objectData.getxPosition() * BRICK_SIZE);
        ghostPanel.setLayoutY(gameBoard.getLayoutY() + 10 + objectData.getyPosition() * BRICK_SIZE);

        for (int i = 0; i < objectData.getGhostPieceData().length; i++) {
            for (int j = 0; j < objectData.getGhostPieceData()[i].length; j++) {
                GhostPieceRenderer.drawGhostCell(ghostPieceMatrix[i][j], objectData.getGhostPieceData()[i][j]);
            }
        }
    }

    public void refreshGameBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                GameBoardRenderer.drawGameBoardCell(board[i][j], gameBoardMatrix[i][j]);
            }
        }
    }
 
    public void startCountdown() {
        if (timeLine != null) {
            timeLine.stop();
        }

        isCountDownEnd = false;
        countdown.start(isResume);
    }

    void pauseAutoDrop() { // pause auto drop for effect
        if (timeLine != null && timeLine.getStatus() == Animation.Status.RUNNING) {
            dropPaused = true;
            timeLine.pause();
        }
    }

    void resumeAutoDrop() { // resume auto drop after effect
        if (dropPaused && timeLine != null && gameStateController.isPlaying() && isCountDownEnd) {
            timeLine.play();
        }
        dropPaused = false;
    }

    private void isCountdownFinished() {
        isCountDownEnd = true;
        isResume = false;
        timeLine.play();
        backgroundMusic.playBGM();
        gamePanel.requestFocus();
    }
    
    private void playClearRowEffect(DownData downData) {
        clearRowEffect.play(downData.getClearRow(), downData);
        
    }

    /**
     * Initializes all dependencies at once.
     * 
     * @param eventListener the input event listener
     * @param gameController the game controller instance
     * @param board the board instance
     * @param gameStateController the game state controller instance
     */
    public void initializeDependencies(InputEventListener eventListener, GameController gameController, Board board, GameStateController gameStateController) {
        this.eventListener = eventListener;
        this.gameController = gameController;
        this.board = board;
        this.gameStateController = gameStateController;
    }

    // Bind the score to the score label from the GAMECONTROLLER
    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString());
    }


    // Bind the level to the level label from the GAMECONTROLLER
    public void bindLevel(IntegerProperty integerProperty) {
        levelLabel.textProperty().bind(integerProperty.asString());
        if (dropSpeedController != null) {
            dropSpeedController.bindLevel(integerProperty);
        }
    }
  
    // Bind the lines cleared to the lines label from the GAMECONTROLLER
    public void bindLinesCleared(IntegerProperty integerProperty) {
        linesLabel.textProperty().bind(integerProperty.asString());
    }
    
    public DropSpeedController getDropSpeedController() {
        return dropSpeedController;
    }

    public void gameOver() {
       
        gameStateController.setGameState(GameStateController.GameState.GAME_OVER);
        timeLine.stop();
        backgroundMusic.stopBGM();
        darkOverlay.setVisible(true);
        gameOverScoreLabel.setText(scoreLabel.getText());
        gameOverPanel.setVisible(true);
       
    }

    /**
     * Handles the restart button click event.
     * Resets UI state and delegates game logic reset to GameController.
     */
    public void newGame(ActionEvent buttonEvent) {

        if (gameStateController.isGameOver()) {
        buttonClickSFX.playSFX();
        }
        
        isResume = false;
        timeLine.stop();
        backgroundMusic.stopBGM(); // Stop BGM when starting a new game 
        darkOverlay.setVisible(false);
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false);
        gameStateController.resetGameState();
        
        if (gameController != null) {
            gameController.createNewGame();
        }

        if (board != null) {
            refreshGameBoard(board.getBoardMatrix()); 
            refreshBrick(board.getViewData());
            startCountdown();
        }
        gamePanel.requestFocus();   
    }

    /**
     * Handles the exit button click event.
     * Exits the game.
     * @param buttonEvent the action event
     */
    public void exitGame(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        Platform.exit();
    }

    /**
     * Handles the ESC key press and pause button click event.
     * Pauses the game and shows the pause panel.
     * @param buttonEvent the action event
     */
    public void pauseGame(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        gameStateController.pauseGame();
        timeLine.stop();
        backgroundMusic.pauseBGM();
        darkOverlay.setVisible(true);
        pausePanel.setVisible(true);
        pauseScoreLabel.setText(scoreLabel.getText());
        gamePanel.requestFocus();
    }
    
    /**
     * Handles the resume button click event.
     * Resumes the game and hides the pause panel.
     * @param actionEvent the action event
     */
    public void resumeGame(ActionEvent actionEvent) {
        buttonClickSFX.playSFX();
        gameStateController.resumeGame();
        isResume = true;
        darkOverlay.setVisible(false);
        pausePanel.setVisible(false);
        
        startCountdown();
        gamePanel.requestFocus();
    }

    /**
     * Shows confirmation dialog for restart action.
     */
    public void confirmRestart(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        actionSelected = "restart";
        pausePanel.setVisible(false);
        confirmPanel.setVisible(true);
        confirmMessage.setText("Restart Game?");
        alertMessage.setText("Restart the game will reset your\n progress to level 1 and your current\nprogress will be lost. ");
        gamePanel.requestFocus();
    }

    /**
     * Shows confirmation dialog for back to menu action.
     */
    public void confirmBackToMenu(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        actionSelected = "backToMenu";
        pausePanel.setVisible(false);
        confirmPanel.setVisible(true);
        confirmMessage.setText("Return to Menu?");
        alertMessage.setText("Your current progress will be lost.");
        gamePanel.requestFocus();
    }

    /**
     * Handles confirmation Yes button - executes the pending action.
     */
    public void confirmYes(ActionEvent buttonEvent) throws Exception {
        buttonClickSFX.playSFX();
        confirmPanel.setVisible(false);
        pausePanel.setVisible(false);

        if (actionSelected.equals("restart")) {
            newGame(buttonEvent);

        } else if (actionSelected.equals("backToMenu")) {
            backToMenu(buttonEvent);
        }
        actionSelected = null;
    }

    /**
     * Handles confirmation No button - returns to pause panel.
     */
    public void confirmNo(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        confirmPanel.setVisible(false);
        pausePanel.setVisible(true);
        actionSelected = null;
        gamePanel.requestFocus();
    }

    public void showControlKeys(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        gameStateController.pauseGame();
        timeLine.stop();
        darkOverlay.setVisible(true);
        pausePanel.setVisible(false); // Hide pause panel when showing controls
        controlPanel.setVisible(true);
      
        gamePanel.requestFocus();
    }

    public void closeControlPanel(ActionEvent buttonEvent) {
        crossButtonSFX.playSFX();
        controlPanel.setVisible(false);
        gameStateController.pauseGame();
        timeLine.stop();
        darkOverlay.setVisible(true);
        pausePanel.setVisible(true);
        gamePanel.requestFocus();
    }

    /**
     * Sets the stage reference for scene switching.
     * @param stage the primary stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles the back to menu button click event.
     * Switches back to the main menu scene.
     * @param buttonEvent the action event
     */
    public void backToMenu(ActionEvent buttonEvent) throws Exception {

        if (gameStateController.isGameOver()) {
            buttonClickSFX.playSFX();
        }
       
        timeLine.stop();
        backgroundMusic.stopBGM();
            
        // Reset game state
        gameStateController.setGameState(GameStateController.GameState.MAIN_MENU);
            
        // Load the menu layout
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("menuLayout.fxml"));
        javafx.scene.Parent root = fxmlLoader.load();
            
        // Get the menu controller and set the stage
        com.comp2042.gui.MenuController menuController = fxmlLoader.getController();
        if (menuController != null && stage != null) {
             menuController.setStage(stage);
        }
            
        // Switch to menu scene
        if (stage != null) {
            javafx.scene.Scene menuScene = new javafx.scene.Scene(root, 800, 580);
            stage.setScene(menuScene);
        } 
    }
}