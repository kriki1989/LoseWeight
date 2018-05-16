package objectItems;

public class User {
	private int userID;
	private String username;
	private String password;
	private boolean isLocked;
	private int attempts;
	private Role role;
	private BMI myBMI;

	//CONSTRUCTORS
	//DB constructor
	public User(int userID, String usernameFound, String password, boolean isLocked, int attempts, String rolename, BMI mybmi) {
		this.userID = userID;
		this.username = usernameFound;
		this.password = password;
		this.myBMI = mybmi;
		if (rolename.equals("ADMIN")) {
			this.role = Role.ADMIN;
		}else if (rolename.equals("EDITOR")) {
			this.role = Role.EDITOR;
		}else if (rolename.equals("READER")) {
			this.role = Role.READER;
		}else if (rolename.equals("SUBADMIN")) {
			this.role = Role.SUBADMIN;
		}else{
			this.role = Role.STANDARD_USER;
		}
		this.isLocked = isLocked;
		this.attempts = attempts;
	}

	//Getters
	public boolean isLocked() {
		return isLocked;
	}
	public int getAttempts() {
		return attempts;
	}
	public String getPassword() {
		return password;
	}
	public BMI getMyBMI() {
		return myBMI;
	}
	public int getUserID() {
		return userID;
	}
	public String getUsername() {
		return username;
	}
	public Role getRole() {
		return role;
	}

	//Setters
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}
