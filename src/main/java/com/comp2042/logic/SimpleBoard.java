package com.comp2042.logic;

import com.comp2042.logic.ghostpieces.GhostPieceLogic;
import com.comp2042.logic.rotation.BrickRotator;
import com.comp2042.logic.rotation.NextShapeInfo;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.logic.hold.HoldLogic;
import com.comp2042.audio.SoundEffect;

import java.awt.*;

/**
 * Represents the game board for the Tetris game.
 * This class manages the game matrix, brick placement, and basic game state.
 * 
 * @author Sek Joe Rin
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
    private final SoundEffect rotateSFX;
    private final SoundEffect hardDropSFX;

    /**
     * Creates a new SimpleBoard with the specified dimensions.
     * 
     * @param width the number of columns in the board
     * @param height the number of rows in the board
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        holdLogic = new HoldLogic(brickRotator, brickGenerator);
        rotateSFX = new SoundEffect("Audio/rotateSFX.wav");
        hardDropSFX = new SoundEffect("Audio/hardDropSFX.wav");
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

    /**
     * Moves the current brick down one step.
     * 
     * @return the number of cells moved (1 if successful, 0 if blocked)
     */
    @Override
    public int softDrop() {
        // Move down one cell at a time
        if (attemptMove(0, 1)) {
            return 1; // Return 1 if successful (moved 1 cell)
        } else {
            return 0; // Return 0 if blocked
        }
    }

    /**
     * Drops the current brick to the bottom instantly.
     * 
     * @return the number of cells the brick was dropped
     */
    @Override
    public int hardDrop() {

        hardDropSFX.playSFX();
        int dropDistance = 0;
        while (attemptMove(0, 1)) {  // Move down as far as possible in one move
            dropDistance++;
        }
        return dropDistance; // Return the actual distance dropped
    }

    /**
     * Moves the current brick one position to the left.
     * 
     * @return true if the move was successful, false if blocked
     */
    @Override
    public boolean moveBrickLeft() {
        return attemptMove(-1, 0);
    }

    /**
     * Moves the current brick one position to the right.
     * 
     * @return true if the move was successful, false if blocked
     */
    @Override
    public boolean moveBrickRight() {
        return attemptMove(1, 0);
    }

    /**
     * Rotates the current brick counter-clockwise.
     * 
     * @return true if the rotation was successful, false if blocked
     */
    @Override
    public boolean rotateLeftBrick() {
        NextShapeInfo nextShape = brickRotator.getAnticlockwiseNextShape();
        rotateSFX.playSFX();
        return kickOffsets(nextShape);
   
    }

    /**
     * Rotates the current brick clockwise.
     * 
     * @return true if the rotation was successful, false if blocked
     */
    @Override
    public boolean rotateRightBrick() {
        NextShapeInfo nextShape = brickRotator.getClockwiseNextShape();
        rotateSFX.playSFX();
        return kickOffsets(nextShape);
    }

    /**
     * Attempts to rotate the brick with kick offsets if collision is detected.
     * 
     * @param nextShape the NextShapeInfo containing the rotated shape and position
     * @return true if the rotation was successful, false if blocked
     */
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


    /**
     * Creates a new brick at the spawn position.
     * 
     * @return true if the brick was created successfully
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(SPAWN_X, SPAWN_Y);
        holdLogic.resetCanHold(); // Reset hold ability when a new brick is created
        return true; // Always return true - game over check will be handled separately
    }
    
    /**
     * Returns the current state of the game board matrix.
     * 
     * @return a 2D array representing the current board state
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }
   
    /**
     * Returns the view data containing all information needed for rendering.
     * 
     * @return ViewData containing current brick, next brick, hold brick, and ghost piece information
     */
    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0), getHoldBrickData(), getGhostPieceViewData().getGhostPieceData());
    }

    /**
     * Calculates and returns the y coordinate where the ghost piece will land.
     * 
     * @return the y coordinate of the ghost piece landing position
     */
    @Override
    public int getGhostPieceY() {
        return GhostPieceLogic.calculateGhostPieceY(currentGameMatrix, brickRotator.getCurrentShape(), currentOffset);
    }

    /**
     * Returns the view data for the ghost piece preview.
     * 
     * @return ViewData containing the ghost piece information
     */
    @Override
    public ViewData getGhostPieceViewData() {
        int ghostY = getGhostPieceY();
        return GhostPieceLogic.createGhostPieceViewData(brickRotator.getCurrentShape(), currentOffset, ghostY);
    }

    /**
     * Holds the current brick or swaps it with the previously held brick.
     */
    @Override
    public void holdBrick() {
        holdLogic.holdBrick(currentOffset, SPAWN_X, SPAWN_Y);
    }

    /**
     * Returns the shape data of the currently held brick.
     * 
     * @return a 2D array representing the held brick shape, or null if no brick is held
     */
    @Override
    public int[][] getHoldBrickData() {
        return holdLogic.getHoldBrickData();
    }
    
    /**
     * Merges the current brick into the background board.
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Clears any full rows from the board and returns the result.
     * 
     * @return a ClearRow object containing information about cleared rows and the new board state
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    /**
     * Checks if the game is over by detecting collision at spawn position.
     * 
     * @return true if the game is over, false otherwise
     */
    @Override
    public boolean isGameOver() {
        return CollisionDetector.checkSpawnCollision(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }
 
    /**
     * Resets the board to a new game state.
     */
    @Override
    public void resetBoard() {
        currentGameMatrix = new int[width][height];
        // Reset the brick generator to start fresh
        brickGenerator.resetBrickGenerator();
        holdLogic.reset(); // Reset hold logic
        
        createNewBrick();
    }


  
}