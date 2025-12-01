package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Generates random Tetris pieces using the bag system.
 * This class ensures all 7 brick types appear before any type repeats.
 * 
 * @author Sek Joe Rin
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Creates a new RandomBrickGenerator and initializes it with two bags of shuffled bricks.
     */
    public RandomBrickGenerator() {
        fillListOfBricks();
        fillListOfBricks(); // Fill two bags initially for better preview
    }

    /**
     * Creates a new shuffled bag of all 7 brick types and adds them to the queue.
     */
    private void fillListOfBricks() {
        List<Brick> listOfBricks = new ArrayList<>();
        listOfBricks.add(new IBrick());
        listOfBricks.add(new JBrick());
        listOfBricks.add(new LBrick());
        listOfBricks.add(new OBrick());
        listOfBricks.add(new SBrick());
        listOfBricks.add(new TBrick());
        listOfBricks.add(new ZBrick());
        Collections.shuffle(listOfBricks);
        nextBricks.addAll(listOfBricks);
    }

    /**
     * This method returns the current brick and advances to the next brick.
     * Automatically refills the queue when it has 7 or fewer bricks remaining.
     * 
     * @return the current brick
     */
    @Override
    public Brick getBrick() {
       
        if (nextBricks.size() <= 7) {
            fillListOfBricks();
        }
        return nextBricks.poll();
    }

    /**
     * This method returns the next brick that will appear without removing it from the queue.
     * 
     * @return the next brick
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
    
    /**
     * Resets the generator by clearing the queue and reinitializing it with new random bricks.
     */
    @Override
    public void resetBrickGenerator() {
        nextBricks.clear();
        fillListOfBricks();
        fillListOfBricks();
    }
}