package com.comp2042.logic;

import com.comp2042.MatrixOperations;
import java.awt.Point;

//Handles all collision detection logic for the Tetris game.
//This class centralizes collision detection to improve maintainability.
public class CollisionDetector {
     
    // Checks if a brick can move to a specific position.
    public static boolean checkCollision(int[][] gameMatrix, int[][] brickShape, int targetX, int targetY) {
        return MatrixOperations.intersect(gameMatrix, brickShape, targetX, targetY); // return true if collision detected, false if move is safe
    }

    // Checks if a brick can move by a specific amount, including down, left and right.
    public static boolean canMoveBy(int[][] gameMatrix, int[][] brickShape, Point currentPosition, int x, int y) {
        int targetX = (int)currentPosition.getX() + x;
        int targetY = (int)currentPosition.getY() + y;
        return checkCollision(gameMatrix, brickShape, targetX, targetY); // return true if collision detected, false if move is safe
    }
         
    // Checks if a brick can rotate at its current position.
    public static boolean checkRotateCollision(int[][] gameMatrix, int[][] rotatedShape, Point currentPosition) {
        return checkCollision(gameMatrix, rotatedShape, (int)currentPosition.getX(), (int)currentPosition.getY());
    }
    
    // Checks if a new brick can be placed at the spawn position.
    public static boolean checkSpawnCollision(int[][] gameMatrix, int[][] brickShape, int spawnX, int spawnY) {
        return checkCollision(gameMatrix, brickShape, spawnX, spawnY);
    }
}
