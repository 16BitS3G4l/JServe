package com.devsegal.jserve;

import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.List;

public class ResponseWriter extends PrintWriter implements Response {
    private StringBuilder responseContents; 
    
    public ResponseWriter(OutputStream output) {
        super(output);
		responseContents = new StringBuilder();
		
		// add in default http information
		responseContents.append("HTTP 1.1/200" + "\n");
    }

	@Override
	public void setContentType(String contentType) {
        responseContents.append("Content-type: " + contentType + "\n");
	}

	@Override
	public void setConnectionType(String connectionType) {
		responseContents.append("Connection-type:" + connectionType + "\n");
	}

	@Override
	public void insertContent(String content) {
        responseContents.append("\n" + content + "\n");
	}

	public void insertContent(List<String> content) {
		for(String contentMember : content) {
			responseContents.append(contentMember);
		}
	}

	public void send() {
		print(responseContents);
		close();
	}
}
