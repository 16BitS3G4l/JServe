# JServe <img src="https://i.ibb.co/3RKzGsw/Adobe-Stock-389747989.jpg" height="55" />

An HTTP 1.1 Web Server built with Java. 

# Goals
The goals are as stated: 
- Providing a framework that serves the general usage of a web server (with good performance in mind).
- Maintaining a clean/readable codebase. 

Tradeoffs: If it comes to it, I'm somewhat of a germaphobe with code, so clean will mostly win over, unless it's an order of magnitude difference in performance, and puts the application at risk.    

# Important Notes
Relies on Java 8+ API's.

# Quick Setup
You can install the jar (from the releases page) or install it from maven/gradle.
More information will be available soon on this process. 

# Examples 
## Demonstration of GET request handling
```java
import com.devsegal.jserve.HTTPServer;
import com.devsegal.jserve.ResponseHeaders;

//... your other code
HTTPServer server = new HTTPServer(8080); // port

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

## Demonstration of POST request handling
```java
import com.devsegal.jserve.HTTPServer;
import com.devsegal.jserve.ResponseHeaders;
import java.util.Map;

HTTPServer server = new HTTPServer(8080);
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
## Changelog
The changelog has been moved to [its own file](https://github.com/dev-segal/JServe/blob/master/changelog.md)

## To-Do List
(soon to be) 
- (**in progress**) Unit Testing (integration testing will be a future consideration, but at the very least I'd like to create an extensive suite of unit tests)
- (**in progress**) Thorough Javadoc Documentation (extensively, at a minimum covering BaseHTTPServer, and hopefully the rest of the server) 
- (**done!**) <s>Serve (by default) binary files instead of ASCII encoded text files - so people can serve images, videos, and other file formats.</s>

(at some point in the future) 
1. Release to repositories like Maven Central, so people can easily work with it using Gradle, Maven, and other dependency management software.
2. Introduce middleware - so people can extend it more easily than editing source code itself. 
3. Support/Compatibility for Android 
4. Add logging instead of intrusive and unwelcomed stdout message clogging 
