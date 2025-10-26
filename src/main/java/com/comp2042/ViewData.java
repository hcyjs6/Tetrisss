package com.comp2042;
// To tell the display "what to draw" and "where to draw it".
// This is a class that represents the data that needs to be displayed on the GUI
// The data includes the brick data, the x position, the y position, and the next brick data
// The brick data is the data that represents the current brick on the board
// The x position is the x position of the current brick
// The y position is the y position of the current brick
// The next brick data is the data that represents the next brick on the board
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;

    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}