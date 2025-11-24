package com.comp2042.logic;

import com.comp2042.logic.ghostpieces.GhostPieceLogic;
import com.comp2042.logic.rotation.BrickRotator;
import com.comp2042.logic.rotation.NextShapeInfo;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.logic.hold.HoldLogic;

import java.awt.*;

/**
 * SimpleBoard class represents the game board for the Tetris game.
 * Manages the game matrix, brick placement, and basic game state.
 * 
 * @author Sek Joe Rin
 * @version 1.0
 */
public class SimpleBoard implements Board {

    private static final int SPAWN_X = 3;
    private static final int SPAWN_Y = -1; // Spawn at y=-1 so visible part appears at row 0
    
    private final int width;    // columns of the board
    private final int height;    // rows of the board
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private final HoldLogic holdLogic;
    private int[][] currentGameMatrix;
    private Point currentOffset;

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        holdLogic = new HoldLogic(brickRotator, brickGenerator);
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
    public int softDrop() {
        // Move down one cell at a time
        if (attemptMove(0, 1)) {
            return 1; // Return 1 if successful (moved 1 cell)
        } else {
            return 0; // Return 0 if blocked
        }
    }

    @Override
    public int hardDrop() {
        int dropDistance = 0;
        while (attemptMove(0, 1)) {  // Move down as far as possible in one move
            dropDistance++;
        }
        return dropDistance; // Return the actual distance dropped
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
        NextShapeInfo nextShape = brickRotator.getAnticlockwiseNextShape();
        return kickOffsets(nextShape);
   
    }

    @Override
    public boolean rotateRightBrick() {
        NextShapeInfo nextShape = brickRotator.getClockwiseNextShape();
        return kickOffsets(nextShape);
    }

    // Kick offsets when collision detected
    @Override
    public boolean kickOffsets(NextShapeInfo nextShape) {
        boolean blocked = CollisionDetector.checkRotateCollision(currentGameMatrix, nextShape.getShape(), currentOffset);

        if (blocked) {
            int[][] kickOffsets = new int[][]{
                {1, 0},
                {-1, 0},
                {0, 1},
                {2, 0},
                {-2, 0}
            };

            for (int[] offset : kickOffsets) {
                int offsetX = offset[0];
                int offsetY = offset[1];
                int targetX = (int) currentOffset.getX() + offsetX;
                int targetY = (int) currentOffset.getY() + offsetY;
                boolean collision = CollisionDetector.checkCollision(currentGameMatrix, nextShape.getShape(), targetX, targetY);
                if (!collision) {
                    currentOffset.translate(offsetX, offsetY);
                    brickRotator.setCurrentShape(nextShape.getPosition());
                    return true;
                }
            }
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
        currentOffset = new Point(SPAWN_X, SPAWN_Y);
        holdLogic.resetCanHold(); // Reset hold ability when a new brick is created
        return true; // Always return true - game over check will be handled separately
    }
    
   
    @Override
    public boolean isGameOver() {
        return CollisionDetector.checkSpawnCollision(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0), getHoldBrickData(), getGhostPieceViewData().getGhostPieceData());
    }
    
    @Override
    public ViewData getGhostPieceViewData() {
        int ghostY = getGhostPieceY();
        return GhostPieceLogic.createGhostPieceViewData(brickRotator.getCurrentShape(), currentOffset, ghostY);
    }
    
    @Override
    public int getGhostPieceY() {
        return GhostPieceLogic.calculateGhostPieceY(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset);
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

    /** 
     * Resets the game board to a new game state.
     */
    @Override
    public void resetBoard() {
        currentGameMatrix = new int[width][height];
        // Reset the brick generator to start fresh
        brickGenerator.resetBrickGenerator();
        holdLogic.reset(); // Reset hold logic
        
        createNewBrick();
    }


    @Override
    public void holdBrick() {
        holdLogic.holdBrick(currentOffset, SPAWN_X, SPAWN_Y);
    }

    @Override
    public int[][] getHoldBrickData() {
        return holdLogic.getHoldBrickData();
    }
}