package com.devsegal.jserve;

import java.net.Socket;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.nio.file.Path;

public class ConnectionHandlerConfiguration {
    private Socket connection;
    private RouteRegistry routes;
    private Path originalServerPath;
    private BiConsumer<RequestParser, ResponseStream> notFoundPageHandler;

    public ConnectionHandlerConfiguration(Socket connection, RouteRegistry routes) {
        this.connection = connection;
        this.routes = routes;
    }

    public ConnectionHandlerConfiguration(Socket connection, RouteRegistry routes, BiConsumer<RequestParser, ResponseStream> notFoundPageHandler) {
        this(connection, routes);
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public ConnectionHandlerConfiguration(Socket connection, RouteRegistry routes, Path originalServerPath) {
        this.connection = connection;
        this.routes = routes;
        this.originalServerPath = originalServerPath;
    }

    public ConnectionHandlerConfiguration(Socket connection, RouteRegistry routes, Path originalServerPath, BiConsumer<RequestParser, ResponseStream> notFoundPageHandler) {
        this(connection, routes, originalServerPath);
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public Socket getConnection() {
        return connection;
    }

    public RouteRegistry getRouteRegistry() {
        return routes;
    }

    public Path getOriginalServerPath() {
        return originalServerPath;
    }

    public BiConsumer<RequestParser, ResponseStream> getNotFoundPageHandler() {
        return notFoundPageHandler;
    }
}
