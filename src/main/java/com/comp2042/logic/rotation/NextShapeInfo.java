package com.comp2042.logic.rotation;

import com.comp2042.logic.MatrixOperations;

/**
 * Contains information about the next rotation state of a brick.
 * This class holds both the shape matrix and the index of the next rotation state.
 * 
 * @author Sek Joe Rin
 */
public final class NextShapeInfo {

    private final int[][] shape; // the shape of the next rotation state
    private final int position; // the position of the next rotation state

    /**
     * Creates a new NextShapeInfo with the specified shape and index.
     * 
     * @param shape the 2D array representing the shape of the next rotation state 
     * @param position the index of the next rotation state
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Returns a copy of the shape matrix for the next rotation state.
     * 
     * @return a 2D array representing the shape of the next rotation state
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Returns the index of the next rotation state.
     * 
     * @return the index of the next rotation state
     */
    public int getPosition() {
        return position;
    }
}
