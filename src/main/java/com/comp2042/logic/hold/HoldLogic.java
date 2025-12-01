package com.comp2042.logic.hold;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.rotation.BrickRotator;
import com.comp2042.audio.SoundEffect;

import java.awt.Point;

/**
 * Handles the hold feature for Tetris pieces.
 * This class manages storing and swapping the current piece with a held piece.
 * Players can only hold once per piece that lands.
 * 
 * @author Sek Joe Rin
 */
public class HoldLogic {
    
    private Brick heldBrick; // The brick currently being held
    private boolean canHold; // Whether we can hold the current piece (can only hold once per piece)
    private final BrickRotator brickRotator;
    private final BrickGenerator brickGenerator;
    private final SoundEffect canholdSFX;
   
    
    /**
     * Creates a new HoldLogic instance.
     * 
     * @param brickRotator the BrickRotator used to get and set the current brick
     * @param brickGenerator the BrickGenerator used to generate new bricks
     */
    public HoldLogic(BrickRotator brickRotator, BrickGenerator brickGenerator) {
        this.heldBrick = null;
        this.canHold = true;
        this.brickRotator = brickRotator;
        this.brickGenerator = brickGenerator;
        this.canholdSFX = new SoundEffect("Audio/canHoldSFX.wav");
   
    }

    /**
     * Holds the current brick or swaps it with the previously held brick.
     * If no brick is held, stores the current brick and creates a new one.
     * If a brick is already held, swaps the current brick with the held brick.
     * Can only hold once per piece that lands.
     * 
     * @param currentOffset the current position of the brick on the board
     * @param spawnX the x coordinate of the spawn position
     * @param spawnY the y coordinate of the spawn position
     */
    public void holdBrick(Point currentOffset, int spawnX, int spawnY) {

        // Get the current brick
        Brick currentBrick = brickRotator.getBrick();

        if (!canHold) {
            return;
        }

        if (heldBrick == null) {
            // No held brick yet, just store the current one and get a new brick
       
            heldBrick = currentBrick;
            Brick newBrick = brickGenerator.getBrick();
            brickRotator.setBrick(newBrick);
            currentOffset.setLocation(spawnX, spawnY);
            canholdSFX.playSFX();
        } else {
            // Swap the held brick with the current brick
        
            Brick temp = heldBrick;
            heldBrick = currentBrick;
            brickRotator.setBrick(temp);
            currentOffset.setLocation(spawnX, spawnY);
            canholdSFX.playSFX();
        }

        canHold = false;
    }

    /**
     * Returns the shape data of the currently held brick.
     * 
     * @return a 2D array representing the held brick shape, or null if no brick is held
     */
    public int[][] getHoldBrickData() {
        if (heldBrick == null) {
            return null;
        }
        return heldBrick.getShapeMatrix().get(0);
    }

    /**
     * Resets the hold ability.
     * This is called when a new piece is created.
     */
    public void resetCanHold() {
        this.canHold = true;
    }

    /**
     * Resets both the held brick and the hold ability.
     * This is called when starting a new game.
     */
    public void reset() {
        this.heldBrick = null;
        this.canHold = true;
    }
}