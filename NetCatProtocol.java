import java.io.*;
import java.nio.charset.StandardCharsets;

public class NetCatProtocol implements AutoCloseable, Runnable{

    /* ====
        PROTOCOL DEFINITION
         - Fixed content length: 50 bytes
     */

    /* ====
        INSTANCE VARIABLES
     */
    private BufferedInputStream input;
    private BufferedOutputStream output;

    /* ====
        CONSTRUCTORS
     */
    public NetCatProtocol(InputStream is, OutputStream os){
        input = new BufferedInputStream(is);
        output = new BufferedOutputStream(os);
    }

    public NetCatProtocol(InputStream is, OutputStream os, int size){
        input = new BufferedInputStream(is, size);
        output = new BufferedOutputStream(os, size);
    }

    /* ====
        INTERFACE METHODS
     */
    public void close() throws IOException{
        input.close();
        output.close();
    }

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
    public void send(String msg) throws IOException {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        output.write(bytes);
        output.flush();
    }

    public void recv() throws IOException{
        byte[] buf = new byte[50];

        if(input.available() > 0){
            input.read(buf, 0,50);
            System.out.println(new String (buf, "UTF-8"));
        }
    }
}