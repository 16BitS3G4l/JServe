import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;

public class TestingClient {

    public static void main(String[] args) throws Exception {
        int port = 8000;
        String hostName = InetAddress.getLocalHost().toString();

        Socket s = new Socket(hostName, port);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pr = new PrintWriter(s.getOutputStream(), true);
        
        pr.println("testing... 123.... testing?");

        s.close();
    }
} 