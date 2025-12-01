package com.comp2042.logic.event;

import com.comp2042.logic.DownData;
import com.comp2042.logic.ViewData;

/**
 * Defines the interface for handling input events in the Tetris game.
 * This interface provides methods to handle all types of brick movements and actions.
 * 
 * @author Sek Joe Rin
 */
public interface InputEventListener {

    /**
     * Handles the soft drop event where the piece moves down one step.
     * 
     * @param event the move event containing information about the soft drop action
     * @return DownData containing information about the result (cleared rows if landed, or just view data)
     */
    DownData onSoftDropEvent(MoveEvent event);

     /**
     * Handles the hard drop event where the piece drops to the bottom instantly.
     * 
     * @param event the move event containing information about the hard drop action
     * @return DownData containing information about cleared rows and the new view data
     */
    DownData onHardDropEvent(MoveEvent event);

    /**
     * Handles the left movement event where the piece moves one position to the left.
     * 
     * @param event the move event containing information about the left movement action
     * @return updated view data after the left movement action of the brick
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles the right movement event where the piece moves one position to the right.
     * 
     * @param event the move event containing information about the right movement action
     * @return updated view data after the right movement action of the brick
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles the rotation event where the piece rotates anti-clockwise (left).
     * 
     * @param event the move event containing information about the rotation action
     * @return updated view data after the left rotation action of the brick
     */
    ViewData onRotateLeftEvent(MoveEvent event);

    /**
     * Handles the rotation event where the piece rotates clockwise (right).
     * 
     * @param event the move event containing information about the rotation action
     * @return updated view data after the right rotation action of the brick
     */
    ViewData onRotateRightEvent(MoveEvent event);

    /**
     * Handles the hold event where the current piece is stored or swapped with a previously held piece.
     * 
     * @param event the move event containing information about the hold action
     * @return updated view data after the hold action of the brick
     */
    ViewData onHoldEvent(MoveEvent event);
}