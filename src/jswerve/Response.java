package jswerve;

public class Response {
	
	StringBuilder reqContents;
	private boolean bodyContentInserted = false;
	
	public Response(STATUS s) {
		reqContents = new StringBuilder(30);
		
		if(s.equals(STATUS.OK)) {
			reqContents.append("HTTP/1.1 200");
		}
		
		else if(s.equals(STATUS.NOT_FOUND)) {
			reqContents.append("HTTP/1.1 404");
		}
	}
	
	public void setContentType(String contentType) {
		if(!reqContents.toString().contains(contentType)) {
			reqContents.append("\nContent-type: " + contentType);	
		}
	}
		
	public void setConnectionType(String connectionType) {
		if(!reqContents.toString().contains(connectionType)) {
			reqContents.append("\nConnection-type: " + connectionType);		
		}
	}
	
	public void insertContent(String content) {
		if(!bodyContentInserted) {
			reqContents.append("\n\n" + content);
			bodyContentInserted = true;
		}
		
		else {
			reqContents.append("\n" + content);
		}
	}
	
	public String toString() {
		return reqContents.toString();
	}
}
