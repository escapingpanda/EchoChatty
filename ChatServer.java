import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
	static boolean dbug = false;
	static int portnr = 5678;
	static HashSet<PrintWriter> outputs = new HashSet<PrintWriter>();
	
	private static class Helper extends Thread{
		private Socket socket;
		public Helper(Socket socket){
			this.socket = socket;
		}
		public void run(){
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				outputs.add(out);
				while(true){
					String msg = in.readLine();
					if(msg == null){
						in.close();
						out.flush();
						out.close();
						break;
					}
					if(dbug == true){
						System.out.println(msg);
					}
					for(PrintWriter pw : outputs){
						System.out.println("Sending message via " + pw);
						pw.println( msg );
					}
				}
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}finally{
				try{
					socket.close();
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}	
		}
	}
	
	private static void argsHandler(String[] args) throws NumberFormatException{
		try{
			if(args.length == 1){
				if(Character.isDigit(args[0].charAt(0))){
					if((1000 < Integer.parseInt(args[0])) && (Integer.parseInt(args[0]) < 65536)){
						portnr = Integer.parseInt(args[0]);
					}	
				}
				if(args[0].equals("-debug")){
					dbug = true;
				}	
			}
			if(args.length == 2 && (((String)args[0]).equals("-debug")) && (1000 < Integer.parseInt(args[1])) && (Integer.parseInt(args[1]) < 65536)){
				dbug = true;
				portnr = Integer.parseInt(args[1]);
			}
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		argsHandler(args);
		ServerSocket server = new ServerSocket(portnr);
		try{
			while(true){
				Socket currSock = server.accept();
				System.out.println("Connection from: " + currSock);
				new Helper(currSock).start();
			}
		}finally{
			server.close();
		}
	}
}
