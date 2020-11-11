package com.devsegal.jserve;

import java.net.Socket;
import java.util.HashMap;
import java.nio.file.Path;

public class ConnectionHandlerConfiguration {
    private Socket connection;
    private HashMap<String, WebRouteHandler> routesToHandlers;
    private Path originalServerPath;
    private NotFoundPageHandler notFoundPageHandler;

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, WebRouteHandler> routesToHandlers) {
        this.connection = connection;
        this.routesToHandlers = routesToHandlers;
    }

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, WebRouteHandler> routesToHandlers, NotFoundPageHandler notFoundPageHandler) {
        this(connection, routesToHandlers);
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, WebRouteHandler> routesToHandlers, Path originalServerPath) {
        this.connection = connection;
        this.routesToHandlers = routesToHandlers;
        this.originalServerPath = originalServerPath;
    }

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, WebRouteHandler> routesToHandlers, Path originalServerPath, NotFoundPageHandler notFoundPageHandler) {
        this(connection, routesToHandlers, originalServerPath);
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public Socket getConnection() {
        return connection;
    }

    public HashMap<String, WebRouteHandler> getRoutesToHandlers() {
        return routesToHandlers;
    }

    public Path getOriginalServerPath() {
        return originalServerPath;
    }

    public NotFoundPageHandler getNotFoundPageHandler() {
        return notFoundPageHandler;
    }
}
