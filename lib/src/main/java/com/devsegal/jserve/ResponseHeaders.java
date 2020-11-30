package com.devsegal.jserve;

public class ResponseHeaders {
    private STATUS responseStatus;
    private int responseCode;

    private String contentType;
    private String connectionType;

    private static final String CRLF = "\r\n"; 

    public ResponseHeaders(STATUS responseStatus, int responseCode, String contentType, String connectionType) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.contentType = contentType;
        this.connectionType = connectionType;
    }

    public ResponseHeaders(String contentType, String connectionType) {
        this(STATUS.OK, 200, contentType, connectionType);
    }

    public static String convertResponseStatusToString(ResponseHeaders responseHeaders) throws ResponseStatusNullException {
        if(responseHeaders.getResponseStatus() == null) {
            throw new ResponseStatusNullException("Your ResponseHeaders object has a null value for it's response status.");
        }

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

	public static String convertToString(ResponseHeaders responseHeaders) throws ResponseStatusNullException {
        StringBuilder responseString = new StringBuilder();

        String firstLineResponsePrefix = "HTTP/1.1 ";
        responseString.append(firstLineResponsePrefix + responseHeaders.getResponseCode() + " ");
        responseString.append(ResponseHeaders.convertResponseStatusToString(responseHeaders) + CRLF);
        
        responseString.append("Connection-type:" + responseHeaders.getConnectionType() + CRLF);

        responseString.append("Content-type:" + responseHeaders.getContentType() + CRLF);

        // before adding the content, add one line in between headers and body 
        responseString.append(CRLF);
        
        return responseString.toString();
    }
}
