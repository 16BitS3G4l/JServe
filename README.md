# JServe <img src="https://i.ibb.co/3RKzGsw/Adobe-Stock-389747989.jpg" height="55" />

An HTTP Web Server built with Java. 

## Goals
The major focuses are providing a framework that serves the general usage of a web server (with good performance in mind), and maintains a clean/readable codebase. If it comes to it, I'm somewhat of a germaphobe with code, so clean will mostly win over, unless it's an order of magnitude difference in performance, and puts the application at risk.    

## Important Notes
Relies on Java 8+ API's

# Quick setup
You can install the jar (from the releases page) or install it from maven/gradle.
More information will be available soon on this process. 

# Examples 
## Simple Server (basic GET request demonstration)
```java
import com.devsegal.jserve.BaseHTTPServer;
import com.devsegal.jserve.ResponseHeaders;

//... your other code
BaseHTTPServer server = new BaseHTTPServer(8080); // port

server.route("/", "GET", (request, response) -> {

  try {
        response.setResponseHeaders(new ResponseHeaders("text/html", "close"));
      } catch(ResponseStatusNullException e) {
        e.printStackTrace();
      } 
      
  response.insertContent("Hello world!");
  response.send();
});

server.run(); 
```

## Utilizing Post requests (with a login page)
```java
import com.devsegal.jserve.BaseHTTPServer;
import com.devsegal.jserve.ResponseHeaders;
import java.util.Map;

BaseHTTPServer server = new BaseHTTPServer(8080);
server.setupOriginalServerPath("path/to/server/folder"); // Where files are read from (excluding the public assets folder)

// your login page of choice
server.route("/login", "GET", (request, response) -> {
  
  try {
      response.setResponseHeaders(new ResponseHeaders("text/html", "close"); 
  } catch(ResponseStatusNullException e) {
      e.printStackTrace();
  } 
  
  response.readContentFromFile("login.html");
});

server.route("/login", "POST", (request, response) -> {
  // Assuming we won't send anything back, just send an empty response 
  response.send();
  
  // Now, get all data posted 
  Map<String, String> postData = request.getPostData(); 
  
  // get the username 
  String username = postData.get("username");
  
  // get the password
  String password = postData.get("password");
  
  // save it somewhere
  // ...
});
```

## Most Recent Changes (Changelog)
1._Introduced a new exception for cases of a Null response status, and added unit testing to facilitate better code practices, and code quality._


2._Replace ambiguous interfaces for Predicates, Consumers, etc... (if necessary and enough evidence is present to indicate negligent practices/code)_
- FilterData (resolved by introducing a Predicate<String> lineRejectable)
- NotFoundPageHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> notFoundPageHandler)
- Response 
- TransformPath (resolved by introducing a Function<Path, Path> translatePath) 
- WebRouteHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> webRouteHandler)
  
## To-Do List
(soon to be) 
- (**in progress**) Unit Testing (integration testing will be a future consideration, but at the very least I'd like to create an extensive suite of unit tests)
- (**in progress**) Thorough Javadoc Documentation (extensively, at a minimum covering BaseHTTPServer, and hopefully the rest of the server) 
- (**done!**) <s>Serve (by default) binary files instead of ASCII encoded text files - so people can serve images, videos, and other file formats.</s>

(at some point in the future) 
1. Introduce middleware - so people can extend it more easily than editing source code itself. 
2. Support/Compatibility for Android 
3. Add logging instead of intrusive and unwelcomed stdout message clogging 
