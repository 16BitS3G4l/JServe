package com.devsegal.jserve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

public class BaseHTTPServer implements Runnable {

    private int port = 80; 
    private boolean isStopped = false;
    
    private NotFoundPageHandler notFoundPageHandler;
    private Path originalServerPath;  
    private ServerSocket server;
    private HashMap<String, WebRouteHandler> routesToHandlers;
    private HashMap<String, String> fileTypeToMIMEType = new HashMap<>();
    
    /**
     * 
     * @param port the port the server will listen on.
     */
    public BaseHTTPServer(int port) {
        this.port = port;
        routesToHandlers = new HashMap<>();
        fileTypeToMIMEType = new HashMap<>();
        initializeFileTypesToMIMETypes();
    }
    
    /**
     * 
     * @param path the physical path the server should read from when sending files (aside from public asset files)
     */
    private void initializeFileTypesToMIMETypes() {
        fileTypeToMIMEType.put("jpg", "image/jpeg");
        fileTypeToMIMEType.put("jpeg", "image/jpeg");
        fileTypeToMIMEType.put("png", "image/png");
        fileTypeToMIMEType.put("html", "text/html");
        fileTypeToMIMEType.put("css", "text/css");
        fileTypeToMIMEType.put("js", "text/javascript");
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

    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        
        // dot has not been found
        if(dotIndex == -1) {
            return "";
        } 

        // the file starts with a dot, no file extension 
        else if(dotIndex == 0) { 
            return "";
        } 

        // dot has been found, but it is the last character of the filename 
        // meaning there is no file extension
        else if(dotIndex == fileName.length()) {
            return "";
        }

        // a file extension exists
        else {
            return fileName.substring(dotIndex + 1, fileName.length());
        }
    }

    public String getContentType(Path path) {
        String fileName = path.getFileName().toString();
        String fileExtension = getFileExtension(fileName);

        System.out.println(fileExtension);

        // we found a file extension
        if(!fileExtension.equals("")) {
            return fileTypeToMIMEType.get(fileExtension);
        }

        return "";
    }
    
    // TODO: replace this method with more readable one and better structured
    // idea: put lambda in one function 
    // TODO: allow nested folders in public asset folder
    /**
     * Registers all files in a folder to the routesHandler, so these assets will be sent/found instead of a 404 page.
     * @param assetFolder the folder to register all assets from. 
     * @param assetFolderPrefix instead of routes that start from the root of the server, you can add a prefix to these routes.
     */
    public void setupPublicAssetFolder(Path assetFolder, String assetFolderPrefix) {
        Stream<Path> publicAssets = null;

        try {
            publicAssets = Files.walk(assetFolder);

            publicAssets.forEach(path -> {
                System.out.println(path.toString()); 

                if(!path.toFile().isDirectory()) {
                    routesToHandlers.put(assetFolderPrefix + "/" + path.getFileName().toString() + "GET", (request, response) -> {
                        String contentType = getContentType(path);

                        if(!contentType.equals("")) {
                            response.setResponseHeaders(new ResponseHeaders(contentType, "close"));
                        } else {
                            response.setResponseHeaders(new ResponseHeaders("text/plain", "close"));
                        }

                        response.readContentFromFile(path, false);
                        response.send();
                    });
                }
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

    /**
     * @param path represents a route (from the root of the server website.com/route) 
     * @param method the HTTP method the WebRouteHandler should be invoked on.
     * @param handler the code that will be executed when a request that matches the path + method occurs.
     */
    public void route(String path, String method, WebRouteHandler handler) {
        routesToHandlers.put(path + method, handler);
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

