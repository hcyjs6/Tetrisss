package com.comp2042.rotation;

import com.comp2042.MatrixOperations;

public final class NextShapeInfo {

    private final int[][] shape; // the shape of the next rotation state
    private final int position; // the position of the next rotation state

    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    public int getPosition() {
        return position;
    }
}
