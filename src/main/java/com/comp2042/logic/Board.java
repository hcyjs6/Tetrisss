package com.comp2042.logic;

import com.comp2042.logic.rotation.NextShapeInfo;

/**
 * Defines the interface for the game board in the Tetris game.
 * This interface specifies all operations that can be performed on the game board, including brick movements, rotations, holds, and game state management.
 * 
 * @author Sek Joe Rin
 */
public interface Board {

    /**
     * Moves the current brick down one step.
     * 
     * @return the number of cells moved (1 if successful, 0 if blocked)
     */
    int softDrop();

    /**
     * Drops the current brick to the bottom instantly.
     * 
     * @return the number of cells the brick was dropped
     */
    int hardDrop();

    /**
     * Moves the current brick one cell to the left.
     * 
     * @return true if the move was successful, false if blocked
     */
    boolean moveBrickLeft();

    /**
     * Moves the current brick one cell to the right.
     * 
     * @return true if the move was successful, false if blocked
     */
    boolean moveBrickRight();

    /**
     * Rotates the current brick counter-clockwise.
     * 
     * @return true if the rotation was successful, false if blocked
     */
    boolean rotateLeftBrick();

    /**
     * Rotates the current brick clockwise.
     * 
     * @return true if the rotation was successful, false if blocked
     */
    boolean rotateRightBrick();

    /**
     * Attempts to rotate the brick with kick offsets if collision is detected.
     * 
     * @param nextShape the NextShapeInfo containing the rotated shape and position
     * @return true if the rotation was successful, false if blocked
     */
    boolean kickOffsets(NextShapeInfo nextShape);

    /**
     * Creates a new brick at the spawn position.
     * 
     * @return true if the brick was created successfully
     */
    boolean createNewBrick();

    /**
     * Gets the current state of the game board matrix.
     * 
     * @return a 2D array representing the current board state
     */
    int[][] getBoardMatrix();

    /**
     * Gets the view data containing all information needed for rendering.
     * 
     * @return ViewData containing current brick, next brick, hold brick, and ghost piece information
     */
    ViewData getViewData();

    /**
     * Calculates and returns the y coordinate where the ghost piece will land.
     * 
     * @return the y coordinate of the ghost piece landing position
     */
    int getGhostPieceY();

    /**
     * Gets the view data for the ghost piece preview.
     * 
     * @return ViewData containing the ghost piece information
     */
    ViewData getGhostPieceViewData();

    /**
     * Holds the current brick or swaps it with the previously held brick.
     */
    void holdBrick();

    /**
     * Returns the shape data of the currently held brick.
     * 
     * @return a 2D array representing the held brick shape, or null if no brick is held
     */
    int[][] getHoldBrickData();

    /**
     * Merges the current brick into the background board.
     */
    void mergeBrickToBackground();

    /**
     * Clears any full rows from the board and returns the result.
     * 
     * @return a ClearRow object containing information about cleared rows and the new board state
     */
    ClearRow clearRows();

    /**
     * Checks if the game is over by detecting collision at spawn position.
     * 
     * @return true if the game is over, false otherwise
     */
    boolean isGameOver();

    /**
     * Resets the board to a new game state.
     */
    void resetBoard();

}