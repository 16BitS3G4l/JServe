package main.java.jswerve; 

public class TestingServer {

    public static void main(String[] args)  {

		int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
    	System.out.println("Starting server at port: " + port);
        
		BaseHTTPServer bhs = new BaseHTTPServer(port);
		(new Thread(bhs)).start();
	}
}