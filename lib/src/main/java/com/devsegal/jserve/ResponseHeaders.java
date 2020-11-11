package com.devsegal.jserve;

import java.util.EnumSet;

public class ResponseHeaders {
    private STATUS responseStatus;
    private int responseCode;

    private String contentType;
    private String connectionType;

    public ResponseHeaders(STATUS responseStatus, int responseCode, String contentType, String connectionType) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.contentType = contentType;
        this.connectionType = connectionType;
    }

    public ResponseHeaders(String contentType, String connectionType) {
        this(STATUS.OK, 200, contentType, connectionType);
    }

    public static String convertResponseStatusToString(ResponseHeaders responseHeaders) {
        return responseHeaders.getResponseStatus().name().replace("_", " ");
    }

    public STATUS getResponseStatus() {
        return responseStatus;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getContentType() {
        return contentType;
    }

    public String getConnectionType() {
        return connectionType;
    }

	public static String convertToString(ResponseHeaders responseHeaders) {
        StringBuilder responseString = new StringBuilder();

        String firstLineResponsePrefix = "HTTP 1.1/";
        responseString.append(firstLineResponsePrefix + responseHeaders.getResponseCode() + " ");
        responseString.append(ResponseHeaders.convertResponseStatusToString(responseHeaders) + "\n");
        
        responseString.append("Connection-type:" + responseHeaders.getConnectionType() + "\n");

        responseString.append("Content-type:" + responseHeaders.getContentType() + "\n");

        // before adding the content, add one line in between headers and body 
        responseString.append("\n");
        
        return responseString.toString();
    }
}
