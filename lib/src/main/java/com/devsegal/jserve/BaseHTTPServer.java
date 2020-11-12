package com.devsegal.jserve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseHTTPServer implements Runnable {

    private int port = 80; 
    private boolean isStopped = false;
    
    private NotFoundPageHandler notFoundPageHandler;
    private Path originalServerPath;  
    private ServerSocket server;
    private HashMap<String, WebRouteHandler> routesToHandlers;
    
    public BaseHTTPServer(int port) {
        this.port = port;
        routesToHandlers = new HashMap<>();
    }
    
    public void setupOriginalServerPath(Path path) {
        this.originalServerPath = path;
    }

    public void setupOriginalServerPath(String path) {
        setupOriginalServerPath(Path.of(path));
    }

    // TODO: replace this method with more readable one and better structured
    // idea: put lambda in one function 
    // TODO: allow nested folders in public asset folder
    public void setupPublicAssetFolder(Path assetFolder, String assetFolderPrefix) {
        Stream<Path> publicAssets = null;

        try {
            publicAssets = Files.list(assetFolder);
            publicAssets.forEach(path -> {
                System.out.println(assetFolderPrefix + "/" + path.getFileName());

                routesToHandlers.put(assetFolderPrefix + "/" + path.getFileName().toString() + "GET", (request, response) -> {
                    response.setResponseHeaders(new ResponseHeaders("text/html", "close"));
                    response.readContentFromFile(path, false);
                    response.send();
                });
            });

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            publicAssets.close();
        }
    }

    public void setupPublicAssetFolder(Path assetFolder) {
        setupPublicAssetFolder(assetFolder, "");
    }

    public void setupPublicAssetFolder(String path) {
        setupPublicAssetFolder(Path.of(path));
    }

    public void setupPublicAssetFolder(String assetFolder, String assetFolderPrefix) {
        setupPublicAssetFolder(Path.of(assetFolder), assetFolderPrefix);
    }

    public void setupNotFoundPageHandler(NotFoundPageHandler notFoundPageHandler) {
        this.notFoundPageHandler = notFoundPageHandler;
    }

    public void route(String path, String method, WebRouteHandler handler) {
        routesToHandlers.put(path + method, handler);
    }

    private void initializeServerSocket() {
        try { 
            server = new ServerSocket(port);
        } catch(IOException e) {
            System.out.println(e.getMessage()); 
        } 
    }
    
    private void acceptConectionsAndThrowToHandlers() {
        try {
            while (!isStopped) {
                Socket connection = server.accept();
                
                if(originalServerPath != null) {
                    (new Thread(new ConnectionHandler( new ConnectionHandlerConfiguration(connection, routesToHandlers, originalServerPath, notFoundPageHandler) ))).start();
                } else {
                    (new Thread(new ConnectionHandler( new ConnectionHandlerConfiguration(connection, routesToHandlers, notFoundPageHandler) ))).start();
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

