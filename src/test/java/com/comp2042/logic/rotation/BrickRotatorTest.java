package com.comp2042.logic.rotation;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrickRotatorTest {

    private BrickRotator brickRotator;
    private BrickGenerator brickGenerator;

    @BeforeEach
    void setUp() {
        brickRotator = new BrickRotator();
        brickGenerator = new RandomBrickGenerator();
        Brick brick = brickGenerator.getBrick();
        brickRotator.setBrick(brick);
        brickRotator.setCurrentShape(0);
    }

    @Test
    void getClockwiseNextShape() {
        // Start at shape 0, next should be shape 1
        assertEquals(1, brickRotator.getClockwiseNextShape().getPosition());
        
        // Move to shape 1, next should be shape 2
        brickRotator.setCurrentShape(1);
        assertEquals(2, brickRotator.getClockwiseNextShape().getPosition());
        
        // Move to shape 3, next should wrap to shape 0
        brickRotator.setCurrentShape(3);
        assertEquals(0, brickRotator.getClockwiseNextShape().getPosition());
    }

    @Test
    void getAnticlockwiseNextShape() {
       
        assertEquals(3, brickRotator.getAnticlockwiseNextShape().getPosition());
      
        brickRotator.setCurrentShape(1);
        assertEquals(0, brickRotator.getAnticlockwiseNextShape().getPosition());
        
        brickRotator.setCurrentShape(3);
        assertEquals(2, brickRotator.getAnticlockwiseNextShape().getPosition());
    }

    @Test
    void getCurrentShape() {
        // return shape at index 0
        int[][] shape0 = brickRotator.getCurrentShape();
        assertNotNull(shape0);
        
        // change to shape 1 
        brickRotator.setCurrentShape(1);
        int[][] shape1 = brickRotator.getCurrentShape();
        assertNotNull(shape1);
        
        // verify they're different (different rotation states)
        assertNotSame(shape0, shape1);
    }

    @Test
    void setCurrentShape() {
        // Set to shape 2, verify by checking next clockwise shape is 3
        brickRotator.setCurrentShape(2);
        assertEquals(3, brickRotator.getClockwiseNextShape().getPosition());
        
        // Set to shape 3, verify next wraps to 0
        brickRotator.setCurrentShape(3);
        assertEquals(0, brickRotator.getClockwiseNextShape().getPosition());
    }

    @Test
    void setBrick() {
        // Set a new brick
        Brick newBrick = brickGenerator.getBrick();
        brickRotator.setBrick(newBrick);
        
        assertEquals(1, brickRotator.getClockwiseNextShape().getPosition());
        assertSame(newBrick, brickRotator.getBrick());
    }

    @Test
    void getBrick() {
        Brick brick = brickGenerator.getBrick();
        brickRotator.setBrick(brick);
        
        assertSame(brick, brickRotator.getBrick());
    }
}