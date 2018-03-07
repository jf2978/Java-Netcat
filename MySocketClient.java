
import java.net.*;
import java.io.*;

    public class MySocketClient {

        // INSTANCE VARIABLES
        private int port;
        private String host;
        private Socket client;
        private SocketAddress address;

        // CONSTRUCTORS
        public MySocketClient() throws IOException {
            port = 8080;
            host = "localhost";
            client = new Socket(host, port);
        }

        public MySocketClient(String hostname, int portNum) throws IOException {
            port = portNum;
            host = hostname;
            client = new Socket(host, port);
        }

        // METHODS
        public void start(SocketAddress server) throws IOException{
            client.connect(server);
        }
}
