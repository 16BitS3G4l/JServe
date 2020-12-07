package com.devsegal.jserve;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.nio.file.Path;

class ConnectionHandler implements Runnable {

	private boolean keepAlive = true; 
	private BufferedReader requestReader;
	private ResponseStream responseStream;
	private Path originalServerPath;
	private Socket connection;
	private BiConsumer<RequestParser, ResponseStream> notFoundPageHandler;
	private RouteRegistry routes;

	public ConnectionHandler(ConnectionHandlerConfiguration configuration) {	
		this.connection  = configuration.getConnection();
		this.routes = configuration.getRouteRegistry();
		this.notFoundPageHandler = configuration.getNotFoundPageHandler();

		Path temporaryServerPath = configuration.getOriginalServerPath();
		if(temporaryServerPath != null) {
			this.originalServerPath = temporaryServerPath;
		}
		
		try {
			if(originalServerPath != null) {
				responseStream = new ResponseStream(connection.getOutputStream(), originalServerPath);
			} else {
				responseStream = new ResponseStream(connection.getOutputStream());
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

			BiConsumer<RequestParser, ResponseStream> regularPageHandler = routes.getHandler(requestParser.getPath() + requestParser.getMethod());

			// If the page is not officially registered as a path, in other words - a 404 page is now necessary 
			if(regularPageHandler == null) {
				notFoundPageHandler.accept(requestParser, responseStream);
			} else {
				regularPageHandler.accept(requestParser, responseStream);
			}

			System.out.print("\n\n");
		}
	}
