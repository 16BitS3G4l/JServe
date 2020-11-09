package com.devsegal.jserve;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ConnectionHandler implements Runnable {

	StringBuilder reqFullContents;
    BufferedReader br; 
    PrintWriter pw;
    Socket conn;

	public ConnectionHandler(Socket s) {	
    	this.conn  = s;
	}
	
	public void run() {
				try {
					reqFullContents = new StringBuilder();
					BufferedReader br = new BufferedReader( 
						new InputStreamReader(conn.getInputStream()));
					
						// parse request 
						RequestParser rp = new RequestParser();
						rp.parseReq(br);

						

					} catch (IOException e) {
					
				}

	            try {
	    			pw = new PrintWriter(
	    					conn.getOutputStream());
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
				
				try {
					ResponseWriter rw = new ResponseWriter(conn.getOutputStream());
					rw.setConnectionType("close");
					rw.setContentType("text/html");

					rw.send();
				} catch (IOException e) {

				}

	    }
}
