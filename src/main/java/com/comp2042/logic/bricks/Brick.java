package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Defines the interface for Tetris brick pieces.
 * This interface represents a Tetris piece with its different rotation states.
 * 
 * @author Sek Joe Rin
 */
public interface Brick {

    /**
     * Returns a list of 2D arrays representing all rotation states of the brick.
     * 
     * @return a list of 2D integer arrays, where each array represents one rotation state of the brick
     */
    List<int[][]> getShapeMatrix();
}
