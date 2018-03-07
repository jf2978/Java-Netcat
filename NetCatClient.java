import java.io.*;

public class NetCatClient {
    public static void main(String[] args){

        // Command-line arguments prompt on error
        if (args.length < 2 ) {
            throw new IllegalArgumentException("Please specify the following:\n1) hostname\n2) port number\n");
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        // try-with-resources
        try(MySocketClient client = new MySocketClient(hostname, port);
            PrintWriter out = new PrintWriter(client.getOutputStream() , true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))){
            client.start(client.getAddress());

            // read + write to established connection
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
