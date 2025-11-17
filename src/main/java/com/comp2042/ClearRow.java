package com.comp2042;

public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int totalPointsAwarded;
    private final int totalComboBonus;

    public ClearRow(int linesRemoved, int[][] newMatrix, int totalPointsAwarded, int totalComboBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.totalPointsAwarded = totalPointsAwarded;
        this.totalComboBonus = totalComboBonus;
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
}

