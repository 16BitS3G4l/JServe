package com.devsegal.jserve;

public interface WebRouteHandler {
    public void handler(RequestParser requestParser, ResponseWriter responseWriter);
}
