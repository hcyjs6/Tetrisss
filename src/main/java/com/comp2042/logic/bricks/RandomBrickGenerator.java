package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

// This class generates random Tetris pieces
public class RandomBrickGenerator implements BrickGenerator {

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

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

    @Override
    public Brick getBrick() {
       
        if (nextBricks.size() <= 7) {
            fillListOfBricks();
        }
        return nextBricks.poll();
    }

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