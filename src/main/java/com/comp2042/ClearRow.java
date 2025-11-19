package com.comp2042;

import java.util.List;
import java.util.ArrayList;

public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int totalPointsAwarded;
    private final int totalComboBonus;
    private final List<Integer> clearedRowIndex;


    public ClearRow(int linesRemoved, int[][] newMatrix, int totalPointsAwarded, int totalComboBonus, List<Integer> clearedRowIndex) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.totalPointsAwarded = totalPointsAwarded;
        this.totalComboBonus = totalComboBonus;
        this.clearedRowIndex = new ArrayList<>(clearedRowIndex);
      
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

