import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.io.*;
import java.util.Scanner;

public class NetCatServer {
    public static void main(String[] args){

        // Command-line arguments prompt on error
        if (args.length < 3 || !args[0].equals("-l")) {
            throw new IllegalArgumentException("Please specify the following arguments:\n1) listen flag, -l\n2) hostname\n3) port number\n");
        }

        String hostname = args[1];
        int port = Integer.parseInt(args[2]);

        // try-with-resources
        try(MySocketServer server = new MySocketServer(hostname, port);
                MySocketClient client = server.start();
                PrintWriter output = new PrintWriter(client.getOutputStream() , true);
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                Scanner in = new Scanner(System.in)){

            System.out.printf("*** Successfully connected to user: %s ***\n", client.toString());

            String inputLine;
            while (in.hasNextLine()){
                System.out.println(inputLine);
                if(inputLine.equals("see ya!")){
                    output.print("see ya later!");
                    break;
                }
                while ((inputLine = input.readLine()) != null) {
                    System.out.println(inputLine);
                    if(in.hasNextLine()){
                        output.print(in.nextLine());
                    }
                }
            }
            System.out.println("pass#3");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
