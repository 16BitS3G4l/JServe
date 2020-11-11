package com.devsegal.jserve;

interface Response {
	
	StringBuilder responseContents = new StringBuilder();
	
	public void setResponseHeaders(ResponseHeaders responseHeaders);
	public void insertContent(String content);
}
