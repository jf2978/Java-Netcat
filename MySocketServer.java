
import java.net.*;
import java.io.*;

public class MySocketServer implements AutoCloseable, Runnable{

    /* ====
        INSTANCE VARIABLES
     */
    private int port;
    private String host;
    private ServerSocket server;
    private SocketAddress address;

    /* ====
        CONSTRUCTORS
     */
    public MySocketServer(String hostname, int portNum) throws IOException {
        port = portNum;
        host = hostname;
        address = new InetSocketAddress(hostname, portNum);
        server = new ServerSocket();
        server.bind(address);
    }

    // Default, overloaded constructor
    public MySocketServer() throws IOException {
        port = 8080;
        host = "localhost";
        address = new InetSocketAddress(host, port);
        server = new ServerSocket();
        server.bind(address);
    }

    /* ====
        INTERFACE METHODS
     */
    public void close() throws IOException{
        this.server.close();
    }

    public void run() {

    }
    /* ====
        PUBLIC METHODS
     */
    public MySocketClient listen() throws IOException {
        System.out.printf(" %s started listening for a connection on port %d...\n", this.toString(), port);
        Socket sock = server.accept(); // blocking
        System.out.println("*** You are now connected to NetCat 1.0 ***\n");
        return new MySocketClient(sock);
    }

    public SocketAddress getAddress() { return address; }

    public String toString(){
        return String.format("[ Address: %s, Port: %d ]", server.getInetAddress(), port);
    }
}