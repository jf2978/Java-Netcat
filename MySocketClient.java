
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
            CONSTRUCTORS
         */
        public MySocketClient() throws IOException {
            port = 8080;
            host = "localhost";
            address = new InetSocketAddress(host, port);
            client = new Socket(host, port);
        }

        public MySocketClient(String hostname, int portNum) throws IOException {
            port = portNum;
            host = hostname;
            address = new InetSocketAddress(host, port);
            client = new Socket(host, port);
        }

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
            // Placeholder for extensible functionality in the future
        }

        /* ====
            PUBLIC METHODS
         */
        public void connect() throws IOException{
            SocketAddress server = this.address;
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
