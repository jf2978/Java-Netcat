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
            PrintWriter output = new PrintWriter(client.getOutputStream() , true);
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Scanner in = new Scanner(System.in)){

            System.out.println("*** You are now connected to NetCat 1.0 ***\n");

            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                System.out.println(inputLine);
                if(in.hasNextLine()){
                    output.print(in.nextLine());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
