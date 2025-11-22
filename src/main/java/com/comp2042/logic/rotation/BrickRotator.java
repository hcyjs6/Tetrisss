package com.comp2042.logic.rotation;
import com.comp2042.logic.bricks.Brick;

public class BrickRotator {

    private Brick brick; // the current shape of the brick
    private int currentShape = 0; // index of the current rotation state

    public NextShapeInfo getClockwiseNextShape() { 
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape); // return the next rotation shape and index of the next rotation state
    }

    public NextShapeInfo getAnticlockwiseNextShape() {
        int size = brick.getShapeMatrix().size();
        int previousShape = currentShape;
        previousShape = (--previousShape + size) % size;
        return new NextShapeInfo(brick.getShapeMatrix().get(previousShape), previousShape);
    }

    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape); // return the current shape of the current rotation state
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape; // set the current shape index
    }

    public void setBrick(Brick brick) {
        this.brick = brick; // switch to a new brick
        currentShape = 0; // reset the current shape index to 0
    }

    public Brick getBrick() {
        return brick; // return the current brick
    }

}