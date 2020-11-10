package com.devsegal.jserve;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    private String method = "";
    private String path = "";
    private String httpVersion = "";
    private HashMap<String, String> headers;

    
    public RequestParser() {
        headers = new HashMap<>();
    }

    public void parseRequest(BufferedReader reader) {
        try {
            if(reader.ready()) {
                String[] firstLine = reader.readLine().split(" "); // should be the METHOD PATH HTTP VERSION 
                
                method = firstLine[0];
                path = firstLine[1];
                httpVersion = firstLine[2];
            }

            while(reader.ready()) {
                String line = reader.readLine();
                String[] header = line.split(":", 2);
                
                if(header.length == 2) {
                    headers.put(header[0], header[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
} 
