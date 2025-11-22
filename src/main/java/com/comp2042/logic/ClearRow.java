package com.comp2042.logic;

import java.util.List;
import java.util.ArrayList;

public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int totalPointsAwarded;
    private final int totalComboBonus;
    private final List<Integer> clearedRowIndex;


    public ClearRow(int linesRemoved, int[][] newMatrix, int totalPointsAwarded, int totalComboBonus, List<Integer> clearedRowIndex) {
        this.linesRemoved = linesRemoved; // number of rows removed
        this.newMatrix = newMatrix; // new matrix after clearing rows
        this.totalPointsAwarded = totalPointsAwarded; // total points awarded
        this.totalComboBonus = totalComboBonus; // total combo bonus
        this.clearedRowIndex = new ArrayList<>(clearedRowIndex); // list of indices of rows removed
      
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    public int getTotalPointsAwarded() {
        return totalPointsAwarded;
    }

    public int getTotalComboBonus() {
        return totalComboBonus;
    }

    public List<Integer> getClearedRowIndex() {
        return new ArrayList<>(clearedRowIndex);
    }
}

