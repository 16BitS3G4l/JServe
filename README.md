# JServe
A lightweight, simple-to-use HTTP Web Server built with Java. 

note: Relies on Java 8+ API's

<img src="https://i.ibb.co/3RKzGsw/Adobe-Stock-389747989.jpg" />

# Quick setup
You can install the jar (from the releases page) or install it from maven/gradle.
More information will be available soon on this process. 

# Examples 
## Simple Server
```java
import com.devsegal.jserve.BaseHTTPServer;
import com.devsegal.jserve.ResponseHeaders;

//... your other code
BaseHTTPServer server = new BaseHTTPServer(8080); // port

server.route("/", "GET", (request, response) -> {
  response.setResponseHeaders(new ResponseHeaders("text/html", "close"));
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
  response.setResponseHeaders(new ResponseHeaders("text/html", "close");
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

## Most Recent Changes
Replace ambiguous interfaces for Predicates, Consumers, etc... (if necessary and enough evidence is present to indicate negligent practices/code)
- FilterData (resolved by introducing a Predicate<String> lineRejectable)
- NotFoundPageHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> notFoundPageHandler)
- Response 
- TransformPath (resolved by introducing a Function<Path, Path> translatePath) 
- WebRouteHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> webRouteHandler)
  
## To-Do List
(soon to be) 
- (**in progress**) Unit Testing (integration testing will be a future consideration, but at the very least I'd like to create an extensive suite of unit tests)
- (**done!**) <s>Serve (by default) binary files instead of ASCII encoded text files - so people can serve images, videos, and other file formats.</s>

(at some point in the future) 
1. Introduce middleware - so people can extend it more easily than editing source code itself. 
2. Support/Compatibility for Android 
3. Add logging instead of intrusive and unwelcomed stdout message clogging 
