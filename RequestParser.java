package main.java.jswerve;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    private String method = "";
    private String path = "";
    private String httpVersion = "";
    private HashMap<String, String> headers;

    public RequestParser() {
        headers = new HashMap<String, String>();
    }

    public void parseReq(BufferedReader br) {
            
        try {
            if(br.ready()) {
                String[] arrOfStrings = br.readLine().split(" "); // should be the METHOD PATH HTTP VERSION 
                
                method = arrOfStrings[0];
                path = arrOfStrings[1];
                httpVersion = arrOfStrings[2];
            }

            while(br.ready()) {
                String str = br.readLine();
                String[] header = str.split(":", 2);
                
                if(header.length == 2) {
                    headers.put(header[0], header[1]);
                }
            }
        } catch (IOException e) {

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