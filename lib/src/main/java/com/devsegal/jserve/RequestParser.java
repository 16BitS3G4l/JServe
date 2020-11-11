package com.devsegal.jserve;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class RequestParser {
    private String method = "";
    private String path = "";
    private String httpVersion = "";
    private BufferedReader reader; 
    private HashMap<String, String> postData;
    private HashMap<String, String> headers;

    public RequestParser(BufferedReader reader) {
        this.reader = reader;
        postData = new HashMap<String, String>();
        headers = new HashMap<>();
    }

    public void parseRequest() {
        parseFirstLine();
    
        if(method.equals("GET")) {
            parseContentAfterFirstLineForGetRequest();
        } else if(method.equals("POST")) {
            parseContentAfterFirstLineForPostRequest();
        }
    }

    public void parseFirstLine() {
        try {

            boolean waitingForInformation = true;

            while(waitingForInformation) {
                if(reader.ready()) {
                    waitingForInformation = false;

                    String[] firstLine = reader.readLine().split(" "); 

                    method = firstLine[0];
                    path = firstLine[1];
                    httpVersion = firstLine[2];
                }
            }

        } catch(IOException e) {
                e.printStackTrace();
        }
    }
    
    public void parseContentAfterFirstLineForGetRequest() {
        readContent(line -> false);
    }

    public void parseContentAfterFirstLineForPostRequest() {
        readContent(line -> parseRequestLineForSignsOfPostData(line));
    }
    
    public void readContent(FilterData<String> filterData) {
        try {
            while(reader.ready()) {
                String line = reader.readLine();
                
                // If the filter found a reason to stop continuing reading data from the request
                if(filterData.badData(line)) {
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

    public HashMap<String, String> getHeaders() {
        return headers;
    }
} 
