package jswerve; 

public class TestingServer {

    public static void main(String[] args) throws InterruptedException {
    	int port = 8000;
    	System.out.println("Starting server at port: " + port);
        
    	BaseHTTPServer bhs = new BaseHTTPServer(port);
    }
}