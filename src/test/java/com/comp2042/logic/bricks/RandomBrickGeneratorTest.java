package com.comp2042.logic.bricks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomBrickGeneratorTest {

    RandomBrickGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new RandomBrickGenerator();
    }

    @Test
    void getBrick() {
        Brick brick = generator.getBrick();
        assertNotNull(brick);
    }

    @Test
    void getNextBrick() {
        Brick next = generator.getNextBrick();
        assertNotNull(next);
        Brick pulled = generator.getBrick();
        assertSame(next, pulled);
    }

    @Test
    void resetBrickGenerator() {
        Brick previousNext = generator.getNextBrick();
        assertNotNull(previousNext);
        Brick pulled = generator.getBrick();
        assertSame(previousNext, pulled);
        
        generator.resetBrickGenerator();
        Brick newPulled = generator.getBrick();
        assertNotSame(previousNext, newPulled);
    }
}