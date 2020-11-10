package com.devsegal.jserve;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.nio.file.Path;
import java.util.function.Consumer;

class ConnectionHandler implements Runnable {

	StringBuilder requestContents;
    BufferedReader reader; 
	ResponseWriter responseWriter;
	BufferedReader requestReader;
    Socket connection;
	HashMap<String, WebRouteHandler> routes;
	String serverPath;
	String contentType;
	String responsePath;


	public ConnectionHandler(Socket connection, HashMap<String, WebRouteHandler> routes, String serverPath) {	
		this.connection  = connection;
		this.routes = routes;
		this.serverPath = serverPath;
		this.requestContents = new StringBuilder();

		try {
			responseWriter = new ResponseWriter(connection.getOutputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}

		try {
			requestReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
			RequestParser requestParser = new RequestParser();
			requestParser.parseRequest(requestReader);

			WebRouteHandler webRouteHandler = routes.get(requestParser.getPath() + requestParser.getMethod());

			if(webRouteHandler == null) {
				responseWriter.send(); // response to invalid requests  
			} else {
				webRouteHandler.handler(requestParser, responseWriter);	
			}
		}
}
