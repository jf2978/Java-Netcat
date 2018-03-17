import java.io.*;
import java.nio.charset.StandardCharsets;

public class NetCatProtocol implements AutoCloseable{

    /* ====
        PROTOCOL DEFINITION
         - Fixed content length: 50 bytes
         - End-of-Request Marker: <<END>>
     */

    /* ====
        CLASS (STATIC) VARIABLES
     */
   // private static final String endOfMessage = "<<END>>";
    //private static final byte[] eom = endOfMessage.getBytes();

    /* ====
        INSTANCE VARIABLES
     */
    private BufferedInputStream input;
    private BufferedOutputStream output;

    /* ====
        CONSTRUCTOR(S)
     */
    public NetCatProtocol(InputStream is, OutputStream os){
        input = new BufferedInputStream(is, 50);
        output = new BufferedOutputStream(os, 50);
    }

    /* ====
        INTERFACE METHODS
     */
    public void close() throws IOException{
        input.close();
        output.close();
    }

    /* ====
        PUBLIC METHODS
    */
    public void send(String msg) throws IOException{
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        output.write(bytes);
    }

    public void recv() throws IOException{
        byte[] buf = new byte[50];
        while(input.available() > 0){
            output.flush();
            input.read(buf, 0,50);
        }
        System.out.println(new String(buf,"UTF-8"));
    }
}
