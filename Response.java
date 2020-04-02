package main.java.jswerve;

interface Response {
	
	StringBuilder respContents = new StringBuilder();
	boolean bodyContentInserted = false;
	
	public void setContentType(String contentType);
	public void setConnectionType(String connectionType);
	public void insertContent(String content);
}