package com.devsegal.jserve;

interface Response {	
	public void setResponseHeaders(ResponseHeaders responseHeaders) throws ResponseStatusNullException;
	public void insertContent(String content);
}
