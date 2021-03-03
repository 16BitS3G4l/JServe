package com.devsegal.jserve.middleware;

import com.devsegal.jserve.RequestParser;
import com.devsegal.jserve.ResponseStream;

public interface EventHandler {
    String getEventType();
    void action(RequestParser request, ResponseStream response);
}