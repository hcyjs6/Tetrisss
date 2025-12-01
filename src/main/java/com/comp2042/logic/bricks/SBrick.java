package com.comp2042.logic.bricks;

import com.comp2042.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the S-shaped Tetris brick.
 * This brick has 4 rotation states and uses color value 5.
 * 
 * @author Sek Joe Rin
 */
final class SBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Creates a new S-shaped brick with all its rotation states.
     */
    public SBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 0, 0},
                {0, 5, 5, 0},
                {0, 0, 5, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0}
        });
    }

    /**
     * Returns a deep copy of all rotation states of the S-shaped brick.
     * 
     * @return a list of 2D integer arrays representing all rotation states of the brick
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
