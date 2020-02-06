package jswerve; 

public class TestingServer {

    public static void main(String[] args) throws InterruptedException {
    	int port = 8080;
    	System.out.println("Starting server at port: " + port);
        
    	BaseHTTPServer bhs = new BaseHTTPServer(8080);
    	(new Thread(bhs)).start();
    }
}