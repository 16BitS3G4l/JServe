package com.devsegal.jserve;

import java.net.Socket;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.nio.file.Path;

public class ConnectionHandlerConfiguration {
    private Socket connection;
    private HashMap<String, BiConsumer<RequestParser, ResponseWriter>> routesToHandlers;
    private Path originalServerPath;
    private BiConsumer<RequestParser, ResponseWriter> notFoundPageHandler;

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, BiConsumer<RequestParser, ResponseWriter>> routesToHandlers) {
        this.connection = connection;
        this.routesToHandlers = routesToHandlers;
    }

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, BiConsumer<RequestParser, ResponseWriter>> routesToHandlers, BiConsumer<RequestParser, ResponseWriter> notFoundPageHandler) {
        this(connection, routesToHandlers);
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, BiConsumer<RequestParser, ResponseWriter>> routesToHandlers, Path originalServerPath) {
        this.connection = connection;
        this.routesToHandlers = routesToHandlers;
        this.originalServerPath = originalServerPath;
    }

    public ConnectionHandlerConfiguration(Socket connection, HashMap<String, BiConsumer<RequestParser, ResponseWriter>> routesToHandlers, Path originalServerPath, BiConsumer<RequestParser, ResponseWriter> notFoundPageHandler) {
        this(connection, routesToHandlers, originalServerPath);
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public Socket getConnection() {
        return connection;
    }

    public HashMap<String, BiConsumer<RequestParser, ResponseWriter>> getRoutesToHandlers() {
        return routesToHandlers;
    }

    public Path getOriginalServerPath() {
        return originalServerPath;
    }

    public BiConsumer<RequestParser, ResponseWriter> getNotFoundPageHandler() {
        return notFoundPageHandler;
    }
}
