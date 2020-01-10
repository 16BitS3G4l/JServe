package jswerve;

import java.io.InputStream;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class ConnectionHandler implements Runnable {

	StringBuilder reqFullContents;
    BufferedReader br;
    PrintWriter pw;
    Socket conn;
    
    public ConnectionHandler(Socket s) throws IOException {
        this.conn  = s;
        
        br = new BufferedReader(
        		new InputStreamReader(s.getInputStream()));
        
        pw = new PrintWriter(
        		new OutputStreamWriter(s.getOutputStream()));
        
        // read the contents of request, assign stringbuilder to entirity of value, and then parse using requestparser,
        // then finally compose a response and send it. 
        
        Response resp = new Response(STATUS.OK);
        
        resp.setConnectionType("close");
        resp.setContentType("text/html");
        
        
        pw.close();
    }

    public void run() {
        try {
			conn.close();
		} catch (IOException e) {
			// add to logs
		}
    }
}