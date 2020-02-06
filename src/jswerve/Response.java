package jswerve;

public class Response {
	
	private StringBuilder respContents;
	private boolean bodyContentInserted = false;
	
	public Response(STATUS s) {
		respContents = new StringBuilder(30);
		
		if(s.equals(STATUS.OK)) {
			respContents.append("HTTP/1.1 200");
		}
		
		else if(s.equals(STATUS.NOT_FOUND)) {
			respContents.append("HTTP/1.1 404");
		}
	}
	
	public void setContentType(String contentType) {
		if(!respContents.toString().contains(contentType)) {
			respContents.append("\nContent-type: " + contentType);	
		}
	}
		
	public void setConnectionType(String connectionType) {
		if(!respContents.toString().contains(connectionType)) {
			respContents.append("\nConnection-type: " + connectionType);		
		}
	}
	
	public void insertContent(String content) {
		if(!bodyContentInserted) {
			respContents.append("\n\n" + content);
			bodyContentInserted = true;
		}
		
		else {
			respContents.append("\n" + content);
		}
	}
	
	public String toString() {
		return respContents.toString();
	}
}
