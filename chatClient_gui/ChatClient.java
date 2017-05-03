import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClient {
    BufferedReader in;
    PrintWriter out;
    JFrame chatWindow = new JFrame("EchoChatty");
    JTextField msgInputField = new JTextField(50);
    JTextArea chatDisplayField = new JTextArea(12, 50);
    String nickname;
    String addr = "localhost";
    int portnr = 5678;
	private Socket socket;
	
    public ChatClient() {
        chatWindow.getContentPane().add(msgInputField, "North");
        chatWindow.getContentPane().add(new JScrollPane(chatDisplayField), "Center");
        chatWindow.pack();
        chatDisplayField.setEditable(false);
        msgInputField.setEditable(true);
        msgInputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(nickname + ": " + msgInputField.getText());
                msgInputField.setText("");
            }
        });
    }

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

    private void run() throws IOException {
        socket = new Socket(addr, portnr);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            chatDisplayField.append(line + "\n");
        }
    }

    public static void main(String[] args) throws Exception {
        ChatClient cclient = new ChatClient();
        cclient.argsHandler(args);
        System.out.println("Caddr: " + cclient.addr + " CPort: " + cclient.portnr + " CNickname: " + cclient.nickname);
        cclient.chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cclient.chatWindow.setVisible(true);
        cclient.run();
    }
}