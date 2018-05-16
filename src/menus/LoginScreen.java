package menus;

import db_Connection.DB_LoginScreen;
import loseWeight.MainApplication;
import objectItems.BMI;
import objectItems.Role;
import objectItems.User;

public class LoginScreen{

	public static void menu1() {
		System.out.println("1.Login \n2.Create account \n3.Quit");
	}

	public static int process() {
		menu1();
		int choice = chooseMainMenuOption_3();
		switch (choice) {
		case 1:
			MainApplication.onlineUser = checkAccess();

			while (MainApplication.onlineUser != null) {
				if (MainApplication.onlineUser.getRole() == Role.ADMIN) {
					AdminMenu.process(MainApplication.onlineUser);
				}else if (MainApplication.onlineUser.getRole() == Role.READER) {
					ReaderMenu.process(MainApplication.onlineUser);
				}else if (MainApplication.onlineUser.getRole() == Role.EDITOR) {
					EditorMenu.process(MainApplication.onlineUser);
				}else if (MainApplication.onlineUser.getRole() == Role.SUBADMIN) {
					SubadminMenu.process(MainApplication.onlineUser);
				}else {
					UserMenu.process(MainApplication.onlineUser);
				}
			}
			break;
		case 2:
			createAccProcess();
			break;
		case 3:
			System.out.println("Goodbye.");
			return 3;
		}

		return choice;

	}

	public static User checkAccess () {
		System.out.println("Username: ");
		String username = MainApplication.input.nextLine();
		DB_LoginScreen.findUsernameInBD(username);
		boolean accessibility = false;
		if (MainApplication.onlineUser!=null) {
			if (MainApplication.onlineUser.isLocked()==true) {
				System.out.println("Your account is blocked. Message sent to administrator so as to unlock it.Your new password will be 0000. Please check back in a day or two.");
			}else {
				if (MainApplication.onlineUser.getAttempts()!=0) {
					for (int i=MainApplication.onlineUser.getAttempts();i>=1;i--) {
						System.out.println("You have " + MainApplication.onlineUser.getAttempts() + " attempt(s) left.");
						System.out.println("Password: ");
						String password = MainApplication.input.nextLine();

						if (password.equals(MainApplication.onlineUser.getPassword())){
							MainApplication.onlineUser.setAttempts(3);
							accessibility = true;
							DB_LoginScreen.setUserDB(accessibility);
							return (MainApplication.onlineUser);
						}else {
							int att = MainApplication.onlineUser.getAttempts()-1;
							MainApplication.onlineUser.setAttempts(att); 
							DB_LoginScreen.setAttempts(att);
						}

					}
					MainApplication.onlineUser.setLocked(true);
					System.out.println("Your account was blocked. Message sent to administrator so as to unlock it. Your new password will be 0000. Please check back in a day or two.");
					DB_LoginScreen.setUserDB(accessibility);
					return null;
				}else {
					MainApplication.onlineUser.setLocked(true);
					System.out.println("Your account was blocked. Message sent to administrator so as to unlock it.Your new password will be 0000. Please check back in a day or two.");
					DB_LoginScreen.setUserDB(accessibility);
					return null;
				}
			}
		}else {
			System.out.println("Username not found.");
			return null;
		}
		return null;
	}

	public static void createAccProcess() {
		//Check username availability
		String username;
		do {
			do {
				System.out.println("Username can contain lowercase [a-z] or uppercase [A-Z] letters, digits [0-9], special characters [@!#$%^&+=], no spaces and it must be above 4 to 25 characters.");
				System.out.println("Username: ");
				username = MainApplication.input.nextLine();
			}while(!username.matches("^([0-9,a-z,A-Z,@!#$%^&+=])(?=\\S+$).{4,25}$"));
		}while (findUsernameInList(username));

		//Input password
		String password;
		do {
			System.out.println("Password can contain lowercase [a-z] or uppercase [A-Z] letters, digits [0-9], special characters [@!#$%^&+=], no spaces and it must be above 5 to 25 characters.");
			System.out.print("Password: ");
			password = MainApplication.input.nextLine();
		}while (!password.matches("^([0-9,a-z,A-Z,@!#$%^&+=])(?=\\S+$).{5,25}$"));

		//Input weight
		double weight= UserMenu.weight();

		//Input height
		double height= UserMenu.height();

		//Input age
		int age= UserMenu.age();

		//Input gender
		String gender= UserMenu.gender();
		BMI userbmi = new BMI (weight,height,age,gender);
		int userID = DB_LoginScreen.findID();
		DB_LoginScreen.addUserInDB(userID, username, password, userbmi);
		MainApplication.onlineUser = null;
	}

	public static boolean findUsernameInList(String username) {
		boolean foundDuplicate=DB_LoginScreen.findDuplicateUsername(username);
		if (foundDuplicate) {
			System.out.println("Username already exists.");
			return true;
		}
		return false;
	}

	public static int chooseMainMenuOption_3() {
		int choice;
		do {
			try {
				System.out.print("Choose: ");
				choice = Integer.parseInt(MainApplication.input.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid entry");
				choice = 0;
			}
		}while (choice<1 || choice>3);
		return choice;
	}

}