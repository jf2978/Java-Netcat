
import java.net.*;
import java.io.*;

public class MySocketServer implements AutoCloseable{

    // INSTANCE VARIABLES
    private int port;
    private String host;
    private ServerSocket server;
    private SocketAddress address;

    // CONSTRUCTORS
    public MySocketServer() throws IOException {
        port = 8080;
        host = "localhost";
        address = new InetSocketAddress(host, port);
        server = new ServerSocket();
        server.bind(address);
    }

    public MySocketServer(String hostname, int portNum) throws IOException {
        port = portNum;
        host = hostname;
        address = new InetSocketAddress(hostname, portNum);
        server = new ServerSocket();
        server.bind(address);
    }

    // INTERFACE METHODS
    public void close() throws IOException{
        this.server.close();
    }

    // PUBLIC METHODS
    public MySocketClient start() throws IOException {
        System.out.printf(" %s started listening for a connection on port %d...\n", this.toString(), port);
        Socket sock = server.accept(); // blocking
        System.out.println("Client connected");
        return new MySocketClient(sock);
    }

    public String toString(){
        return String.format("[ Address: %s, Port: %d ]", server.getInetAddress(), port);
    }
}