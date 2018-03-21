
import java.net.*;
import java.io.*;

    public class MySocketClient implements AutoCloseable, Runnable{

        /* ====
            INSTANCE VARIABLES
         */
        private int port;
        private String host;
        private Socket client;
        private SocketAddress address;

        /* ====
            CONSTRUCTOR(S)
         */
        // Default constructor, creates an unconnected client socket
        public MySocketClient() throws IOException {
            client = new Socket();
        }

        // Creates a client socket + connects to the specified port number + host
        public MySocketClient(String hostname, int portNum) throws IOException {
            port = portNum;
            host = hostname;
            address = new InetSocketAddress(host, port);
            client = new Socket(host, port);
        }

        // Creates a MySocketClient as a wrapper for a provided Socket object
        public MySocketClient(Socket sock){
            client = sock;
            address = sock.getLocalSocketAddress();
            port = sock.getPort();
            host = sock.getInetAddress().getHostName();
        }
        /* ====
            INTERFACE METHODS
         */
        @Override
        public void close() throws IOException{
            this.client.close();
        }

        @Override
        public void run(){
            // Placeholder for extensibility purposes
        }

        /* ====
            PUBLIC METHODS
         */
        // For an unconnected MySocketClient; attempts to connect to a server at the given address
        public void connect(SocketAddress server) throws IOException{
            System.out.printf(" Trying to connect to %s on port %d...\n", server.toString(), port);
            this.client.connect(server, 5000); // blocking
            System.out.println("Connected to server");
        }

        public SocketAddress getAddress() {
            return address;
        }

        public OutputStream getOutputStream() throws IOException{
            return client.getOutputStream();
        }

        public InputStream getInputStream() throws IOException{
            return client.getInputStream();
        }

        @Override
        public String toString(){
            return String.format("[ Address: %s, Port: %d ]", client.getInetAddress(), port);
        }
    }