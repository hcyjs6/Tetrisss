package com.comp2042.logic.event;

/**
 * Represents the type of move event that can occur in the game.
 * This enum defines all possible actions that can be performed on a Tetris piece.
 * 
 * @author Sek Joe Rin
 */
public enum EventType {
    SOFT_DROP,
    HARD_DROP,
    LEFT,
    RIGHT,
    ROTATE_LEFT,
    ROTATE_RIGHT,
    HOLD
}
