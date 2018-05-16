package db_Connection;

import java.sql.Connection; //must
import java.sql.DriverManager; //must
import java.sql.PreparedStatement; //maybe
import java.sql.ResultSet; //must
import java.sql.SQLException; //must
import java.sql.Statement; //must
import java.util.logging.Level;
import java.util.logging.Logger;

import loseWeight.MainApplication;
import objectItems.BMI;
import objectItems.User;


public class DB_LoginScreen {
	private static final String USERNAME = "kriki1989";
	private static final String PASS = "1234";
	private static final String MYSQLURL1 = "jdbc:mysql://localhost?useSSL=false";
	private static final String MYSQLURL = "jdbc:mysql://localhost/loseweightdb?allowMultiQueries=true";

	public void readDB() {
		Connection con = null;
		Connection conn = null;
		PreparedStatement stm = null;
		try { //1

			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2
			con = DriverManager.getConnection(MYSQLURL1, USERNAME, PASS);
			Statement statement = con.createStatement();

			createDB(con,statement); //1
			con.close();
			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			Statement stat = conn.createStatement();
			createTables(conn,stat,stm); //2
		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}// end method


	public static void createDB (Connection con, Statement statement) {
		PreparedStatement stm;
		try {
			String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS LoseWeightDB;";
			stm = con.prepareStatement(sqlCreateDB);
			stm.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void createTables (Connection conn, Statement stat, PreparedStatement stm) {
		try {
			String sqlCreateFieldTable  = "CREATE TABLE IF NOT EXISTS bmi " +
					"(bmiID INT NOT NULL AUTO_INCREMENT, " +
					" weight DOUBLE NOT NULL, " +
					" height DOUBLE NOT NULL, " +
					" age TINYINT(2) NOT NULL, " +
					" gender VARCHAR(1) NOT NULL, " +
					" bmi VARCHAR(10) NULL, " +
					" classification VARCHAR(25) NULL, " +
					" bmr DOUBLE NULL, " +
					" PRIMARY KEY (bmiID));";
			stat.executeUpdate(sqlCreateFieldTable);
			sqlCreateFieldTable  = "CREATE TABLE IF NOT EXISTS role " +
					"(roleID INT NOT NULL AUTO_INCREMENT, " +
					" roleName VARCHAR(15) NOT NULL, " +
					" PRIMARY KEY (roleID));";
			stat.executeUpdate(sqlCreateFieldTable);
			sqlCreateFieldTable  = "CREATE TABLE IF NOT EXISTS user " +
					" (userID INT NOT NULL AUTO_INCREMENT, " +
					" username VARCHAR(25) NOT NULL, " +
					" password VARCHAR(25) NOT NULL, " +
					" isLocked TINYINT(1) NOT NULL DEFAULT 0, " +
					" attempts TINYINT(1) NOT NULL DEFAULT 3, " +
					" roleID INT NOT NULL, " +
					" bmiID INT NOT NULL, " +
					" PRIMARY KEY (userID), " +
					" FOREIGN KEY (bmiID) REFERENCES bmi(bmiID) ON DELETE CASCADE ON UPDATE NO ACTION, " +
					" FOREIGN KEY (roleID) REFERENCES role(roleID) ON UPDATE NO ACTION);";
			stat.executeUpdate(sqlCreateFieldTable);
			sqlCreateFieldTable  = "CREATE TABLE IF NOT EXISTS message " +
					"(messageID INT NOT NULL AUTO_INCREMENT, " +
					" dateSubmission TIMESTAMP NOT NULL, " +
					" senderID INT NOT NULL, " +
					" receiverID INT NOT NULL, " +
					" messageData VARCHAR(250) NULL, " +
					" PRIMARY KEY ( messageID ), " + 
					" FOREIGN KEY (senderID) REFERENCES user(userID) ON DELETE CASCADE ON UPDATE NO ACTION, " +
					" FOREIGN KEY (receiverID) REFERENCES user(userID) ON DELETE CASCADE ON UPDATE NO ACTION); ";
			stat.executeUpdate(sqlCreateFieldTable);

			//INSERT DATA
			ResultSet rs = stat.executeQuery("SELECT * FROM user");
			if (!rs.isBeforeFirst()) {
				String sqlInsertionData = "INSERT INTO role (roleName) VALUES ('ADMIN');" +
						"INSERT INTO role (roleName) VALUES ('READER');" +
						"INSERT INTO role (roleName) VALUES ('EDITOR');" +
						"INSERT INTO role (roleName) VALUES ('SUBADMIN');" +
						"INSERT INTO role (roleName) VALUES ('STANDARD_USER');" +
						"INSERT INTO bmi (bmiID, weight, height, age, gender, bmi, classification, bmr) VALUES (1,75,1.75,33,'M', 24.49, 'Optimal', 1744.10);" +
						"INSERT INTO user (username, password, isLocked, attempts, roleID, bmiID) VALUES ('admin', 'aDmI3$', 0, 3, 1, 1);" +
						"INSERT INTO bmi (bmiID, weight, height, age, gender, bmi, classification, bmr) VALUES (2,77,1.64,29,'F', 28.63, 'Overweight', 1553.10);" +
						"INSERT INTO user (username, password, isLocked, attempts, roleID, bmiID) VALUES ('user1', '123456', 0, 3, 5, 2);" +
						"INSERT INTO bmi (bmiID, weight, height, age, gender, bmi, classification, bmr) VALUES (3,85,1.75,30,'F', 27.76, 'Overweight', 1645.00);" +
						"INSERT INTO user (username, password, isLocked, attempts, roleID, bmiID) VALUES ('user2', '123456', 0, 3, 5, 3);" +
						"INSERT INTO bmi (bmiID, weight, height, age, gender, bmi, classification, bmr) VALUES (4,75,1.75,33,'M', 24.49,'Optimal', 1744.10);" +
						"INSERT INTO user (username, password, isLocked, attempts, roleID, bmiID) VALUES ('user3', '123456', 0, 3, 5, 4);";
				stm = conn.prepareStatement(sqlInsertionData);
				stm.executeUpdate();
			}
		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void findUsernameInBD(String username) {
		Connection conn = null;
		PreparedStatement stm = null;
		try { //1

			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			MainApplication.onlineUser = UserFound(username, conn, stm);
		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}// end method

	public static User UserFound (String username, Connection conn, PreparedStatement stm) {
		try {
			String searchUsername = "SELECT user.userID, user.username, user.password, user.isLocked, user.attempts, user.roleID, user.bmiID, role.roleName, bmi.weight, bmi.height, bmi.age, bmi.gender, bmi.bmi, bmi.classification, bmi.bmr FROM User \r\n" + 
					"INNER JOIN role ON user.roleID = role.roleID\r\n" + 
					"INNER JOIN bmi ON user.bmiID = bmi.bmiID WHERE user.username = '" + username + "';";
			stm = conn.prepareStatement(searchUsername);
			ResultSet rs = stm.executeQuery();
			boolean isLocked;
			while (rs.next()) {
				int userID = rs.getInt(1);
				String usernameFound = rs.getString(2);
				String password = rs.getString(3);
				if (rs.getInt(4) == 0) {
					isLocked = false;
				}else {
					isLocked = true;
				}
				int attempts = rs.getInt(5);
				String rolename = rs.getString(8);
				double weight = rs.getDouble(9);
				double height = rs.getDouble(10);
				int age = rs.getInt(11);
				String gender = rs.getString(12);
				double bmi = rs.getDouble(13);
				String classification = rs.getString(14);
				double bmr = rs.getDouble(15);	
				User foundUser = new User (userID, usernameFound, password, isLocked, attempts, rolename, new BMI (weight, height, age, gender, bmi, classification, bmr));
				return foundUser;
			}
			return null;
		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static void setUserDB(boolean accessibility) {
		Connection conn = null;
		PreparedStatement stm = null;
		try { //1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			if(accessibility == true) {
				String updateAttempts = "UPDATE user SET attempts = 3 WHERE user.username ='" + MainApplication.onlineUser.getUsername() + "';";
				stm = conn.prepareStatement(updateAttempts);
				stm.executeUpdate();
			}else {
				String updateAttempts = "UPDATE user SET attempts = 0 WHERE user.username ='" + MainApplication.onlineUser.getUsername() + "';" +
						"UPDATE user SET isLocked = 1 WHERE user.username ='" + MainApplication.onlineUser.getUsername() + "';";
				stm = conn.prepareStatement(updateAttempts);
				stm.executeUpdate();
			}

		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}// end method

	public static void setAttempts(int att) {
		Connection conn = null;
		PreparedStatement stm = null;
		try { //1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String updateAttempts = "UPDATE user SET attempts =" + att + " WHERE user.username ='" + MainApplication.onlineUser.getUsername() + "';";
			stm = conn.prepareStatement(updateAttempts);
			stm.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
	}// end method

	public static boolean findDuplicateUsername(String username) {
		Connection conn = null;
		PreparedStatement stm = null;
		try { //1

			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String searchUsername = "SELECT user.username FROM user WHERE user.username = '" + username + "';";
			stm = conn.prepareStatement(searchUsername);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
		return false;
	}// end method

	public static int findID () {
		Connection conn = null;
		PreparedStatement stm = null;
		try { //1

			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String userID = "SELECT MAX(userID) FROM user;";
			stm = conn.prepareStatement(userID);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				int ID = rs.getInt(1)+1;
				return ID;
			}
			return 0;			
		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try { //3
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}//end try 3
			} //end if
		} //end try 1
		return 0;
	}// end method

	public static void addUserInDB(int userID, String username, String password, BMI userbmi) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {//1
			try { //2
				Class.forName("com.mysql.jdbc.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} //end try 2

			conn = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

			String sqlInsertionData = "INSERT INTO bmi (bmiID, weight, height, age, gender, bmi, classification, bmr) VALUES (" +userID+ "," +userbmi.getWeight()+"," +userbmi.getHeight()+","+userbmi.getAge()+", '"+userbmi.getGender()+"',"+userbmi.getBmi()+", '"+userbmi.getClassification()+"', " +userbmi.getBmr()+ ");" +
					"INSERT INTO user (username, password, isLocked, attempts, roleID, bmiID) VALUES ('"+username+"', '"+password+"', 0, 3, 5, "+userID+");";
			stm = conn.prepareStatement(sqlInsertionData);
			stm.executeUpdate();			

		} catch (SQLException ex) {
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(DB_LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
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