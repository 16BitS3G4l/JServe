package com.devsegal.jserve;

interface Response {	
	public void setResponseHeaders(ResponseHeaders responseHeaders);
	public void insertContent(String content);
}
