package db_Connection;

import java.sql.Connection; //must
import java.sql.DriverManager; //must
import java.sql.PreparedStatement; //maybe
import java.sql.ResultSet; //must
import java.sql.SQLException; //must
import java.util.logging.Level;
import java.util.logging.Logger;

import loseWeight.MainApplication;
import menus.LoginScreen;


public class DB_AdminMenu {
	private static final String USERNAME = "kriki1989";
	private static final String PASS = "1234";
	private static final String MYSQLURL = "jdbc:mysql://localhost/loseweightdb?allowMultiQueries=true";

	public static void unlockPasswords() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String proceed = "N";
			do {
				String findLockedUsers = "SELECT userID, username FROM user WHERE user.isLocked=1;";
				stm = conn.prepareStatement(findLockedUsers);
				ResultSet rs = stm.executeQuery();

				if (rs.next()) {
					rs.beforeFirst();
					while (rs.next()) {
						System.out.println("Locked userID:" + rs.getInt(1) +", Username: "+ rs.getString(2));
					}
					int typedID;
					boolean found = false;
					do {
						try {
							System.out.println("Which user would you like to unlock? Type userID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
					if (found) {
						String unlockUser = "UPDATE user SET password='0000', isLocked=0, attempts=3 WHERE userID=" + typedID + ";";
						stm = conn.prepareStatement(unlockUser);
						stm.executeUpdate();
						System.out.println("User unlocked successfully");
						do {
							System.out.println("Do you want to unlock another user? [Y/N]: ");
							proceed = MainApplication.input.nextLine();
						} while (!proceed.equals("Y") && !proceed.equals("N"));
					}else {
						System.out.println("User not found.");
					}

				}else {
					System.out.println("No user to unlock.");
				}
			}while (proceed.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void viewMessage() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String viewMessages = "SELECT messageID, dateSubmission, (SELECT user.username FROM user WHERE user.userID=message.receiverID) AS receiver, (SELECT user.username FROM user WHERE user.userID=message.senderID) AS sender, messageData FROM message;";
			stm = conn.prepareStatement(viewMessages);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				rs.beforeFirst();
				System.out.println("----------------------------------");
				while (rs.next()) {
					System.out.println("MessageID: " +rs.getInt(1)+ ", Date: "+rs.getTimestamp(2)+", Sender: '"+rs.getString(4)+"', Receiver: '"+rs.getString(3)+"', \n'"+ rs.getString(5)+"';");
					System.out.println("----------------------------------");
				}
			}else {
				System.out.println("There are no messages.");
			}

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void editMessage() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String viewMessages = "SELECT messageID, dateSubmission, (SELECT user.username FROM user WHERE user.userID=message.receiverID) AS receiver, (SELECT user.username FROM user WHERE user.userID=message.senderID) AS sender, messageData FROM message;";
				stm = conn.prepareStatement(viewMessages);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("MessageID: " +rs.getInt(1)+ ", Date: "+rs.getTimestamp(2)+", Sender: '"+rs.getString(4)+"', Receiver: '"+rs.getString(3)+"', \n'"+ rs.getString(5)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which message would you like to edit? Type messageID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}else {
					System.out.println("There are no messages.");
				}
				if (found) {
					System.out.println("Edit the message: ");
					String typedmessage = MainApplication.input.nextLine();
					typedmessage = typedmessage.replace("'", "''");
					String editMessage = "UPDATE message SET messageData='"+ typedmessage +"' WHERE messageID=" + typedID + ";";
					stm = conn.prepareStatement(editMessage);
					stm.executeUpdate();
					System.out.println("Message edited successfully");
					do {
						System.out.println("Do you want to edit another message? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("Message not found.");
				}

			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void deleteMessage() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String viewMessages = "SELECT messageID, dateSubmission, (SELECT user.username FROM user WHERE user.userID=message.receiverID) AS receiver, (SELECT user.username FROM user WHERE user.userID=message.senderID) AS sender, messageData FROM message;";
				stm = conn.prepareStatement(viewMessages);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("MessageID: " +rs.getInt(1)+ ", Date: "+rs.getTimestamp(2)+", Sender: '"+rs.getString(4)+"', Receiver: '"+rs.getString(3)+"', \n'"+ rs.getString(5)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which message would you like to delete? Type messageID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}else {
					System.out.println("There are no messages.");
				}
				if (found) {
					String deleteMessage = "DELETE FROM message WHERE messageID=" + typedID + ";";
					stm = conn.prepareStatement(deleteMessage);
					stm.executeUpdate();
					System.out.println("Message deleted successfully");
					do {
						System.out.println("Do you want to delete another message? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("Message not found.");
				}

			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void deleteUser() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String showAllUsers = "SELECT userID, username, (SELECT roleName FROM role WHERE user.roleID=role.roleID) FROM user;";
				stm = conn.prepareStatement(showAllUsers);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("userID: " +rs.getInt(1)+ ", Username: '"+rs.getString(2)+"', Role: '"+rs.getString(3)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which user would you like to delete? Type userID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}
				if (found) {
					String deleteUser = "DELETE FROM bmi WHERE bmiID=" + typedID + ";";
					stm = conn.prepareStatement(deleteUser);
					stm.executeUpdate();
					System.out.println("User deleted successfully");
					do {
						System.out.println("Do you want to delete another user? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("User not found.");
				}

			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void updateUsername() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String showAllUsers = "SELECT userID, username, password FROM user;";
				stm = conn.prepareStatement(showAllUsers);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("userID: " +rs.getInt(1)+ ", Username: '"+rs.getString(2)+"', Password: '"+rs.getString(3)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which username would you like to update? Type userID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}
				if (found) {
					if (typedID==1) {
						System.out.println("You cannot change admin username! It is impossible to update it.");
					}else {
						String username;
						do {
							do {
								System.out.println("Username can contain lowercase [a-z] or uppercase [A-Z] letters, digits [0-9], special characters [@!#$%^&+=], no spaces and it must be above 4 to 25 characters.");
								System.out.println("Username: ");
								username = MainApplication.input.nextLine();
							}while(!username.matches("^([0-9,a-z,A-Z,@!#$%^&+=])(?=\\S+$).{4,25}$"));
						}while (LoginScreen.findUsernameInList(username));
						String updateUser = "UPDATE user SET username = '"+username+"' WHERE userID=" + typedID + ";";
						stm = conn.prepareStatement(updateUser);
						stm.executeUpdate();
						System.out.println("Username updated successfully");
					}
					do {
						System.out.println("Do you want to update another user's username? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("User not found.");
				}

			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void updatePassword() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String showAllUsers = "SELECT userID, username, password FROM user;";
				stm = conn.prepareStatement(showAllUsers);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("userID: " +rs.getInt(1)+ ", Username: '"+rs.getString(2)+"', Password: '"+rs.getString(3)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which password would you like to update? Type userID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}
				if (found) {
					String password;
					do {
						do {
							System.out.println("Password can contain lowercase [a-z] or uppercase [A-Z] letters, digits [0-9], special characters [@!#$%^&+=], no spaces and it must be above 5 to 25 characters.");
							System.out.print("Password: ");
							password = MainApplication.input.nextLine();
						}while (!password.matches("^([0-9,a-z,A-Z,@!#$%^&+=])(?=\\S+$).{5,25}$"));
					}while (password.length()>25);
					if (typedID==1) {
						MainApplication.onlineUser.setPassword(password);
					}
					String updatePassword = "UPDATE user SET password = '"+password+"' WHERE userID=" + typedID + ";";
					stm = conn.prepareStatement(updatePassword);
					stm.executeUpdate();
					System.out.println("Password updated successfully");
					do {
						System.out.println("Do you want to update another user's password? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("User not found.");
				}

			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void assignReader() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String showAllUsers = "SELECT userID, username, (SELECT roleName FROM role WHERE user.roleID=role.roleID) FROM user;";
				stm = conn.prepareStatement(showAllUsers);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("userID: " +rs.getInt(1)+ ", Username: '"+rs.getString(2)+"', Role: '"+rs.getString(3)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which user would you like to assign as a Reader? Type userID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}
				if (found) {
					if (typedID==1) {
						System.out.println("You cannot change admin's role!");
					}else {
						String assignReader = "UPDATE user SET roleID = 2 WHERE userID=" + typedID + ";";
						stm = conn.prepareStatement(assignReader);
						stm.executeUpdate();
						System.out.println("Role assigned successfully");
					}
					do {
						System.out.println("Do you want to assign another user as a Reader? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("User not found.");
				}

			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void assignEditor() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String showAllUsers = "SELECT userID, username, (SELECT roleName FROM role WHERE user.roleID=role.roleID) FROM user;";
				stm = conn.prepareStatement(showAllUsers);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("userID: " +rs.getInt(1)+ ", Username: '"+rs.getString(2)+"', Role: '"+rs.getString(3)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which user would you like to assign as an Editor? Type userID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}
				if (found) {
					if (typedID==1) {
						System.out.println("You cannot change admin's role!");
					}else {
						String assignReader = "UPDATE user SET roleID = 3 WHERE userID=" + typedID + ";";
						stm = conn.prepareStatement(assignReader);
						stm.executeUpdate();
						System.out.println("Role assigned successfully");
					}
					do {
						System.out.println("Do you want to assign another user as an Editor? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("User not found.");
				}

			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void assignSubAdmin() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			String editing = "N";

			do {
				int typedID =0;
				String showAllUsers = "SELECT userID, username, (SELECT roleName FROM role WHERE user.roleID=role.roleID) FROM user;";
				stm = conn.prepareStatement(showAllUsers);
				ResultSet rs = stm.executeQuery();
				boolean found = false;
				if (rs.next()) {
					rs.beforeFirst();
					System.out.println("----------------------------------");
					while (rs.next()) {
						System.out.println("userID: " +rs.getInt(1)+ ", Username: '"+rs.getString(2)+"', Role: '"+rs.getString(3)+"';");
						System.out.println("----------------------------------");
					}
					do {
						try {
							System.out.println("Which user would you like to assign as a SubAdmin? Type userID: ");
							typedID = Integer.parseInt(MainApplication.input.nextLine());
							rs.beforeFirst();
							while (rs.next()) {
								int ID = rs.getInt(1);
								if (ID == typedID) {
									found = true;
									break;
								}
							}
							break;
						}catch (Exception e) {
							System.out.println("This is not a valid ID");
						}
					}while (true);
				}
				if (found) {
					if (typedID==1) {
						System.out.println("You cannot change admin's role!");
					}else {
						String assignReader = "UPDATE user SET roleID = 4 WHERE userID=" + typedID + ";";
						stm = conn.prepareStatement(assignReader);
						stm.executeUpdate();
						System.out.println("Role assigned successfully");
					}
					do {
						System.out.println("Do you want to assign another user as a SubAdmin? [Y/N]: ");
						editing = MainApplication.input.nextLine();
					} while (!editing.equals("Y") && !editing.equals("N"));
				}else {
					System.out.println("User not found.");
				}
			}while (editing.equals("Y"));

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void viewUsers() {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String viewUsers = "SELECT username FROM user;";
			stm = conn.prepareStatement(viewUsers);
			ResultSet rs = stm.executeQuery();
			System.out.println("---------------------------------- \nUsers on application:");
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}

		} catch (SQLException ex) {
			Logger.getLogger(DB_AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
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