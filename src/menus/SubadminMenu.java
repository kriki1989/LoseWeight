package menus;

import db_Connection.DB_AdminMenu;
import loseWeight.Mailbox;
import loseWeight.MainApplication;
import objectItems.User;

public class SubadminMenu {

	public static void menu1() {
		System.out.println("\n1.Update info \n2.Messages \n3.Send progress to other users \n4.Show profile \n5.Log out \nWhat do you want to do?");
		System.out.println("\nSub-Admin User options: \n6.Read all messages \n7.Edit message \n8.Delete message");
	}

	public static User process(User myUser) {
		menu1();
		int userChoice = chooseMainMenuOption_8();
		switch (userChoice){
		case 1:
			boolean repeat = true;
			do {
				repeat = UserMenu.secondaryDisplayOptions_1(repeat);
			}while (repeat);
			break;
		case 2:
			boolean repeat1 = true;
			do {
				repeat1 = UserMenu.secondaryDisplayOptions_2(repeat1);
			}while (repeat1);
			break;
		case 3:
			Mailbox.sendProgressOthers();
			break;
		case 4:
			System.out.println(MainApplication.onlineUser.getMyBMI().toString());
			break;
		case 5:
			MainApplication.onlineUser = null;
		case 6:
			DB_AdminMenu.viewMessage();
			break;
		case 7:
			DB_AdminMenu.editMessage();
			break;
		case 8:
			DB_AdminMenu.deleteMessage();
			break;
		}
		return null;
	}

	public static int chooseMainMenuOption_8() {
		int choice;
		do {
			try {
				System.out.print("Choose: ");
				choice = Integer.parseInt(MainApplication.input.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid entry");
				choice = 0;
			}
		}while (choice<1 || choice>8);
		return choice;
	}

}
