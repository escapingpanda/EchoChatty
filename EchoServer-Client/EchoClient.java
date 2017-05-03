import java.net.*;
import java.io.*;

public class EchoClient {
	static String addr = "localhost";
	static int portnr = 5678;
	
	private static void argsHandler(String[] args) throws NumberFormatException{
		try{
			if(args.length == 1){
				if((args[0].substring(0, 1).equals(":")) && (1000 < Integer.parseInt(args[0].substring(1, args[0].length()))) && (Integer.parseInt(args[0].substring(1, args[0].length())) < 65536)){
						portnr = Integer.parseInt(args[0].substring(1, args[0].length()));
				}
				if(!args[0].substring(0, 1).equals(":")){
					addr = args[0];
				}
			}
			if(args.length == 2 ){
				if(args[1].substring(0, 1).equals(":")){
					addr = args[0];
					portnr = Integer.parseInt(args[1].substring(1, args[1].length()));
				}
			}
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		argsHandler(args);
		String msg = "";
		try{
			Socket s = new Socket(addr, portnr);
			BufferedReader inClient = new BufferedReader(new InputStreamReader(System.in));
	        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	        DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println("Connected: " + s);
			while(!msg.equals("quit")){
				msg = inClient.readLine();
				if(msg.equals("quit")){
					break;
				}
				out.writeBytes(msg + '\n');		
				System.out.println(in.readLine());
			}
			inClient.close();
			in.close();
			out.flush();
			out.close();
			s.close();
		}catch(UnknownHostException uhe){
			uhe.printStackTrace();
		}
		
		
	}

}