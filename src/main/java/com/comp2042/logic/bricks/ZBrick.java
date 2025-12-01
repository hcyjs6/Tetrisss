package com.comp2042.logic.bricks;

import com.comp2042.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Z-shaped Tetris brick.
 * This brick has 4 rotation states and uses color value 7.
 * 
 * @author Sek Joe Rin
 */
final class ZBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Creates a new Z-shaped brick with all its rotation states.
     */
    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 7, 0},
                {0, 7, 7, 0},
                {0, 7, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0}
        });

    }

    /**
     * Returns a deep copy of all rotation states of the Z-shaped brick.
     * 
     * @return a list of 2D integer arrays representing all rotation states of the brick
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
