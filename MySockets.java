import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.net.Socket;

public class MySockets {
    public static void main(String[] args){
    	if(args.length < 2){
            throw new IllegalArgumentException("Please specify the following:\n 1) listen flag, -l [OPTIONAL]\n 2) hostname [REQUIRED] \n 3) port number [REQUIRED] \n");
        }

        String hostname = args[1];
        int port = Integer.parseInt(args[2]);
        try{
	    	if(args[0].equals("-l")){
	    		MySocketServer server = new MySocketServer(hostname, port);
                Socket client = server.start();
	    	}else{
	    		System.out.println("CLIENT");
	    	}
    	}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
    	}catch(IOException e){
    		System.out.println(e.getMessage());
        }finally{
            // close streams to prevent resource leaks
        }
    }
}
