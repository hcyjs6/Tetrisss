package com.comp2042.logic;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents the result of clearing rows from the game board.
 * This class contains information about cleared rows, points awarded, combo bonus, and level progression.
 * 
 * @author Sek Joe Rin
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int totalPointsAwarded;
    private final int totalComboBonus;
    private final int combo;
    private final List<Integer> clearedRowIndex;
    private final boolean levelUp;


    /**
     * Creates a new ClearRow instance with the specified data.
     * 
     * @param linesRemoved the number of rows that were cleared
     * @param newMatrix the new board matrix after clearing rows
     * @param totalPointsAwarded the total points awarded for clearing the rows
     * @param combo the combo multiplier value
     * @param totalComboBonus the total combo bonus points
     * @param clearedRowIndex the list of row indices that were cleared
     * @param levelUp whether the level increased after clearing these rows
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int totalPointsAwarded, int combo, int totalComboBonus, List<Integer> clearedRowIndex, boolean levelUp) {
        this.linesRemoved = linesRemoved; // number of rows removed
        this.newMatrix = newMatrix; // new matrix after clearing rows
        this.totalPointsAwarded = totalPointsAwarded; // total points awarded
        this.combo = combo; // combo multiplier
        this.totalComboBonus = totalComboBonus; // total combo bonus
        this.clearedRowIndex = new ArrayList<>(clearedRowIndex); // list of indices of rows removed
        this.levelUp = levelUp; // whether level increased
    }

    /**
     * Gets the number of rows that were cleared in the last line clear.
     * 
     * @return the number of rows removed in the last line clear.
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets a copy of the new board matrix after clearing rows.
     * 
     * @return a copy of the new board matrix after clearing rows.
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the total points awarded in the last line clear.
     * 
     * @return the total points awarded in the last line clear.
     */
    public int getTotalPointsAwarded() {
        return totalPointsAwarded;
    }

    /**
     * Gets the combo multiplier value in the last line clear.
     * 
     * @return the combo multiplier value in the last line clear.
     */
    public int getCombo() {
        return combo; // combo multiplier
    }

    /**
     * Gets the total combo bonus points in the last line clear.
     * 
     * @return the total combo bonus points in the last line clear.
     */
    public int getTotalComboBonus() {
        return totalComboBonus;
    }

    /**
     * Gets a copy of the list of row indices that were cleared in the last line clear.
     * 
     * @return a copy of the list of row indices that were cleared in the last line clear.
     */
    public List<Integer> getClearedRowIndex() {
        return new ArrayList<>(clearedRowIndex);
    }

    /**
     * Returns whether the level increased after clearing these rows.
     * 
     * @return true if level increased, false otherwise
     */
    public boolean isLevelUp() {
        return levelUp;
    }
}

