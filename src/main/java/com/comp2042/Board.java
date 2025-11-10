package com.comp2042;

// Define what a game board can do before you implement it.
public interface Board {

    int softDrop();

    int hardDrop();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    int getGhostPieceY();

    ViewData getGhostPieceViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    GameScore getScore();

    void newGame();
}