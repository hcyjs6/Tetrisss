package com.comp2042;

import com.comp2042.displayNextBrick.NextBrickRenderer;
import com.comp2042.event.EventSource;
import com.comp2042.event.EventType;
import com.comp2042.event.MoveEvent;
import com.comp2042.event.InputEventListener;
import com.comp2042.ghostpieces.GhostPieceRenderer;
import com.comp2042.speed.DropSpeedController;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 28;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane ghostPanel;

    @FXML
    private GridPane nextBrickPanel;

    @FXML
    private BorderPane gameOverPanel;

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
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateLeftEvent(new MoveEvent(EventType.ROTATE_LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.E || keyEvent.getCode() == KeyCode.X) {
                        refreshBrick(eventListener.onRotateRightEvent(new MoveEvent(EventType.ROTATE_RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) { // Soft drop
                        // Soft drop: move down one step
                        moveDown(new MoveEvent(EventType.SOFT_DROP, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.SPACE) { // Hard drop
                        // Hard drop: drop to bottom instantly
                        moveDown(new MoveEvent(EventType.HARD_DROP, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);

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

    private void refreshBrick(ViewData brick) {
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
                String summary = "Lines cleared: " + downData.getClearRow().getLinesRemoved() + " (+" + downData.getClearRow().getScoreBonus() + " points)";
                NotificationPanel notificationPanel = new NotificationPanel(summary);
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

    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }
}