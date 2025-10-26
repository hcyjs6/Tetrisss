package com.comp2042.logic.bricks;

import java.util.List;

// This class defines what a Tetris piece is before you can create specific pieces
public interface Brick {

    List<int[][]> getShapeMatrix();
}
