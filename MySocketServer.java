
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException;

public class MySocketServer {
    
    // INSTANCE VARIABLES
    int port;
	String host;
	ServerSocket server;
	SocketAddress address;

    // CONSTRUCTORS
    public MySocketServer() throws IOException{
    	port = 8080;
    	host = "localhost";
        ServerSocket server = new ServerSocket();
        SocketAddress address = new InetSocketAddress(host, port);
        server.bind(address);
    }

    public MySocketServer(String hostname, int portNum) throws IOException{
		port = portNum;
    	host = hostname;
        ServerSocket server = new ServerSocket();
        SocketAddress address = new InetSocketAddress(host, port);
        server.bind(address);
    }

    // METHODS
    public Socket listen() throws IOException{
    	System.out.printf("Starting connection at port %d", port);
    	return server.accept();
    }
		
}
