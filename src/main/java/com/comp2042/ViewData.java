package com.comp2042;
// To tell the display "what to draw" and "where to draw it".
// This is a class that represents the data that needs to be displayed on the GUI

public final class ViewData {
 
    private final int[][] brickData; // represents the current brick on the board
    private final int xPosition; // represents the x position of the current brick
    private final int yPosition; // represents the y position of the current brick
    private final int[][] nextBrickData; // represents the next brick on the board

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