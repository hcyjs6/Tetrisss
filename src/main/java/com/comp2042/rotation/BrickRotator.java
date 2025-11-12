package com.comp2042.rotation;

import com.comp2042.logic.bricks.Brick;

// You need to handle brick rotation before implementing the full board.
// This class handles the rotation of the brick.
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    public NextShapeInfo getClockwiseNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    public NextShapeInfo getAnticlockwiseNextShape() {
        int size = brick.getShapeMatrix().size();
        int previousShape = currentShape;
        previousShape = (--previousShape + size) % size;
        return new NextShapeInfo(brick.getShapeMatrix().get(previousShape), previousShape);
    }

    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }


}