package com.comp2042.logic.event;

/**
 * Represents a move event that occurs during the game.
 * This class packages the event type and event source into a single object.
 * 
 * @author Sek Joe Rin
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Creates a new MoveEvent with the specified event type and source.
     * 
     * @param eventType the type of event that occurred (e.g., LEFT, RIGHT, ROTATE_LEFT)
     * @param eventSource the source that caused the event (USER or THREAD)
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Returns the type of event that occurred.
     * 
     * @return the EventType representing what action happened
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the source that caused the event.
     * 
     * @return the EventSource indicating who caused the event (USER or THREAD)
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}