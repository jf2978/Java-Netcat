import java.io.*;
import org.apache.commons.cli.*;

public class NetCat {
    /* ====
        STATIC INNER CLASS - NetCatArgs
     */
    private static class NetCatArgs{

        // Static (class) variables
        private static final  Option listen = new Option("l", "listen", false, "starts NetCat in listening mode (server)");
        private static final Option hostname = Option.builder("h").
                longOpt("host")
                .required(true)
                .valueSeparator()
                .desc("Hostname of the machine trying to connect to")
                .build();
        private static final Option port = Option.builder("p").
                longOpt("port")
                .required(true)
                .valueSeparator()
                .desc("Port number specifying the process to connect to (within the machine)")
                .build();
        private static final Options ncOptions = initOptions();
        private static final HelpFormatter help = new HelpFormatter();

        // Non-static (instance) variables
        private CommandLine cmd;

        // Constructor
        private NetCatArgs(String[] args) throws ParseException{
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(ncOptions, args);
        }

        // Public methods
        private String getHostname(){
            return this.hasOption("hostname") ? ncOptions.getOption("hostname").getValue():"";
        }

        private int getPort(){
            String port = this.hasOption("port") ? ncOptions.getOption("port").getValue():"";
            return Integer.parseInt(port);
        }

        private boolean isServer(){
            return cmd.hasOption("l");
        }

        private boolean isValid(){
            return cmd.hasOption("hostname") && cmd.hasOption("port");
        }

        // Private methods
        private static Options initOptions(){
            Options ncOptions = new Options();
            ncOptions.addOption(listen);
            ncOptions.addOption(hostname);
            ncOptions.addOption(port);

            return ncOptions;
        }
    }

    public static void main(String[] args){

        // Command-line argument parsing
        try {
            NetCatArgs nc = new NetCatArgs(args);
            if (!nc.isValid()) {
                NetCatArgs.help.printHelp("Netcat - Java", NetCatArgs.ncOptions, true);
                System.exit(0);
            }

            String hostname = nc.getHostname();
            int port = nc.getPort();

            if(nc.isServer()){
                MySocketServer server = new NetCatServer(hostname, port);
                server.start();
            }else{
                MySocketClient client = new NetCatClient(hostname, port);
                client.start();
            }
        } catch(ParseException e){
            System.out.println(e.getMessage());
        }

            // Connect client to listening server
            try(MySocketClient client = new MySocketClient(hostname, port);
                NetCatProtocol nc = new NetCatProtocol(client.getInputStream(), client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in))){

                System.out.println("*** You are now connected to NetCat 1.0 ***\n");

                // Asynchronously read
                Thread recv = new Thread(nc);
                recv.start();

                // Continuously write from std in
                String inputLine;
                while ((inputLine = in.readLine()) != null){
                    if(inputLine.equals("exit")) {
                        System.out.println("-   Client Exiting   -");
                        System.exit(0);
                    }

                    nc.send(inputLine);
                }
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }

        private void server(){

        }

        private void client(){

        }

    }
