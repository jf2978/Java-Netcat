import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.net.Socket;
import java.io.*;

public class NetCatServer {
    public static void main(String[] args){
        // Command-line arguments prompt on error
        if (args.length < 3 || args[0].equals("-l")) {
            throw new IllegalArgumentException("Please specify the following:\n1) listen flag, -l\n2) hostname [REQUIRED]\n3) port number [REQUIRED] \n");
        }

        MySocketServer server;
        String hostname;
        int port;

        try{
            hostname = args[1];
            port = Integer.parseInt(args[2]);
            server = new MySocketServer(hostname, port);
            Socket connection = server.start();

            //read + write
            PrintWriter out = new PrintWriter(connection.getOutputStream() , true);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }

            out.close();
            in.close();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred while listening for a connection!");
        }
    }
}
