package com.devsegal.jserve.middleware;

import java.util.List;

public abstract class Middleware {
    public abstract List<EventHandler> getEventHandlers();
}