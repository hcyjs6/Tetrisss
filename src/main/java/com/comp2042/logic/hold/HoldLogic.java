package com.comp2042.logic.hold;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.rotation.BrickRotator;

import java.awt.Point;

public class HoldLogic {
    
    private Brick heldBrick; // The brick currently being held
    private boolean canHold; // Whether we can hold the current piece (can only hold once per piece)
    private final BrickRotator brickRotator;
    private final BrickGenerator brickGenerator;

    
    public HoldLogic(BrickRotator brickRotator, BrickGenerator brickGenerator) {
        this.heldBrick = null;
        this.canHold = true;
        this.brickRotator = brickRotator;
        this.brickGenerator = brickGenerator;
    }

    // Check if the hold ability is available
    public void holdBrick(Point currentOffset, int spawnX, int spawnY) {
        if (!canHold) {
            return;
        }
        // Get the current brick
        Brick currentBrick = brickRotator.getBrick();
     
        
        if (heldBrick == null) {
            // No held brick yet, just store the current one and get a new brick
            heldBrick = currentBrick;
            Brick newBrick = brickGenerator.getBrick();
            brickRotator.setBrick(newBrick);
            currentOffset.setLocation(spawnX, spawnY);
        } else {
            // Swap the held brick with the current brick
            Brick temp = heldBrick;
            heldBrick = currentBrick;
            brickRotator.setBrick(temp);
            currentOffset.setLocation(spawnX, spawnY);
        }

        canHold = false;
    }

    // Get the data of the held brick
    public int[][] getHoldBrickData() {
        if (heldBrick == null) {
            return null;
        }
        return heldBrick.getShapeMatrix().get(0);
    }

    // Reset the hold ability
    public void resetCanHold() {
        this.canHold = true;
    }

    // Reset the held brick and the hold ability
    public void reset() {
        this.heldBrick = null;
        this.canHold = true;
    }
}