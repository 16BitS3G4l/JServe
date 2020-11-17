# JServe
A lightweight, simple-to-use HTTP Web Server built with Java.
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

## To-Do List
1. Introduce middleware - so people can extend it more easily than editing source code itself. 
2. Serve (by default) binary files instead of ASCII encoded text files - so people can serve images, videos, and other file formats.
3. Support/Compatibility with Android 
