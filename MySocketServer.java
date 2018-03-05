
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException;

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
    	System.out.printf("%s started listening for a connection on port %d", server, port);  
        StringBuilder sb = new StringBuilder(".");
        Socket sock = server.accept();
        while(sock == null){
            for(int i = 0; i < 100; i++){
                // simulate waiting 
            }
            System.out.print(sb.toString());
            sb.append(".");
            sock = server.accept();
        }
        return sock;
    }	

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }
}
