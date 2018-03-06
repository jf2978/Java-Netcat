
import java.net.*;
import java.io.*;
import java.lang.Thread;

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
    	// listen for client to connect
        System.out.printf(" %s started listening for a connection on port %d", server, port);
        StringBuilder sb = new StringBuilder(".");
        Socket sock = server.accept();
        // do stuff once client connects
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        System.out.println("Client connected");
        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        return sock;
    }	

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }
}
