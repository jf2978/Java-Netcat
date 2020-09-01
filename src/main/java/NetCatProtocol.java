import java.io.*;
import java.nio.charset.StandardCharsets;

public class NetCatProtocol implements AutoCloseable, Runnable {

    /* ====
        PROTOCOL DEFINITION
         - Defines communication between a client-server connection
         - Fixed content length: 50 bytes
     */

    /* ====
        VARIABLES
     */
    // Instance variables
    private BufferedInputStream input;
    private BufferedOutputStream output;

    /* ====
        CONSTRUCTOR(S)
     */
    // Default NetCatProtocol
    public NetCatProtocol(InputStream is, OutputStream os){
        input = new BufferedInputStream(is);
        output = new BufferedOutputStream(os);
    }

    // Buffered by specified size
    public NetCatProtocol(InputStream is, OutputStream os, int size){
        input = new BufferedInputStream(is, size);
        output = new BufferedOutputStream(os, size);
    }

    /* ====
        INTERFACE METHODS
     */
    // AutoCloseable interface -> enables try-with-resources use
    public void close() throws IOException{
        input.close();
        output.close();
    }

    // run(): Asynchronous recv() loop to simulate passively receive messages
    public void run() {
        try{
            while(true){
                recv();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /* ====
        PUBLIC METHODS
    */
    // send(): Turn String messages into byte arrays; immediately writing to the buffer + flushing to the stream
    // NOTE: Using a buffered stream seems moot here if it's immediately flushed - reconsider stream use
    public void send(String msg) throws IOException {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        output.write(bytes);
        output.flush();
    }

    // recv(): Reads the input stream in 50 byte chunks + prints to Standard Output
    public void recv() throws IOException{
        byte[] buf = new byte[50];

        if(input.available() > 0){
            input.read(buf, 0,50);
            System.out.println(new String (buf, "UTF-8"));
        }
    }
}