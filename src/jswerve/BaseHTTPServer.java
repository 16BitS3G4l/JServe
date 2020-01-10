package jswerve;

import java.io.BufferedReader;
import java.io.PrintWriter; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class BaseHTTPServer {
	
    private int port = 80; 
    private BufferedReader reader;
    private PrintWriter writer;
    private ServerSocket ss;
    private Socket s;

    public void initServerSocket() {
        try { 
            ss = new ServerSocket(port);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        } 
    }

    public void acceptConectionsAndThrowToHandlers() throws InterruptedException {
        try {
            while (true) {
                s = ss.accept();
                
                (new Thread(new ConnectionHandler(s))).start();
            }			
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BaseHTTPServer(int port) throws InterruptedException {
    	this.port = port;
        initServerSocket();
        try {
			acceptConectionsAndThrowToHandlers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public BaseHTTPServer() throws InterruptedException {
        initServerSocket();
        try {
			acceptConectionsAndThrowToHandlers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}