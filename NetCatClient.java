import java.io.*;

public class NetCatClient {
    public static void main(String[] args){

        String hostname = "localhost";
        int port = 8080;

        /* ====
            Command-line argument handling
         */
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

        /* ====
            Connect client to listening server
         */
        try(MySocketClient client = new MySocketClient(hostname, port);
            NetCatProtocol nc = new NetCatProtocol(client.getInputStream(), client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in))){

            System.out.println("*** You are now connected to NetCat 1.0 ***\n");

            /* ====
                Asynchronously read
             */
            Thread recv = new Thread(nc);
            recv.start();

            /* ====
                Continuously write from std in
             */
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                if(inputLine.equals("exit")) {
                    System.out.println("-   Client Exiting   -");
                    System.exit(0);
                }

                nc.send(inputLine);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}