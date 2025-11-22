package com.comp2042.logic.event;

import com.comp2042.logic.DownData;
import com.comp2042.logic.ViewData;

public interface InputEventListener {

    DownData onSoftDropEvent(MoveEvent event);

    DownData onHardDropEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateLeftEvent(MoveEvent event);

    ViewData onRotateRightEvent(MoveEvent event);

    ViewData onHoldEvent(MoveEvent event);
}