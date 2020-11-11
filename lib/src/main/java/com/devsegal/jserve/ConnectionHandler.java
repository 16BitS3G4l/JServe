package com.devsegal.jserve;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.HashMap;
import java.nio.file.Path;

class ConnectionHandler implements Runnable {

    BufferedReader reader; 
	ResponseWriter responseWriter;
	BufferedReader requestReader;
	Path originalServerPath;
	Socket connection;
	String contentType;
	NotFoundPageHandler notFoundPageHandler;
	HashMap<String, WebRouteHandler> routesToHandlers;	


	public ConnectionHandler(ConnectionHandlerConfiguration configuration) {	
		this.connection  = configuration.getConnection();
		this.routesToHandlers = configuration.getRoutesToHandlers();
		this.notFoundPageHandler = configuration.getNotFoundPageHandler();

		Path temporaryServerPath = configuration.getOriginalServerPath();
		if(temporaryServerPath != null) {
			this.originalServerPath = temporaryServerPath;
		}
		
		try {
			if(originalServerPath != null) {
				responseWriter = new ResponseWriter(connection.getOutputStream(), originalServerPath);
			} else {
				responseWriter = new ResponseWriter(connection.getOutputStream());
			}

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
			RequestParser requestParser = new RequestParser(requestReader);
			requestParser.parseRequest();

			WebRouteHandler webRouteHandler = routesToHandlers.get(requestParser.getPath() + requestParser.getMethod());

			// If the page is not officially registered as a path, in other words - a 404 page is now necessary 
			if(webRouteHandler == null) {
				notFoundPageHandler.handle(requestParser, responseWriter);
			} else {
				webRouteHandler.handler(requestParser, responseWriter);	
			}
		}
}
