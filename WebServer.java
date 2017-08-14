
package webserver;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Wazifa
 */
public class WebServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        
        // Open a socket
        ServerSocket WebSocket = new ServerSocket(8080);
       
        while (true) {
            // Listen for a TCP connection request.
            Socket connectionSocket =  WebSocket.accept();

            
//            //Process HTTP request message
            Thread thread = new Thread(new HttpRequest(connectionSocket));
            thread.start(); 
     }
    }
    
}
