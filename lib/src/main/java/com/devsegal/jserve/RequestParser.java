package com.devsegal.jserve;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RequestParser {
    private String method = "";
    private String path = "";
    private String httpVersion = "";
    private BufferedReader reader; 
    private HashMap<String, String> postData;
    private HashMap<String, String> parameters;
    private HashMap<String, String> headers;

    public RequestParser(BufferedReader reader) {
        this.reader = reader;
        postData = new HashMap<>();
        parameters = new HashMap<>();
        headers = new HashMap<>();
    }

    public void parseRequest() {
        parseRequestLine();
    
        if(method.equals("GET")) {
            processGetRequest();
        } else if(method.equals("POST")) {
            processPostRequest();
        }
    }

    public void parseRequestLine() {
        try {

            boolean waitingForInformation = true;

            while(waitingForInformation) {
                if(reader.ready()) {
                    waitingForInformation = false;
                    
                    String[] splitRequestLine = reader.readLine().split(" ");

                    method = splitRequestLine[0];
                    path = splitRequestLine[1];

                    System.out.println(path);

                    // Path should really be path up till the ?, and then the parameters should be set from there
                    int startingIndexOfParameters = path.indexOf("?") + 1;

                    // If it does exist (after 1 added to the -1 returned when it is not found)
                    if(!(startingIndexOfParameters == 0)) {

                        String[] parameterPairs = path.substring(startingIndexOfParameters).split("&");

                        for(String parameterPair : parameterPairs) {
                            String[] splitParameter = parameterPair.split("=");

                            parameters.put(splitParameter[0], splitParameter[1]);
                        }

                        // Remove the parameters from the path
                        path = path.substring(0, startingIndexOfParameters - 1);
                    }


                    httpVersion = splitRequestLine[2];
                }
            }

        } catch(IOException e) {
                e.printStackTrace();
        }
    }
    
    public void processGetRequest() {
        readContent(line -> false);
    }

    public void processPostRequest() {
        readContent(line -> parseRequestLineForSignsOfPostData(line));
    }
    
    public void readContent(Predicate<String> lineRejectable) {
        try {
            while(reader.ready()) {
                String line = reader.readLine();

                // If we find a reason to reject/stop reading content, then stop reading content 
                if(lineRejectable.test(line)) {
                    break; 
                }

                splitLine(line, ":", (splittedLine) -> splittedLine.length == 2, (splittedLine) -> {
                    headers.put(splittedLine[0], splittedLine[1]);
                    
                    System.out.println(splittedLine[0] + ":" + splittedLine[1]);
                });

            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void splitLine(String line, String splitter, Predicate<String[]> predicate, Consumer<String[]> action) {
        String[] splitLine = line.split(splitter);

        if(predicate.test(splitLine)) {
            action.accept(splitLine);
        }
    }

    public boolean parseRequestLineForSignsOfPostData(String line) {
        if(line.length() == 0) {
            
            try {
                StringBuilder tempPostData = new StringBuilder();

                while(reader.ready()) {
                    tempPostData.append((char)reader.read());
                }

                System.out.println(tempPostData);

                splitLine(tempPostData.toString(), "&", (splittedLine) -> true, (splittedLine) -> {
                    
                    for(String parameterAndValue : splittedLine) {
                        String[] parameterSplitted = parameterAndValue.split("="); 
                        postData.put(parameterSplitted[0], parameterSplitted[1]);
                        System.out.println(parameterSplitted[0] + "=" + parameterSplitted[1]);
                    }

                });
                
                return true; // Post data will be the next line

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return false; // If post data has not been recognized
    }
    
    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HashMap<String, String> getPostData() {
        return postData;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
} 
