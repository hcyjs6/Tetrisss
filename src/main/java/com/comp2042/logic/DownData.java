package com.comp2042.logic;

// DownData class track what happens when the brick is moved down
public final class DownData {
    private final ClearRow clearRow; 
    private final ViewData viewData;

    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    public ClearRow getClearRow() {
        return clearRow;
    }

    public ViewData getViewData() {
        return viewData;
    }
}
