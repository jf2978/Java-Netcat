import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.net.Socket;
import java.io.*;

public class NetCatServer {
    public static void main(String[] args){

        // Command-line arguments prompt on error
        if (args.length < 3 || !args[0].equals("-l")) {
            throw new IllegalArgumentException("Please specify the following:\n1) listen flag, -l\n2) hostname\n3) port number\n");
        }

        String hostname = args[1];
        int port = Integer.parseInt(args[2]);

        // try-with-resources statement
        try(MySocketServer server = new MySocketServer(hostname, port);
                Socket connection = server.start();
                PrintWriter out = new PrintWriter(connection.getOutputStream() , true);
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))){

            // read + write to established connection
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
