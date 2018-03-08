import java.io.*;
import java.util.Scanner;

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
            Scanner in = new Scanner(new InputStreamReader(client.getInputStream()))){

            System.out.println("*** You are now connected to NetCat 1.0 ***\n");
            // read + write to established connection
            String inputLine;
            while ((inputLine = in.nextLine()) != null) {
                System.out.println("pass#2");
                out.print(inputLine);
            }
            System.out.println("pass#3");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
