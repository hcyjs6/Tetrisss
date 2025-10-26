package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

// This is the MOST CRITICAL utility class. 
//Everything else depends on it for collision detection, matrix operations, and line clearing.
// It contains methods to check if a brick intersects with the board, to merge a brick with the board, and to check if a line is clear.

public class MatrixOperations {


    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

    public static boolean intersect(final int[][] matrix, final int[][] brick, int targetX, int targetY) {
        for (int i = 0; i < brick.length; i++) { // Loop through brick rows
            for (int j = 0; j < brick[i].length; j++) { // Loop through brick columns
                if (brick[i][j] != 0) { // If brick is not empty, check if it intersects with the board
                    int blockX = targetX + j; // Calculate the block x position (column)
                    int blockY = targetY + i; // Calculate the block y position (row)
                    if (checkOutOfBound(matrix, blockX, blockY) || matrix[blockY][blockX] != 0) {
                        return true; // COLLISION DETECTED: Return true if block out of bounds or target is not empty/occupied
                    }
                }
            }
        }
        return false; // Return false if the block is within the board and target is not empty/occupied
    }

    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        if (targetX < 0 || targetY < 0 || targetY >= matrix.length || targetX >= matrix[targetY].length) {
            return true;
        }
        return false;
    }

    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + j; // Calculate the target x position (column)
                int targetY = y + i; // Calculate the target y position (row)
                if (brick[i][j] != 0) {
                    copy[targetY][targetX] = brick[i][j];
                }
            }
        }
        return copy;
    }

    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}