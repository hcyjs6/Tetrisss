package com.comp2042.logic.rotation;
import com.comp2042.logic.bricks.Brick;

/**
 * This class handles the rotation of the brick.
 * 
 * @author Sek Joe Rin
 */
public class BrickRotator {

    private Brick brick; // the current shape of the brick
    private int currentShape = 0; // index of the current rotation state

    /**
     * Returns the next shape when rotating clockwise.
     * 
     * @return NextShapeInfo containing the next rotation shape and its position index
     */
    public NextShapeInfo getClockwiseNextShape() { 
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape); // return the next rotation shape and index of the next rotation state
    }

    /**
     * Returns the next shape when rotating counter-clockwise.
     * 
     * @return NextShapeInfo containing the next rotation shape and its position index
     */
    public NextShapeInfo getAnticlockwiseNextShape() {
        int size = brick.getShapeMatrix().size();
        int previousShape = currentShape;
        previousShape = (--previousShape + size) % size;
        return new NextShapeInfo(brick.getShapeMatrix().get(previousShape), previousShape);
    }

    /**
     * Returns the current shape of the brick in its current rotation state.
     * 
     * @return a 2D array representing the current rotation state of the brick
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape); // return the current shape of the current rotation state
    }

    /**
     * Sets the current rotation state index.
     * 
     * @param currentShape the index of the rotation state to be set
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape; // set the current shape index
    }

    /**
     * Sets a new brick and resets the rotation state to the initial index.
     * 
     * @param brick the new brick to be set
     */
    public void setBrick(Brick brick) {
        this.brick = brick; // switch to a new brick
        currentShape = 0; // reset the current shape index to 0
    }

    /**
     * Returns the current brick.
     * 
     * @return the current brick instance
     */
    public Brick getBrick() {
        return brick; // return the current brick
    }

}