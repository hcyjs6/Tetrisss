package com.comp2042;

import com.comp2042.displayNextBrick.NextBrickRenderer;
import com.comp2042.event.EventSource;
import com.comp2042.event.EventType;
import com.comp2042.event.MoveEvent;
import com.comp2042.event.InputEventListener;
import com.comp2042.ghostpieces.GhostPieceRenderer;
import com.comp2042.speed.DropSpeedController;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 28;

    @FXML
    private GridPane gamePanel;

    @FXML
    private StackPane groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane ghostPanel;

    @FXML
    private GridPane nextBrickPanel;

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

    @FXML
    private Button pauseButton;



    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Rectangle[][] ghostRectangles;

    private ViewData currentPieceData; // Store current piece data for ghost comparison

    private DropSpeedController dropSpeedController;
    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
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
                    
                }
              
            }
        });
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false); // Initially hide the pause panel
        controlPanel.setVisible(false); // Initially hide the control panel
        darkOverlay.setVisible(false); // Initially hide the dark overlay

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length]; // displayMatrix is the background of the game board, it is a 2D array tht store all grid rectangles
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
               
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE); // rectangle creates one square block of the pieces
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i);
                setRectangleData(boardMatrix[i][j], rectangle);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(BrickColour.getFillColor(brick.getBrickData()[i][j]));
                // Only add borders to non-empty blocks
                if (brick.getBrickData()[i][j] != 0) {
                    rectangle.setOpacity(1.0); // Ensure falling blocks are fully opaque and vibrant
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setStrokeWidth(0.8);
                    rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
                } else {
                    rectangle.setOpacity(0.0); // Make empty parts of falling block transparent
                    rectangle.setStroke(null);
                }
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gameBoard.getLayoutX() + 10 + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(gameBoard.getLayoutY() + 10 + brick.getyPosition() * BRICK_SIZE);

        NextBrickRenderer.initNextBrick(nextBrickPanel, brick.getNextBrickData());

        dropSpeedController = new DropSpeedController(() -> moveDown(new MoveEvent(EventType.SOFT_DROP, EventSource.THREAD)));
        timeLine = dropSpeedController.getTimeline();
        dropSpeedController.updateSpeed(1);
        
        currentPieceData = brick;

        if (ghostRectangles == null) {
            initGhostPiece();
        }
        if (eventListener != null) {
            refreshGhostPiece(eventListener.getGhostPieceData());
        }
    }

    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            currentPieceData = brick;
            
            brickPanel.setLayoutX(gameBoard.getLayoutX() + 10 + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(gameBoard.getLayoutY() + 10 + brick.getyPosition() * BRICK_SIZE);
            
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    rectangles[i][j].setFill(BrickColour.getFillColor(brick.getBrickData()[i][j]));
                    if (brick.getBrickData()[i][j] != 0) {
                        rectangles[i][j].setOpacity(1.0);
                        rectangles[i][j].setStroke(Color.BLACK);
                        rectangles[i][j].setStrokeWidth(0.8);
                        rectangles[i][j].setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
                    } else {
                        rectangles[i][j].setOpacity(0.0);
                        rectangles[i][j].setStroke(null);
                    }
                }
            }
            
            if (eventListener != null) {
                refreshGhostPiece(eventListener.getGhostPieceData());
            }

            NextBrickRenderer.initNextBrick(nextBrickPanel, brick.getNextBrickData());
        }
    }

    // Initialize the ghost piece
    private void initGhostPiece() {
        ghostRectangles = GhostPieceRenderer.initGhostPiece(ghostPanel, BRICK_SIZE);
    }
    
    // Refresh the ghost piece
    private void refreshGhostPiece(ViewData ghostData) {
        GhostPieceRenderer.refreshGhostPiece(ghostData, currentPieceData, ghostRectangles, ghostPanel, gameBoard, BRICK_SIZE);
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(BrickColour.getFillColor(color));
        

        // Use soft pink background for empty cells to create subtle contrast
        if (color == 0) {
            rectangle.setFill(Color.BLACK); // Misty Rose - soft pink for contrast
            rectangle.setOpacity(1);
            // Add borders to empty cells to create grid
            rectangle.setStroke(Color.GREY); // Light pink border
            rectangle.setStrokeWidth(0.2);
            rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        } else {
            rectangle.setOpacity(1.0);
            // Add borders to placed blocks
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(0.8);
            rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        }
    }


    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
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
                String notificationText = "Lines cleared: " + downData.getClearRow().getLinesRemoved() + " (+" + downData.getClearRow().getScoreBonus() + " points)";
                NotificationPanel notificationPanel = new NotificationPanel(notificationText);
                StackPane.setAlignment(notificationPanel, Pos.CENTER);
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString());
    }
    
    public void bindLevel(IntegerProperty integerProperty) {
        levelLabel.textProperty().bind(integerProperty.asString());
        if (dropSpeedController != null) {
            dropSpeedController.bindLevel(integerProperty);
        }
    }
  
    
    public void bindLinesCleared(IntegerProperty integerProperty) {
        linesLabel.textProperty().bind(integerProperty.asString());
    }
    
    public DropSpeedController getDropSpeedController() {
        return dropSpeedController;
    }

    public void gameOver() {
        timeLine.stop();
        darkOverlay.setVisible(true);
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);                                                  
        pauseButton.setDisable(true); // Disable button when game over
    }

    /**
     * Handles the restart button click event.
     * Resets UI state and delegates game logic reset to GameController.
     */
    public void newGame(ActionEvent buttonEvent) {
        timeLine.stop();
        darkOverlay.setVisible(false);
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false);
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        pauseButton.setDisable(false); // Re-enable button for new game
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
        timeLine.stop();
        isPause.setValue(Boolean.TRUE);
        darkOverlay.setVisible(true);
        pausePanel.setVisible(true);
        pauseButton.setDisable(true); // Disable button when paused
        gamePanel.requestFocus();
    }
    
    /**
     * Handles the resume button click event.
     * Resumes the game and hides the pause panel.
     * @param actionEvent the action event
     */
    public void resumeGame(ActionEvent actionEvent) {
        darkOverlay.setVisible(false);
        pausePanel.setVisible(false);
        isPause.setValue(Boolean.FALSE);
        pauseButton.setDisable(false); // Re-enable button when resuming
        timeLine.play();
        gamePanel.requestFocus();
    }

    public void showControlKeys(ActionEvent buttonEvent) {
        timeLine.stop();
        isPause.setValue(Boolean.TRUE);
        darkOverlay.setVisible(true);
        pausePanel.setVisible(false); // Hide pause panel when showing controls
        controlPanel.setVisible(true);
        pauseButton.setDisable(true); // Disable button when showing controls
          
        gamePanel.requestFocus();
    }

    public void closeControlPanel(ActionEvent buttonEvent) {
        controlPanel.setVisible(false);
        timeLine.stop();
        darkOverlay.setVisible(true);
        isPause.setValue(Boolean.TRUE);
        pausePanel.setVisible(true);
        gamePanel.requestFocus();
        pauseButton.setDisable(true);

    }
}