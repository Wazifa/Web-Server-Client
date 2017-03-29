  
package webserver;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 *
 * @author Wazifa
 */
final class HttpRequest implements Runnable{
 
    
    final static String CRLF = "\r\n";
    Socket socket;
 
 // Constructor
    public HttpRequest(Socket socket) throws Exception
    {
        this.socket = socket;
    }
 
 // Implement the run() method of the Runnable interface.
    public void run()
    {
        try {
            processRequest();
            } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
 
 private void processRequest() throws Exception
 {
    //Get reference to the socket's input and output streams
    InputStream is = socket.getInputStream();
    DataOutputStream os = new DataOutputStream(socket.getOutputStream());
  
    /*
    Set up input stream filters.
    Request line of the HTTP request message 
    Display the request line
    */
    
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String requestLine = br.readLine();
    System.out.println();
    System.out.println(requestLine);
    
    
    //Get and display header lines
    String headerLine = null;
    while ((headerLine = br.readLine()).length() != 0) 
    { 
        System.out.println(headerLine);
    }
    
   //Extract filename from request line
    StringTokenizer tokens = new StringTokenizer(requestLine);
    tokens.nextToken();  // skip over method “GET”
    String fileName = tokens.nextToken();
    
    // Prepend a “.” so that file request is within the current directory.
    fileName = "." + fileName;
   
   // Open the requested file.
    FileInputStream fis = null;
    boolean fileExists = true;
    try 
    {
        fis = new FileInputStream(fileName);
    } 
    catch (FileNotFoundException e) 
    {
        fileExists = false;
    }   
 
   //Construct the response message
    String statusLine = null; 
    String contentTypeLine = null;
    String entityBody = null;
    if (fileExists) 
    {
        statusLine = "HTTP/1.1 200 OK: ";
        contentTypeLine = "Content-Type: " + contentType(fileName) + CRLF;
    } 
    
    else 
    {
        statusLine = "HTTP/1.1 404 Not Found: ";
        contentTypeLine = "Content-Type: text/html" + CRLF;
        entityBody = ("HTTP/1.0 404 Not Found\r\n"+ fileName+" not found\n");
        
    }
 
   /*
    Send the status line
    Send the content type line.
    Send a blank line to indicate the end of the header lines.
    */
    
    os.writeBytes(statusLine); 
    os.writeBytes(contentTypeLine);
    os.writeBytes(CRLF);
   
   // Send the entity body.
    if (fileExists) 
    {
        sendBytes(fis, os);
        fis.close();
    } 
    else 
    {
        os.writeBytes(entityBody);
    }
    
    //Close streams and socket.
    os.close(); 
    br.close();
    socket.close();
   
 }

 private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception
{
   // Construct a 1K buffer to hold bytes on their way to the socket.
   byte[] buffer = new byte[1024];
   int bytes = 0;
 
   // Copy requested file into the socket’s output stream.
    while((bytes = fis.read(buffer)) != -1 ) 
    {
        os.write(buffer, 0, bytes);
    }   
}
 
private static String contentType(String fileName)
{
    if(fileName.endsWith(".htm") || fileName.endsWith(".html"))
        return "text/html";
    if(fileName.endsWith(".jpg"))
        return "text/jpg";
    if(fileName.endsWith(".gif"))
        return "text/gif";
    return "application/octet-stream";
 }

}
    

