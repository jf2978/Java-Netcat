import java.io.IOException;
import java.lang.IllegalArgumentException;

public class MySockets {
    public static void main(String[] args){
    	try{
    		if(args.length == 0){
    			throw new IllegalArgumentException(
                    "Please specify the following:\n 
                    1) listen flag, -l [OPTIONAL]\n
                    2) hostname\n
                    3) port number\n");
    		}

	    	if(args[0].equals("-l")){
	    		System.out.println("SERVER");
	    		MySocketServer server = new MySocketServer();
	    	}else{
	    		System.out.println("CLIENT");
	    	}
    	}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
    	}catch(IOException e){
    		System.out.println(e.getMessage());
    	}
    	
    }
}
