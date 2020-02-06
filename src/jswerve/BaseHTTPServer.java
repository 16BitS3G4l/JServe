package jswerve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BaseHTTPServer implements Runnable {
	
    private int port = 80; 
    private ServerSocket ss;
    private Socket s;
    public static boolean isStopped = false;
    
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
    }
    
    public void stop() {
    	isStopped = true;
    }
    
    public void run() {
    	initServerSocket();
    	
    	try {
    		acceptConectionsAndThrowToHandlers();
		} catch (Exception e) {
			e.printStackTrace(); // replace with logging utility
		}	

    }
    
    public void setRoute(String route, RouteHandler rh) {
    	
    }
}

