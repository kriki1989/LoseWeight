package menus;

import db_Connection.DB_AdminMenu;
import loseWeight.Mailbox;
import loseWeight.MainApplication;
import objectItems.User;

public class AdminMenu {

//MENOY 1
	public static void menu1() {
		System.out.println("1.Unlock passwords \n2.Modify messages \n3.Modify users \n4.Log out");
	}

	public static User process(User myUser) {
		menu1();
		int choice = UserMenu.chooseMainMenuOption_4();
		switch (choice) {
		case 1:
			DB_AdminMenu.unlockPasswords();
			break;
		case 2:
			boolean repeat = true;
			do{
				repeat = modifyMessages(repeat);
			}while (repeat);
			break;
		case 3:
			boolean repeat1 = true;
			do{
				repeat1 = modifyUsers(repeat1);
			}while (repeat1);
			break;
		case 4:
			MainApplication.onlineUser = null;
			break;
		}
		return null;
	}
		
//MENOY 1.2
	public static void menu1_2() {
		System.out.println("\n1.View messages \n2.Edit messages \n3.Delete messages \n4.Send message \n5.Go back");
	}

	public static boolean modifyMessages(boolean repeat) {
		menu1_2();
		int choice = UserMenu.chooseMainMenuOption_5();
		switch (choice) {
		case 1:
			DB_AdminMenu.viewMessage();
			break;
		case 2:
			DB_AdminMenu.editMessage();
			break;
		case 3:
			DB_AdminMenu.deleteMessage();
			break;
		case 4:
			Mailbox.sendMessage();
			break;
		case 5:
			repeat = false;
			return repeat;
		}
		repeat = true;
		return repeat;
	}

//MENOY 1.3 
	public static void menu1_3() {
		System.out.println("\n1.Create user \n2.Delete user \n3.Update user \n4.Assign role \n5.Go back");
	}

	public static boolean modifyUsers(boolean repeat) {
		menu1_3();
		int choice = UserMenu.chooseMainMenuOption_5();
		switch (choice) {
		case 1:
			LoginScreen.createAccProcess();
			break;
		case 2:
			DB_AdminMenu.deleteUser();
			break;
		case 3:
			boolean repeat1 = true;
			do {
				repeat1 = updateUser(repeat1);
			}while (repeat1);
			break;
		case 4:
			boolean repeat2 = true;
			do {
				repeat2 = assignRoles(repeat2);
			}while (repeat2);
			break;
		case 5:
			repeat = false;
			return repeat;
		}
		repeat = true;
		return repeat;
	}

//MENOY 1.3.3
	public static void menu1_3_3() {
		System.out.println("What do you want to update? \n1.Username \n2.Password \n3.Go back");
	}

	public static boolean updateUser(boolean repeat2) {
		menu1_3_3();
		int choice = LoginScreen.chooseMainMenuOption_3();
		switch (choice) {
		case 1:
			DB_AdminMenu.updateUsername();
			break;
		case 2:
			DB_AdminMenu.updatePassword();
			break;
		case 3:
			repeat2 = false;
			return repeat2;
		}
		repeat2 = true;
		return repeat2;		
	}

//MENOY 1.3.4
	public static void menu1_3_4() {
		System.out.println("Your roles are: \n1.Reader \n2.Editor \n3.Sub-Admin \n4.Go back");
	}

	public static boolean assignRoles(boolean repeat) {
		menu1_3_4();
		int choice = UserMenu.chooseMainMenuOption_4();
		switch (choice) {
		case 1:
			DB_AdminMenu.assignReader();
			break;
		case 2:
			DB_AdminMenu.assignEditor();
			break;
		case 3:
			DB_AdminMenu.assignSubAdmin();
			break;
		case 4:
			repeat = false;
			return repeat;
		}
		repeat = true;
		return repeat;	
	}

}
