import java.lang.IllegalArgumentException;
import java.io.*;

public class NetCatServer {
    /* ====
        INSTANCE VARIABLES
     */
    private Socket
    public static void main(String[] args){

        String hostname = "localhost";
        int port = 8080;

        // Command-line argument handling
        try{
            if (args.length < 2) {
                throw new IllegalArgumentException("Please specify the following arguments:\n" +
                        "1) hostname [ String ]\n" +
                        "2) port number [ Integer ]\n");
            }

            hostname = args[0];
            port = Integer.parseInt(args[1]);

        } catch(NumberFormatException e){
            System.out.println("Port Number Error! Please enter a valid integer\n");
            System.exit(1);
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        // Start server
        try(MySocketServer server = new MySocketServer(hostname, port);
            MySocketClient client = server.listen();
            NetCatProtocol nc = new NetCatProtocol(client.getInputStream(), client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in))){

            // Asynchronous read
            Thread recv = new Thread(nc);
            recv.start();

            // Continuously read from stdin (new line delimiter implicit through BufferedReader use)
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                if(inputLine.equals("exit")) {
                    System.out.println("-   Server Exiting   -");
                    System.exit(0);
                }

                nc.send(inputLine);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
