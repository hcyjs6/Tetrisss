package com.comp2042.logic;

import java.awt.Point;

/**
 * Handles all collision detection logic for the Tetris game.
 * This class is the central point for all collision detection logic in the game board.
 * 
 * @author Sek Joe Rin
 */
public class CollisionDetector {
     
    /**
     * Checks if a brick can move to a specific position without collision.
     * 
     * @param gameMatrix the current state of the game board
     * @param brickShape the 2D array representing the brick shape
     * @param targetX the target x coordinate
     * @param targetY the target y coordinate
     * @return true if collision detected, false if move is safe
     */
    public static boolean checkCollision(int[][] gameMatrix, int[][] brickShape, int targetX, int targetY) {
        return MatrixOperations.intersect(gameMatrix, brickShape, targetX, targetY); // return true if collision detected, false if move is safe
    }

    /**
     * Checks if a brick can move by a specific amount in x and y directions.
     * 
     * @param gameMatrix the current state of the game board
     * @param brickShape the 2D array representing the brick shape
     * @param currentPosition the current position of the brick
     * @param x the amount to move in the x direction
     * @param y the amount to move in the y direction
     * @return true if collision detected, false if move is safe
     */
    public static boolean canMoveBy(int[][] gameMatrix, int[][] brickShape, Point currentPosition, int x, int y) {
        int targetX = (int)currentPosition.getX() + x;
        int targetY = (int)currentPosition.getY() + y;
        return checkCollision(gameMatrix, brickShape, targetX, targetY); // return true if collision detected, false if move is safe
    }
         
    /**
     * Checks if a brick can rotate at its current position without collision.
     * 
     * @param gameMatrix the current state of the game board
     * @param rotatedShape the 2D array representing the rotated brick shape
     * @param currentPosition the current position of the brick
     * @return true if collision detected, false if rotation is safe
     */
    public static boolean checkRotateCollision(int[][] gameMatrix, int[][] rotatedShape, Point currentPosition) {
        return checkCollision(gameMatrix, rotatedShape, (int)currentPosition.getX(), (int)currentPosition.getY());
    }
    
    /**
     * Checks if a new brick can be placed at the spawn position without collision.
     * 
     * @param gameMatrix the current state of the game board
     * @param brickShape the 2D array representing the brick shape
     * @param spawnX the x coordinate of the spawn position
     * @param spawnY the y coordinate of the spawn position
     * @return true if collision detected, false if spawn is safe
     */
    public static boolean checkSpawnCollision(int[][] gameMatrix, int[][] brickShape, int spawnX, int spawnY) {
        return checkCollision(gameMatrix, brickShape, spawnX, spawnY);
    }
}
