public class NetCat {

    public static void main(String[] args){

        // Command-line arguments prompt on error
        if (args.length < 3 || !args[0].equals("-l")) {
            throw new IllegalArgumentException("Please specify the following arguments:\n1) listen flag, -l\n2) hostname\n3) port number\n");
        }

        String hostname = args[1];
        int port = Integer.parseInt(args[2]);
    }
}
