package jswerve;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

	StringBuilder reqFullContents;
    BufferedReader br;
    PrintWriter pw;
    Socket conn;
    private boolean close = false;
    
    public ConnectionHandler(Socket s) {	
    	this.conn  = s;
    }

    public void run() {
	    	try {
	            br = new BufferedReader(
	            		new InputStreamReader(conn.getInputStream()));
	            
	            String content;
	            
	            while((content = br.readLine()) != null)
	            	System.out.println(content);
	    	} catch (IOException e) {
	    			e.printStackTrace();
	    			// insert logging utility
	            }
	            
	            try {
	    			pw = new PrintWriter(
	    					new OutputStreamWriter(conn.getOutputStream()));
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	            
	            Response resp = new Response(STATUS.OK);
	            
	            resp.setConnectionType("close");
	            resp.setContentType("text/html");
	            resp.insertContent("hello world!");
	            resp.insertContent("<br><div><b>sdf</b></div>");
	            pw.print(resp);
	            
	            pw.close();
	    }
}