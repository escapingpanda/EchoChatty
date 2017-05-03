import java.net.*;
import java.io.*;

public class SendClient extends Thread {
	private static String addr = "localhost";
	private static int portnr = 5678;
	private static BufferedReader inClient = new BufferedReader(new InputStreamReader(System.in));
	private static PrintWriter out;
	public SendClient(String addr, int portnr){
		this.addr = addr;
		this.portnr = portnr;
	}
	
	public void run(){
		String msg = "";
		try{
			Socket s = new Socket(addr, portnr);
			out = new PrintWriter(s.getOutputStream(), true);
			System.out.println("Sender is running");
			while(true){		
				msg = inClient.readLine();
				System.out.println("Client input: " + msg);
				if(msg.equals("quit")){
					break;				
				}
				out.println(msg);
			}
			inClient.close();
			out.flush();
			out.close();
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
			out = new PrintWriter(s.getOutputStream(), true);
			System.out.println("Sender is running");
			while(true){		
				msg = inClient.readLine();
				System.out.println("Client input: " + msg);
				if(msg.equals("quit")){
					break;				
				}
				out.println(msg);
			}
			inClient.close();
			out.flush();
			out.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}
