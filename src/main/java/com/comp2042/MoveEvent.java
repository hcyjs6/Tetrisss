package com.comp2042;
// This is a class that represents an event/actions that occurs when a user or automatic timer moves the brick
// Package the event type(what happened) and event source (who caused the event) into a single object

public final class MoveEvent { // final class cannot be extended
    private final EventType eventType; // what happened - down, left, right, rotate, etc.
    private final EventSource eventSource; // who caused the event - user or automatic timer

    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventSource getEventSource() {
        return eventSource;
    }
}