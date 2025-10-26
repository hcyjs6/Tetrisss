package com.comp2042;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(20, 10);  // Create a new board with 20 rows and 10 columns

    private final GuiController viewGuiController;  // Create a new GUI controller

    // This is the "constructor" - it runs when someone creates a new GameController
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick(); // tell the board to create the first falling brick
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {   // if the clear row is greater than 0 (lines removed), add the score bonus
                board.getScore().add(clearRow.getScoreBonus());
            }

            board.createNewBrick(); // Create new brick (always succeeds without checking for collision)
            
            // Check for game over if the new brick has reached the top of the board
            if (((SimpleBoard) board).isGameOver()) {
                viewGuiController.gameOver();   // tell the GUI controller to show the game over screen
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix()); // Update the display with the new board matrix to show the new state of the board

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}