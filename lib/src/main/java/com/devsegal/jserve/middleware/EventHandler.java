package com.devsegal.jserve.middleware;

public abstract class EventHandler {
    public abstract String getEventType();
    public abstract void action(Object data);
}