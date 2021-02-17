# JServe <img src="https://i.ibb.co/3RKzGsw/Adobe-Stock-389747989.jpg" height="55" />

An HTTP 1.1 Web Server built with Java. 

## Goals
The goals are as stated: 
- Providing a framework that serves the general usage of a web server (with good performance in mind).
- Maintaining a clean/readable codebase. 

Tradeoffs: If it comes to it, I'm somewhat of a germaphobe with code, so clean will mostly win over, unless it's an order of magnitude difference in performance, and puts the application at risk.    

## Important Notes
Relies on Java 8+ API's.

## Quick Setup
You can install the jar (from the releases page) or install it from maven/gradle.
More information will be available soon on this process. 

## Examples 
### Demonstration of GET request handling
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

### Demonstration of POST request handling
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

## To-Do List (everything that needs to be done to merit a new release)
- (**in progress**) Introduce middleware at some level. This might be as simple as creating hooks, so middleware "plug-ins" could be activated at certain places in code. Or, they could be more complex creatures with lifecycles and such things as I've imagined... We'll have to see what makes sense. 

- (**not working on**) Make importing the library as easy as using respositories like Maven Central (in gradle/maven). For some reason, this is something that somehow keeps getting me annoyed - after reading many many tutorials. I've determined the best way to proceed is just to accept it, and read directly from Maven's site on the process. 

- (**not working on**) Resolve the issue with the public asset folder's structure. What JServe currently does is like flattening a list of lists (basically putting the lists (in the list) elements in the original list, thus "flattening" the list into one list, rather than a list of lists. Now, replace the idea of lists with directories, and a list with a root directory, and suddenly it's very analogous. 
 
- (**done**) <s>Unit Testing (integration testing will be a future consideration, but at the very least I'd like to create an extensive suite of unit tests)</s>

This is way too subjective. It was more of a motivational, go-get-started kind of reminder, than a fixed point which I was aiming for (what is extensive? do we measure that by number of unit tests? do we assess each one?) 

- (**done**) <s> Thorough Javadoc Documentation (extensively, at a minimum covering BaseHTTPServer, and hopefully the rest of the server) </s> 

This is also way too subjective. Definitely a way to get started doing it (which did end up happening), so I'll consider it's mission accomplished, and in the future come up with very very specific metrics to easily measure how much progress has been made in each of these tasks. 

- (**done!**) <s>Serve (by default) binary files instead of ASCII encoded text files - so people can serve images, videos, and other file formats.</s>
 
The following are tasks that could be done now, but could also be pushed off to later versions depending on the mood. 
1. Support/Compatibility for Android
2. Add logging instead of intrusive and unwelcomed stdout message clogging 

## Help Out
Giving a helping hand to an open source project is really easy, however intimidating it might be (which it shouldn't!). There are tons of stuff to be done - namely features, fixes (even identifying bugs and opening issues), and helping sustain the project's goals/vision. You're encouraged to make/suggest changes in the code/documentation, mess around with things, and try stuff out. The main things to be done (as I see them) are listed in the To-Do section. I'm not really tied to the ordering of it, (thus warranting the use of an unordered list), but they're the most direct conflicts between the project's current state, and where I want it to go. Changes that might seem minute (like improving the writing of READMEs or improving a comment - which isn't really minute, but that's tangential...) are very important to the project's health.
