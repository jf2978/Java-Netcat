
import java.net.*;
import java.io.*;

public class MySocketServer {

    // INSTANCE VARIABLES
    private int port;
    private String host;
    private ServerSocket server;
    private SocketAddress address;

    // CONSTRUCTORS
    public MySocketServer() throws IOException {
        port = 8080;
        host = "localhost";
        server = new ServerSocket();
        address = new InetSocketAddress(host, port);
        server.bind(address);
    }

    public MySocketServer(String hostname, int portNum) throws IOException {
        port = portNum;
        host = hostname;
        server = new ServerSocket();
        address = new InetSocketAddress(host, port);
        server.bind(address);
    }

    // METHODS
    public Socket start() throws IOException {
        System.out.printf(" %s started listening for a connection on port %d...\n", server, port);
        Socket sock = server.accept(); // blocking, throws IOException
        System.out.println("Client connected");
        return sock;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public SocketAddress getAddress(){
        return address;
    }
}

