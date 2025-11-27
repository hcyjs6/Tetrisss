package com.comp2042.logic;

import java.util.List;
import java.util.ArrayList;

public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int totalPointsAwarded;
    private final int totalComboBonus;
    private final int combo;
    private final List<Integer> clearedRowIndex;
    private final boolean levelUp;


    public ClearRow(int linesRemoved, int[][] newMatrix, int totalPointsAwarded, int combo, int totalComboBonus, List<Integer> clearedRowIndex, boolean levelUp) {
        this.linesRemoved = linesRemoved; // number of rows removed
        this.newMatrix = newMatrix; // new matrix after clearing rows
        this.totalPointsAwarded = totalPointsAwarded; // total points awarded
        this.combo = combo; // combo multiplier
        this.totalComboBonus = totalComboBonus; // total combo bonus
        this.clearedRowIndex = new ArrayList<>(clearedRowIndex); // list of indices of rows removed
        this.levelUp = levelUp; // whether level increased
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

    public int getCombo() {
        return combo; // combo multiplier
    }

    public int getTotalComboBonus() {
        return totalComboBonus;
    }

    public List<Integer> getClearedRowIndex() {
        return new ArrayList<>(clearedRowIndex);
    }

    public boolean isLevelUp() {
        return levelUp;
    }
}

