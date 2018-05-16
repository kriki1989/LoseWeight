package db_Connection;

import java.sql.Connection; //must
import java.sql.DriverManager; //must
import java.sql.PreparedStatement; //maybe
import java.sql.ResultSet; //must
import java.sql.SQLException; //must
import java.util.logging.Level;
import java.util.logging.Logger;

import loseWeight.MainApplication;
import objectItems.User;

import java.sql.Timestamp;


public class DB_UserMenu {
	private static final String USERNAME = "kriki1989";
	private static final String PASS = "1234";
	private static final String MYSQLURL = "jdbc:mysql://localhost/loseweightdb?allowMultiQueries=true";

	public static void updatePass(String password) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String sqlUpdateData = "UPDATE user SET password = '" + password + "';";
			stm = conn.prepareStatement(sqlUpdateData);
			stm.executeUpdate();			

		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}

	public static void updateW_H(Double W_H, int choice) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			if (choice==2) {
				String sqlUpdateData = "UPDATE user SET weight = " + W_H + ";";
				stm = conn.prepareStatement(sqlUpdateData);
				stm.executeUpdate();
			}else {
				String sqlUpdateData = "UPDATE user SET height = " + W_H + ";";
				stm = conn.prepareStatement(sqlUpdateData);
				stm.executeUpdate();
			}

		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}

	public static void updateAge(int age) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String sqlUpdateData = "UPDATE user SET age = " + age + ";";
			stm = conn.prepareStatement(sqlUpdateData);
			stm.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}

	public static void updateGender(String gen) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String sqlUpdateData = "UPDATE user SET gender = '" + gen + "';";
			stm = conn.prepareStatement(sqlUpdateData);
			stm.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}

	public static void sendProgressOthers(Timestamp nowDate, String messageBMI) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			int senderID = MainApplication.onlineUser.getUserID();
			String selectUsers = "SELECT userID FROM user WHERE userID != " + MainApplication.onlineUser.getUserID() + ";";
			stm = conn.prepareStatement(selectUsers);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				int receiverID = rs.getInt(1);
				String sendMessage = "INSERT INTO message (dateSubmission,senderID,receiverID, messageData) VALUES ('" + nowDate + "', " + senderID + ", " + receiverID + ", '" + messageBMI + "');";
				stm = conn.prepareStatement(sendMessage);
				stm.executeUpdate();
			}
		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}

	public static User findUsernameInBD(String username) {
		Connection conn = null;
		PreparedStatement stm = null;
		try { //1

			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			User existUser = DB_LoginScreen.UserFound(username, conn, stm);
			if (existUser!=null)
				return existUser;
		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
		return null;
	}// end method

	public static void sendMessage(Timestamp nowDate, int senderID, int receiverID, String privateMessage) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String sendMessage = "INSERT INTO message (dateSubmission,senderID,receiverID, messageData) VALUES ('" + nowDate + "', " + senderID + ", " + receiverID + ", '" + privateMessage + "');";
			stm = conn.prepareStatement(sendMessage);
			stm.executeUpdate();
			System.out.println("Message sent successfully");
		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1	
	}
	
	public static void viewReceivedMessages(int receiverID) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String viewReceivedMessages = "SELECT message.dateSubmission,user.username, message.messageData FROM message INNER JOIN user ON message.senderID = user.userID WHERE receiverID = " + receiverID + ";";
			stm = conn.prepareStatement(viewReceivedMessages);
			ResultSet rs = stm.executeQuery();
			if (!rs.next())
				System.out.println("You have received no messages!");
			else {
				rs.beforeFirst();
				System.out.println("Your receiver messages are:");
				System.out.println("------------- ");
				int i=1;
				while (rs.next()) {
					Timestamp date = rs.getTimestamp(1);
					String sender = rs.getString(2);
					String message = rs.getString(3);
					System.out.println("Message " +i);
					System.out.println("Date: " +date + ", Sender: " +sender+ ", \n"+message);
					System.out.println("------------- ");
					i++;
				}
			}			
		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1	
	}
	
	public static void viewSentMessages(int senderID) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String viewReceivedMessages = "SELECT message.dateSubmission,user.username, message.messageData FROM message INNER JOIN user ON message.receiverID = user.userID WHERE senderID = " + senderID + ";";
			stm = conn.prepareStatement(viewReceivedMessages);
			ResultSet rs = stm.executeQuery();
			if (!rs.next())
				System.out.println("You have received no messages!");
			else {
				rs.beforeFirst();
				System.out.println("Your sent messages are:");
				System.out.println("------------- ");
				int i=1;
				while (rs.next()) {
					Timestamp date = rs.getTimestamp(1);
					String sender = rs.getString(2);
					String message = rs.getString(3);
					System.out.println("Message " +i);
					System.out.println("Date: " +date + ", Sender: " +sender+ ", \n"+ message);
					System.out.println("------------- ");
					i++;
				}
			}			
		} catch (SQLException ex) {
			Logger.getLogger(DB_UserMenu.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1	
	}



}// end of class