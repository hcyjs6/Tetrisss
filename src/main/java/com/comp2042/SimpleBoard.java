package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.logic.CollisionDetector;

import java.awt.*;

/**
 * SimpleBoard class represents the game board for the Tetris game.
 * Manages the game matrix, brick placement, and basic game state.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class SimpleBoard implements Board {

    private final int width;    // columns of the board
    private final int height;    // rows of the board
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    // Centralized method to attempt a move.
    private boolean attemptMove(int x, int y) {
        boolean collide = CollisionDetector.canMoveBy(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset, x, y); // check if the brick can move by a specific amount
        if (collide) {
            return false; // return false if the brick cannot move by a specific amount
        }
        currentOffset.translate(x, y); // move the brick by a specific amount
        return true; // return true if the brick can move by a specific amount
    }

    @Override
    public boolean moveBrickDown() {
        return attemptMove(0, 1);
    }

    @Override
    public boolean moveBrickLeft() {
        return attemptMove(-1, 0);
    }

    @Override
    public boolean moveBrickRight() {
        return attemptMove(1, 0);
    }

    @Override
    public boolean rotateLeftBrick() {
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean blocked = CollisionDetector.checkRotateCollision(currentGameMatrix, nextShape.getShape(), currentOffset);
        if (blocked) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, -1); // Spawn at y=-1 so visible part appears at row 0
        return true; // Always return true - game over check will be handled separately
    }
    
    // Check if the current brick has reached the top of the board (game over condition)
    public boolean isGameOver() {
        // Game over when the brick cannot be placed at the spawn position due to collision
        return CollisionDetector.checkSpawnCollision(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY()); // return true if collision detected, false if can spawn
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    @Override
    public Score getScore() {
        return score;
    }


    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.resetScore();
        createNewBrick();
    }
}