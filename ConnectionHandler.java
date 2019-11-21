import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConnectionHandler implements Runnable {

    BufferedReader br;

    public ConnectionHandler(InputStream is) {
        this.br = new BufferedReader(new InputStreamReader(is));
    }


    public void run() {
        try {
            String c; 
            while((c = br.readLine()) != null) {
                System.out.println(c);
            }
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}