package com.devsegal.jserve;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    private String method = "";
    private String path = "";
    private String httpVersion = "";
    private BufferedReader reader; 
    private HashMap<String, String> headers;
    
    
    public RequestParser(BufferedReader reader) {
        this.reader = reader;
        headers = new HashMap<>();
    }

    public void getHeaders(FilterData<String> filterData) {
        try {
            while(reader.ready()) {
                String line = reader.readLine();

                // if the filter has found a reason to stop continuing
                if(filterData.badData(line, reader)) {
                    break; 
                }

                String[] header = line.split(":", 2);
                
                System.out.println(line);

                if(header.length == 2) {
                    headers.put(header[0], header[1]);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void parseRequest() {
        try {
            boolean waitingForInformation = true;

            while(waitingForInformation) {
                if(reader.ready()) {
                    waitingForInformation = false;

                    String[] firstLine = reader.readLine().split(" "); // should be the METHOD PATH HTTP VERSION 

                    method = firstLine[0];
                    path = firstLine[1];
                    httpVersion = firstLine[2];
                }
            }

            if(method.equals("GET")) {
                getHeaders((data, reader) -> {
                    return true;
                });
            } else if(method.equals("POST")) {
                getHeaders((data, reader) -> {
                    if(data.length() == 0) {
                        try {
                            while(reader.ready())
                                System.out.println("post data: " + reader.readLine());
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else {
                        return false;
                    }
                });
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
