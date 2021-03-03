package com.devsegal.jserve;

import com.devsegal.jserve.middleware.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventHandlerRegistry {
    HashMap<String, List<EventHandler>> eventTypeToEventHandlers = new HashMap<>();

    public EventHandlerRegistry() {

    }

    public void registerEventHandler(String eventType, EventHandler eventHandler) {

        // If we already have an event handler of this type
        if (eventTypeToEventHandlers.containsKey(eventType)) {
            eventTypeToEventHandlers.get(eventType).add(eventHandler);
        } else {
            ArrayList<EventHandler> eventHandlers = new ArrayList<>();
            eventHandlers.add(eventHandler);
            eventTypeToEventHandlers.put(eventType, eventHandlers);
        }

    }

    public List<EventHandler> getEventHandlersForType(String eventType) {
        return eventTypeToEventHandlers.get(eventType);
    }
}
