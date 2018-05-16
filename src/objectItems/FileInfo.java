package objectItems;

import java.util.Date;

public class FileInfo {
	private static int counter;
	private int messageID;
	private Date dateSubmission;
	private String sender;
	private String receiver;
	private String messageData;
	
	//Constructor
	public FileInfo(Date dateSubmission, String sender, String receiver, String messageData) {
		super();
		this.messageID=++counter;
		this.dateSubmission = dateSubmission;
		this.sender = sender;
		this.receiver = receiver;
		this.messageData = messageData;
	}
	
	//toString
	@Override
	public String toString() {
		return "\nMessageID= " + messageID + "\ndateSubmission= " + dateSubmission + ", \nsender= " + sender + ", \nreceiver= " + receiver	+ ", \nmessage= " + messageData;
	}
	
	
	
}
