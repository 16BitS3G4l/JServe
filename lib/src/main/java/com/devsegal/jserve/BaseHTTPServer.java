package com.devsegal.jserve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
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

