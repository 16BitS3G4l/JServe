import java.io.BufferedReader;
import java.io.PrintWriter; 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BaseHTTPServer {
    private int port = 80; 
    private BufferedReader reader;
    private PrintWriter writer;
    private ServerSocket ss;
    private Socket s;

    public void initServerSocket() {
        try {
            ss = new ServerSocket(port);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void acceptConectionsAndThrowToHandlers() {
        try {
            while (true) {
                s = ss.accept();
                (new Thread(new ConnectionHandler(s.getInputStream()))).start();
            }

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BaseHTTPServer(int port) {
        this.port = port;
        initServerSocket();
        acceptConectionsAndThrowToHandlers();
    }

    public BaseHTTPServer() {
        initServerSocket();
        acceptConectionsAndThrowToHandlers();
    }


}