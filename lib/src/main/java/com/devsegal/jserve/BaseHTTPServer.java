package com.devsegal.jserve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class BaseHTTPServer implements Runnable {
	
    private int port = 80; 
    private ServerSocket ss;
    private Socket s;
    public static boolean isStopped = false;
    private String serverPath  = "C:\\Users\\gitpu\\programming\\jswerve\\server"; // for other users it will be empty
    private HashMap<String, String> routes;
    
    // structure of a server folder 
    /* 
        1. Routes file 
         --- includes all the information on routes (from -> where)
        2. Data files, etc
        --- for most of this, I believe it will be inherit
        3. Connections, mass communication
        --- this is more of a inherit/just-in-time system based on more complex infrastructure 
    */ 

    private void initServerSocket() {
        try { 
            ss = new ServerSocket(port);
        } catch(IOException e) {
            System.out.println(e.getMessage()); // replace with logging utility
        } 
    }

    private void acceptConectionsAndThrowToHandlers() {
        try {
            while (!isStopped) {
                s = ss.accept();
                
                (new Thread(new ConnectionHandler(s))).start();
            }			
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BaseHTTPServer(int port) {
        this.port = port;

        // parse the routes in the routes file
        parseRoutes();
    }
    
    public void parseRoutes() {
        File routesFile = new File(serverPath + "\\" + "routes.txt");
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(routesFile));
            String line;
            String[] paths; 

            while((line = br.readLine()) != null) {
                 paths = line.split(" ");
                 routes.put(paths[0], paths[1]);
            }

            System.out.println(routes.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }     
    }

    public void stop() {
    	isStopped = true;
    }
    
    public void setupOrigin(String path) {
        serverPath = path;
    }

    public void run() {
        // before starting the server, we have to get all the information regarding: 
		/* 
		 1. Routes - routes file
		 2. Data (files, strings, etc) includes any html, js, css files etc.... (all srcs will originate from here I believe)
		 3. Connections (just-in-time approach) bulk messaging, communication handling, etc
		*/
		
		// for this version, we'll take in a file and parse it with all the routes, etc... 
		// In later versions, perhaps we'll provide an option in doing this programmatically... 
        

        initServerSocket();
            
        try {
            acceptConectionsAndThrowToHandlers();
        } catch (Exception e) {
            e.printStackTrace(); // replace with logging utility
        }	
    } 
}

