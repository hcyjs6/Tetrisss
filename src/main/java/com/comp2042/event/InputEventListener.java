package com.comp2042.event;

import com.comp2042.DownData;
import com.comp2042.ViewData;

public interface InputEventListener {

    DownData onSoftDropEvent(MoveEvent event);

    DownData onHardDropEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateLeftEvent(MoveEvent event);

    ViewData onRotateRightEvent(MoveEvent event);

    ViewData getGhostPieceData();

    void createNewGame();
}