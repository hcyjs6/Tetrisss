package com.comp2042.logic;

/**
 * Tracks what happens when a brick is moved down.
 * This class contains information about cleared rows and updated view data after a drop action.
 * 
 * @author Sek Joe Rin
 */
public final class DownData {
    private final ClearRow clearRow; 
    private final ViewData viewData;

    /**
     * Creates a new DownData instance with the specified clear row and view data.
     * 
     * @param clearRow the ClearRow data if rows were cleared, or null if no rows were cleared
     * @param viewData the ViewData containing updated brick position and shape information
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Gets the ClearRow data if rows were cleared.
     * 
     * @return the ClearRow instance if rows were cleared, or null if no rows were cleared
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the updated view data after the drop action.
     * 
     * @return the ViewData containing updated brick position and shape information
     */
    public ViewData getViewData() {
        return viewData;
    }
}
