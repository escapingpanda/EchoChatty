import java.net.*;
import java.io.*;

public class ChatClient {
	static String addr = "localhost";
	static int portnr = 5678;
	
	private void argsHandler(String[] args) throws NumberFormatException{
		try{
			if(args.length == 1){
				nickname = args[0];
			}
			if(args.length == 2){
				nickname = args[0];
				if((args[1].substring(0, 1).equals(":")) && (1000 < Integer.parseInt(args[1].substring(1, args[1].length()))) && (Integer.parseInt(args[1].substring(1, args[1].length())) < 65536)){
						portnr = Integer.parseInt(args[1].substring(1, args[1].length()));
				}
				if(!args[1].substring(0, 1).equals(":")){
					addr = args[1];
				}
			}
			if(args.length == 3 ){
				nickname = args[0];
				addr = args[1];
				if(args[2].substring(0, 1).equals(":")){
					portnr = Integer.parseInt(args[2].substring(1, args[2].length()));
				}
			}
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		argsHandler(args);
		new ListenClient(addr, portnr).start();
		new SendClient(addr, portnr).start();	
	}

}
