import java.net.*;
import java.io.*;

public class ListenClient extends Thread {
	private static String addr = "localhost";
	private static int portnr = 5678;
	private static BufferedReader in;
	public ListenClient(String addr, int portnr){
		this.addr = addr;
		this.portnr = portnr;
	}

	public void run(){
		String msg = "";
		try{
			Socket s = new Socket(addr, portnr);
	        	in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.println("Listener is running ");
			while(true){		
				msg = in.readLine();
				System.out.println(msg);
				if(msg.equals("BYE")){
					break;				
				}
			}
			in.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
/*
	public static void main(String[] args){
		String msg = "";
		try{
			Socket s = new Socket(addr, portnr);
	        	in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.println("Listener is running ");
			while(true){		
				msg = in.readLine();
				System.out.println(msg);
				if(msg.equals("BYE")){
					break;				
				}
			}
			in.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}
