package com.devsegal.jserve;

import com.devsegal.jserve.middleware.EventHandler;
import com.devsegal.jserve.middleware.Middleware;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.nio.file.Path;

public class HTTPServer implements Runnable, Serverable {
    private int port = 80;
    private boolean isStopped = false;

    private ServerSocket server;
    private Path originalServerPath;
    private Path properties;
    private BiConsumer<RequestParser, ResponseStream> notFoundPageHandler;
    private RouteRegistry routes;
    private FileMIMETypeRegistry fileTypes;
    private EventHandlerRegistry eventHandlers;

    /**
     * @param port the port the server will listen on.
     */
    public HTTPServer(int port) {
        this.port = port;

        try {
            fileTypes = new FileMIMETypeRegistry();
        } catch(IOException e) {
            e.printStackTrace();
        }

        routes = new RouteRegistry(fileTypes);
        eventHandlers = new EventHandlerRegistry();
    }

    public HTTPServer(int port, Path properties) {
        this.port = port;

        try {
            fileTypes = new FileMIMETypeRegistry(properties);
        } catch(IOException e) {
            e.printStackTrace();
        }

        routes = new RouteRegistry(fileTypes);
        eventHandlers = new EventHandlerRegistry();
    }

    public void loadMiddleware(Middleware middleware) {
        for(EventHandler eventHandler : middleware.getEventHandlers()) {
                eventHandlers.registerEventHandler(eventHandler.getEventType(), eventHandler);
        }
    }

    public void setupOriginalServerPath(Path path) {
        this.originalServerPath = path;
    }

    /**
     * @param path You can pass in a string instead of a Path type.
     */
    public void setupOriginalServerPath(String path) {
        setupOriginalServerPath(Path.of(path));
    }

    public void setupPublicAssetFolder(Path assetFolder) {
        routes.registerAssetFolderAsRoutes(assetFolder, "");
    }

    public void setupPublicAssetFolder(String assetFolder) {
        routes.registerAssetFolderAsRoutes(Path.of(assetFolder), "");
    }

    public void setupPublicAssetFolder(String assetFolder, String assetFolderPrefix) {
        routes.registerAssetFolderAsRoutes(Path.of(assetFolder), assetFolderPrefix);
    }

    public void setupNotFoundPageHandler(BiConsumer<RequestParser, ResponseStream> notFoundPageHandler) {
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public void route(String path, String method, BiConsumer<RequestParser, ResponseStream> handler) {
            routes.registerRoute(path, method, handler);
    }

    /**
     * Will setup the server socket for connections, it will not start accepting them.
     */
    private void initializeServerSocket() {
        try { 
            server = new ServerSocket(port);
        } catch(IOException e) {
            System.out.println(e.getMessage()); 
        } 
    }
    
    /**
     * This method will continue accepting connections and sending them to ConnectionHandlers, until stop() is called. 
     */
    private void acceptConectionsAndThrowToHandlers() {
        try {
            while (!isStopped) {
                Socket connection = server.accept();
                
                if(originalServerPath != null) {
                    ConnectionHandlerConfiguration connectionHandlerConfiguration = new ConnectionHandlerConfiguration();

                    connectionHandlerConfiguration.setConnection(connection);
                    connectionHandlerConfiguration.setRoutes(routes);
                    connectionHandlerConfiguration.setOriginalServerPath(originalServerPath);
                    connectionHandlerConfiguration.setNotFoundPageHandler(notFoundPageHandler);
                    connectionHandlerConfiguration.setEventHandlers(eventHandlers);

                    (new Thread(new ConnectionHandler( connectionHandlerConfiguration ))).start();
                } else {
                    ConnectionHandlerConfiguration connectionHandlerConfiguration = new ConnectionHandlerConfiguration();

                    connectionHandlerConfiguration.setConnection(connection);
                    connectionHandlerConfiguration.setRoutes(routes);
                    connectionHandlerConfiguration.setNotFoundPageHandler(notFoundPageHandler);
                    connectionHandlerConfiguration.setEventHandlers(eventHandlers);

                    (new Thread(new ConnectionHandler( connectionHandlerConfiguration ))).start();
                }    
            }			
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        initializeServerSocket();
            
        try {
            acceptConectionsAndThrowToHandlers();
        } catch (Exception e) {
            e.printStackTrace(); // replace with logging utility
        }	
    } 

    public void stop() {
    	isStopped = true;
    }
}

