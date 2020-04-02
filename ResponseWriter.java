package main.java.jswerve;

import java.io.PrintWriter;
import java.io.OutputStream;

public class ResponseWriter extends PrintWriter implements Response {
    private StringBuilder respContents; 
    
    public ResponseWriter(OutputStream os) {
        super(os);
		respContents = new StringBuilder();
		
		// add in default http information
		respContents.append("HTTP 1.1/200" + "\n");
    }

	@Override
	public void setContentType(String contentType) {
        respContents.append("Content-type: " + contentType + "\n");
	}

	@Override
	public void setConnectionType(String connectionType) {
		respContents.append("Connection-type:" + connectionType + "\n");
	}

	@Override
	public void insertContent(String content) {
        respContents.append("\n" + content + "\n");
	}

	public void send() {
		print(respContents.toString());
		close();
	}
}