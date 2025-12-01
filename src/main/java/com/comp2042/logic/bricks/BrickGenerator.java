package com.comp2042.logic.bricks;

/**
 * Defines the interface for generating Tetris bricks.
 * This interface provides methods to get the current brick, preview the next brick, and reset the generator.
 * 
 * @author Sek Joe Rin
 */
public interface BrickGenerator {

    /**
     * Gets the current brick.
     * 
     * @return the current brick
     */
    Brick getBrick();

    /**
     * Gets the next brick that will appear without removing it from the queue.
     * 
     * @return the next brick
     */
    Brick getNextBrick();

    /**
     * Resets the brick generator to its initial state by clearing the queue and filling it with the initial bricks.
     * 
     */
    void resetBrickGenerator();
}
