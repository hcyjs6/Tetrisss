package com.comp2042.gui;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

    private Rectangle[][] displayMatrix;
    private ClearRowEffect clearRowEffect;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Rectangle[][] ghostRectangles;

    private ViewData currentPieceData; // Store current piece data for ghost comparison

    private DropSpeedController dropSpeedController;
    private Timeline timeLine;
    private boolean isResume = false;
    private boolean isCountDownEnd;
    private Countdown countdown;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
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

    // ✅ Initialize the game view from the GAMECONTROLLER
    public void initGameView(int[][] boardMatrix, ViewData brick) {

        // Display the game board background with all grid rectangles
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length]; // displayMatrix is the background of the game board, it is a 2D array tht store all grid rectangles
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
               
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE); // rectangle creates one square block of the pieces
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i);
                setRectangleData(boardMatrix[i][j], rectangle);
            }
        }

        clearRowEffect = new ClearRowEffect(displayMatrix, groupNotification, this);

        // Display the current brick with all grid rectangles
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                styleRectangle(rectangle, brick.getBrickData()[i][j]);
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gameBoard.getLayoutX() + 10 + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(gameBoard.getLayoutY() + 10 + brick.getyPosition() * BRICK_SIZE);

        // Display the next brick with all grid rectangles
        NextBrickRenderer.initNextBrick(nextBrickPanel, brick.getNextBrickData());

        // Display the hold brick (if any)
        HoldBrickRenderer.initHoldBrick(holdBrickPanel, brick.getHoldBrickData());

        // Initialize the drop speed controller
        dropSpeedController = new DropSpeedController(() -> moveDown(new MoveEvent(EventType.SOFT_DROP, EventSource.THREAD)));
        timeLine = dropSpeedController.getTimeline();
        dropSpeedController.updateSpeed(1);
        
        currentPieceData = brick;

        // Display the ghost piece with all grid rectangles
        if (ghostRectangles == null) {
            initGhostPiece();
        }

        // Refresh the ghost piece
        refreshGhostPiece();

        // Display the countdown
        startCountdown();
    }

    // ✅ Refresh the brick display when the brick moves 
    public void refreshBrick(ViewData brick) {
        if (!gameStateController.isPaused() && !gameStateController.isGameOver() && gameStateController.isPlaying()) {
            currentPieceData = brick;
            
            brickPanel.setLayoutX(gameBoard.getLayoutX() + 10 + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(gameBoard.getLayoutY() + 10 + brick.getyPosition() * BRICK_SIZE);
            
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    rectangles[i][j].setFill(BrickColour.getFillColor(brick.getBrickData()[i][j]));
                    styleRectangle(rectangles[i][j], brick.getBrickData()[i][j]);
                }
            }
            
            // Refresh ghost piece after current piece moves
            refreshGhostPiece();

            NextBrickRenderer.initNextBrick(nextBrickPanel, brick.getNextBrickData());
            
            // Refresh hold brick display
            HoldBrickRenderer.initHoldBrick(holdBrickPanel, brick.getHoldBrickData());
        }
    }

    

    public void startCountdown() {
        if (timeLine != null) {
            timeLine.stop();
        }

        isCountDownEnd = false;
        countdown.start(isResume);
      
    }

    private void isCountdownFinished() {
        isCountDownEnd = true;
        isResume = false;

        
        timeLine.play();
        

        gamePanel.requestFocus();
    }
    

    // Initialize the ghost piece
    private void initGhostPiece() {
        ghostRectangles = GhostPieceRenderer.initGhostPiece(ghostPanel, BRICK_SIZE);
    }
    
    /**
     * Updates the ghost piece display to show where the current piece will land.
     * This method should be called whenever the current piece moves or rotates.
     */
    private void refreshGhostPiece() {
        if (gameController == null || currentPieceData == null || ghostRectangles == null) {
            return;
        }
        
        ViewData ghostData = gameController.getGhostPieceData();
        if (ghostData != null) {
            GhostPieceRenderer.refreshGhostPiece(ghostData, currentPieceData, ghostRectangles, ghostPanel, gameBoard, BRICK_SIZE);
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

   
    private void setRectangleData(int color, Rectangle rectangle) {
        
        if (color == 0) {
            rectangle.setFill(Color.BLACK); // Empty cells: black background
            rectangle.setOpacity(1);
            rectangle.setStroke(Color.GREY); // Grey grid border
            rectangle.setStrokeWidth(0.2);
            rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        } else {
            rectangle.setFill(BrickColour.getFillColor(color));
            rectangle.setOpacity(1.0);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(0.8);
            rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        }
    }
    
    private void styleRectangle(Rectangle rectangle, int colorValue) {

        rectangle.setFill(BrickColour.getFillColor(colorValue));
        
        if (colorValue != 0) {
            rectangle.setOpacity(1.0);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(0.8);
            rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        } else {
            rectangle.setStroke(null);
        }
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

    // ✅ Bind the score to the score label from the GAMECONTROLLER
    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString());
    }

    // ✅ Bind the level to the level label from the GAMECONTROLLER
    public void bindLevel(IntegerProperty integerProperty) {
        levelLabel.textProperty().bind(integerProperty.asString());
        if (dropSpeedController != null) {
            dropSpeedController.bindLevel(integerProperty);
        }
    }
  
    // ✅ Bind the lines cleared to the lines label from the GAMECONTROLLER
    public void bindLinesCleared(IntegerProperty integerProperty) {
        linesLabel.textProperty().bind(integerProperty.asString());
    }
    
    public DropSpeedController getDropSpeedController() {
        return dropSpeedController;
    }

    public void gameOver() {
       
        gameStateController.setGameState(GameStateController.GameState.GAME_OVER);
        timeLine.stop();
        darkOverlay.setVisible(true);
        gameOverPanel.setVisible(true);
    }

    /**
     * Handles the restart button click event.
     * Resets UI state and delegates game logic reset to GameController.
     */
    public void newGame(ActionEvent buttonEvent) {
        
        isResume = false;
        timeLine.stop();
        darkOverlay.setVisible(false);
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false);
        gameStateController.resetGameState();
        

        if (gameController != null) {
            gameController.createNewGame();
        }

        if (board != null) {
            refreshGameBackground(board.getBoardMatrix()); 
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
        
        Platform.exit();
    }

    /**
     * Handles the ESC key press and pause button click event.
     * Pauses the game and shows the pause panel.
     * @param buttonEvent the action event
     */
    public void pauseGame(ActionEvent buttonEvent) {
       
        gameStateController.pauseGame();
        timeLine.stop();
        darkOverlay.setVisible(true);
        pausePanel.setVisible(true);
        gamePanel.requestFocus();
    }
    
    /**
     * Handles the resume button click event.
     * Resumes the game and hides the pause panel.
     * @param actionEvent the action event
     */
    public void resumeGame(ActionEvent actionEvent) {
   
        gameStateController.resumeGame();
        isResume = true;
        darkOverlay.setVisible(false);
        pausePanel.setVisible(false);
        startCountdown();
        gamePanel.requestFocus();
    }

    public void showControlKeys(ActionEvent buttonEvent) {
    
        gameStateController.pauseGame();
        timeLine.stop();
        darkOverlay.setVisible(true);
        pausePanel.setVisible(false); // Hide pause panel when showing controls
        controlPanel.setVisible(true);
      
        gamePanel.requestFocus();
    }

    public void closeControlPanel(ActionEvent buttonEvent) {
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
       
         
            timeLine.stop();
            
            
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