package com.comp2042.logic;

/**
 * Represents the data that needs to be displayed on the GUI.
 * This class tells the display what to draw and where to draw it, including the current brick, next brick, hold brick, and ghost piece.
 * 
 * @author Sek Joe Rin
 */
public final class ViewData {
 
    private final int[][] brickData; // represents the current brick on the board
    private final int xPosition; // represents the x position of the current brick
    private final int yPosition; // represents the y position of the current brick
    private final int[][] nextBrickData; // represents the next brick on the board
    private final int[][] holdBrickData; // represents the held brick
    private final int[][] ghostPieceData; // represents the ghost piece

    /**
     * Creates a new ViewData instance with all brick information.
     * 
     * @param brickData the 2D array representing the current brick shape
     * @param xPosition the x coordinate of the current brick position
     * @param yPosition the y coordinate of the current brick position
     * @param nextBrickData the 2D array representing the next brick shape
     * @param holdBrickData the 2D array representing the held brick shape
     * @param ghostPieceData the 2D array representing the ghost piece shape
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int[][] holdBrickData, int[][] ghostPieceData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.holdBrickData = holdBrickData;
        this.ghostPieceData = ghostPieceData;
    }

    /**
     * Creates a new ViewData instance with only current brick information.
     * 
     * @param brickData the 2D array representing the current brick shape
     * @param xPosition the x coordinate of the current brick position
     * @param yPosition the y coordinate of the current brick position
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition) {
        this(brickData, xPosition, yPosition, null, null, null);
    }

    /**
     * Returns a copy of the current brick shape data.
     * 
     * @return a 2D array representing the current brick shape
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Returns a copy of the ghost piece shape data.
     * 
     * @return a 2D array representing the ghost piece shape
     */
    public int[][] getGhostPieceData() {
        return MatrixOperations.copy(ghostPieceData);
    }

    /**
     * Returns the x coordinate of the current brick position.
     * 
     * @return the x position of the brick
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Returns the y coordinate of the current brick position.
     * 
     * @return the y position of the brick
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Returns a copy of the next brick shape data.
     * 
     * @return a 2D array representing the next brick shape
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Returns a copy of the held brick shape data.
     * 
     * @return a 2D array representing the held brick shape, or null if no brick is held
     */
    public int[][] getHoldBrickData() {

        if (holdBrickData == null) {
            return null;
        }
        
        return MatrixOperations.copy(holdBrickData);
    }
}