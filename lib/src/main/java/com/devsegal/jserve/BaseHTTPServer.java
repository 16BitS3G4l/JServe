package com.devsegal.jserve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class BaseHTTPServer implements Runnable {

    private int port = 80; 
    private boolean isStopped = false;

    private String serverPath  = ""; 

    private ServerSocket server;
    private HashMap<String, WebRouteHandler> routesToHandlers;
    
    public BaseHTTPServer(int port) {
        this.port = port;
        routesToHandlers = new HashMap<>();
    }
    
    public void setupOrigin(String path) {
        serverPath = path;
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
    
/* 
    public void parseRoutes() {
        File routesFile = new File(serverPath + "routes.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(routesFile));
            String line;
            String[] paths; 

            while((line = reader.readLine()) != null) {
                 paths = line.split(" ");
                 routes.put(paths[0], paths[1]);
            }

            System.out.println(routes.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } */

    private void acceptConectionsAndThrowToHandlers() {
        try {
            while (!isStopped) {
                Socket connection = server.accept();
                
                (new Thread(new ConnectionHandler(connection, routesToHandlers, serverPath))).start();
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

