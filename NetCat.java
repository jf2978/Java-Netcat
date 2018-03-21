import java.io.*;
import java.text.NumberFormat;

import org.apache.commons.cli.*;

public class NetCat {
        /* ====
            STATIC INNER CLASS - NetCatArgs
        */
        private static class NetCatArgs {
            /* ====
                STATIC/INSTANCE VARIABLES
             */
            // Static (class) variables
            private static final Option listen = new Option("l", "listen", false, "starts NetCat in listening mode (server)");
            private static final Option hostname = Option.builder("h").
                    longOpt("hostname")
                    .valueSeparator('=')
                    .desc("Hostname of the machine trying to connect to")
                    .hasArg()
                    .build();
            private static final Option port = Option.builder("p").
                    longOpt("port")
                    .valueSeparator('=')
                    .desc("Port number specifying the process to connect to (within the machine)")
                    .hasArg()
                    .build();
            private static final Options ncOptions = initOptions();
            private static final HelpFormatter help = new HelpFormatter();

            // Non-static (instance) variables
            private CommandLine cmd;

            /* ====
                CONSTRUCTORS
             */
            // String[] Constructor
            private NetCatArgs(String[] args) throws ParseException {
                CommandLineParser parser = new DefaultParser();
                cmd = parser.parse(ncOptions, args);
            }

            // Default (overloaded) constructor
            private NetCatArgs() throws ParseException {
                CommandLineParser parser = new DefaultParser();
                cmd = parser.parse(ncOptions, new String[]{"localhost", "8080"});
            }

            /* ====
                PUBLIC METHODS
             */
            // getHostname(): returns hostname if provided, else defaults to localhost
            private String getHostname() {
                return cmd.getOptionValue("hostname", "localhost");
            }

            // getPort(): returns port if provided, else defaults to 8080
            private int getPort() throws NumberFormatException{
                return Integer.parseInt(cmd.getOptionValue("port", "8080"));
            }

            // isServer(): checks if listen flag has been passed; used to branch into listening (server) mode or not
            private boolean isServer() {
                return cmd.hasOption("listen");
            }

            // hasRequiredArgs():
            //private boolean hasRequiredArgs() {
              //  return cmd.hasOption("hostname") && cmd.hasOption("port");
            //}

            /* ====
                PRIVATE METHODS
             */
            // initOptions(): Prepares Options object for parsing by combining static Option objects
            private static Options initOptions() {
                Options ncOptions = new Options();
                ncOptions.addOption(listen);
                ncOptions.addOption(hostname);
                ncOptions.addOption(port);

                return ncOptions;
            }
        }

        /* ====
            MAIN METHOD
         */
        public static void main(String[] args) {

            // Initialize placeholder variables
            NetCatArgs ncArgs = null;
            String hostname = "";
            int port = 0;

            // Parse Command-line arguments
            try {
                ncArgs = new NetCatArgs(args);
                //if (!ncArgs.hasRequiredArgs()) {
                  //  NetCatArgs.help.printHelp("nc", NetCatArgs.ncOptions );
                   // System.exit(0);
                //}

                hostname = ncArgs.getHostname();
                port = ncArgs.getPort();
            }catch (ParseException | NumberFormatException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.exit(0);
            }

            // Start client/server
            try(MySocketServer server = ncArgs.isServer() ? new MySocketServer(hostname, port): null;
                MySocketClient client = server != null ? server.listen() : new MySocketClient(hostname, port);
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
            } catch (NumberFormatException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
