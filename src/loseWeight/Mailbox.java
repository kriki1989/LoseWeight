package loseWeight;

import java.util.ArrayList;

import db_Connection.DB_AdminMenu;
import db_Connection.DB_UserMenu;
import objectItems.FileInfo;
import objectItems.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;

public class Mailbox {

	private static final String FILENAME = "messages.txt";
	private static final String sp = File.separator;
	public static ArrayList <FileInfo> messages = new ArrayList <FileInfo> ();

	public static void sendProgressOthers() {
		Timestamp nowDate = new Timestamp(System.currentTimeMillis());
		String messageBMI = MainApplication.onlineUser.getMyBMI().toString();
		messageBMI = messageBMI.replace("'","''");
		DB_UserMenu.sendProgressOthers(nowDate, messageBMI);
		System.out.println("Progress sent successfully to other users.");
	}

	public static void sendMessage(){
		Timestamp nowDate = new Timestamp(System.currentTimeMillis());
		int senderID = MainApplication.onlineUser.getUserID();
		DB_AdminMenu.viewUsers();
			System.out.println("Who is the receiver (username): ");
			String receiverUsername = MainApplication.input.nextLine();
			User receiver = DB_UserMenu.findUsernameInBD(receiverUsername);
			if (receiver == null) {
				System.out.println("User does not exist!");
			}else {
				int receiverID = receiver.getUserID();
				boolean complete=false;
				do {
					System.out.println("Type your message (limited to 250 char): ");
					String privateMessage = MainApplication.input.nextLine();
					int messLen = privateMessage.length();
					if (messLen<=250) {
						privateMessage = privateMessage.replace("'","''");
						DB_UserMenu.sendMessage(nowDate,senderID,receiverID,privateMessage);
						FileInfo message = new FileInfo (nowDate, MainApplication.onlineUser.getUsername(), receiver.getUsername(), privateMessage);
						messages.add(message);
						writeMessageToFile(message.toString());
						complete = true;
					}else {
						System.out.println("Message is larger than 250 char. It contains " + messLen + " characters.");
					}
				}while (!complete);
			}
	}

	public static void receivedMessages() {
		int receiverID = MainApplication.onlineUser.getUserID();
		DB_UserMenu.viewReceivedMessages(receiverID);
	}
	
	public static void showSentMessages() {
		int senderID = MainApplication.onlineUser.getUserID();
		DB_UserMenu.viewSentMessages(senderID);
	}
	
	public static void writeMessageToFile(String message) {
		String jarPath = "";

		try {
			jarPath = URLDecoder.decode(MainApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String completePath = jarPath.substring(0, jarPath.lastIndexOf("/")) + sp + FILENAME;
		File f = new File(completePath);
		BufferedWriter writer;
		try {
			if (!f.exists() && !f.createNewFile()) {
				System.out.println("There is an error in creating the file in the selected path.");
			} else {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(completePath,true), "UTF8"));
				writer.write(message + "\n\n");
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


}
