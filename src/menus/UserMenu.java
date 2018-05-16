package menus;

import db_Connection.DB_UserMenu;
import loseWeight.Mailbox;
import loseWeight.MainApplication;
import objectItems.User;

public class UserMenu {

	//MENOY 1
	public static void menu1() {
		System.out.println("\n1.Update info \n2.Messages \n3.Send progress to other users \n4.Show profile \n5.Log out \nWhat do you want to do?");
	}

	public static User process(User myUser) {
		menu1();
		int userChoice = chooseMainMenuOption_5();
		switch (userChoice){
		case 1:
			boolean repeat = true;
			do {
				repeat = secondaryDisplayOptions_1(repeat);
			}while (repeat);
			break;
		case 2:
			boolean repeat1 = true;
			do {
				repeat1 = secondaryDisplayOptions_2(repeat1);
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
		}
		return null;
	}

	//MENOY 1.1
	public static void menu1_1() {
		System.out.println("\n1.Password \n2.Weight \n3.Height \n4.Age \n5.Gender \n6.Go back \nWhat do you want to update?");
	}

	public static boolean secondaryDisplayOptions_1(boolean repeat) {
		menu1_1();
		int choice = chooseMainMenuOption_6();
		switch (choice) {
		case 1:
			String password=password();
			MainApplication.onlineUser.setPassword(password);
			DB_UserMenu.updatePass(password);
			break;
		case 2:
			double weight=weight();
			MainApplication.onlineUser.getMyBMI().setWeight(weight);
			DB_UserMenu.updateW_H(weight, choice);
			break;
		case 3:
			double height=height();
			MainApplication.onlineUser.getMyBMI().setHeight(height);
			DB_UserMenu.updateW_H(height, choice);
			break;
		case 4:
			int age=age();
			MainApplication.onlineUser.getMyBMI().setAge(age);
			DB_UserMenu.updateAge(age);
			break;
		case 5:
			String gender=gender();
			MainApplication.onlineUser.getMyBMI().setGender(gender);
			DB_UserMenu.updateGender(gender);
			break;
		case 6:
			repeat = false;
			return repeat;
		}
		repeat = true;
		return repeat;
	}

	//MENOY 1.2
	public static void menu1_2() {
		System.out.println("\n1.Send message \n2.Show received messages \n3.Show sent messages \n4.Go back \nWhat do you want to do?");
	}

	public static boolean secondaryDisplayOptions_2(boolean repeat) {
		menu1_2();
		int choice = chooseMainMenuOption_4();
		switch (choice) {
		case 1:
			Mailbox.sendMessage();
			break;
		case 2:
			Mailbox.receivedMessages();
			break;
		case 3:
			Mailbox.showSentMessages();
			break;
		case 4:
			repeat = false;
			return repeat;
		}
		repeat = true;
		return repeat;
	}

	//Entry for weight, height, age and gender
	public static String password (){
		String password;
		do {
			System.out.println("Password can contain lowercase [a-z] or uppercase [A-Z] letters, digits [0-9], special characters [@!#$%^&+=], no spaces and it must be above 5 to 25 characters.");
			System.out.print("Type your new password: ");
			password = MainApplication.input.nextLine();
		}while (!password.matches("^([0-9,a-z,A-Z,@!#$%^&+=])(?=\\S+$).{5,25}$"));
		return password;
	}

	public static double weight (){
		String weight1;
		double weight;
		do {
			try {
				System.out.println("Weight in kg: ");
				weight1 = MainApplication.input.nextLine();
				weight1 = weight1.replace(",",".");
				weight = Double.parseDouble(weight1);
			} catch (Exception e) {
				System.out.println("Weight not accepted e.g. 64.2");
				weight = 0;
			}
		} while (weight>600 || weight<10);
		return weight;
	}

	public static double height () {
		String height1;
		double height;
		do {
			try {
				System.out.println("Height in m: ");
				height1 = MainApplication.input.nextLine();
				height1 = height1.replace(",",".");
				height = Double.parseDouble(height1);
			} catch (Exception e) {
				System.out.println("Height not accepted e.g. 1.64");
				height = 0;
			}
		} while (height<0.50 || height>2.5);
		return height;
	}

	public static int age() {
		int age;
		do {
			try {
				System.out.println("Age: ");
				age = Integer.parseInt(MainApplication.input.nextLine());
			}catch (Exception e) {
				System.out.println("Invalid age");
				age = 9;
			}
		} while (age<10 || age>80);
		return age;
	}

	public static String gender() {
		String gender;
		do {
			System.out.println("Gender [M/F]: ");
			gender = MainApplication.input.nextLine();
		} while (!gender.equals("M") && !gender.equals("F"));
		return gender;
	}

	//Error catchers
	public static int chooseMainMenuOption_4() {
		int choice;
		do {
			try {
				System.out.print("Choose: ");
				choice = Integer.parseInt(MainApplication.input.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid entry");
				choice = 0;
			}
		}while (choice<1 || choice>4);
		return choice;
	}

	public static int chooseMainMenuOption_5() {
		int choice;
		do {
			try {
				System.out.print("Choose: ");
				choice = Integer.parseInt(MainApplication.input.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid entry");
				choice = 0;
			}
		}while (choice<1 || choice>5);
		return choice;
	}

	public static int chooseMainMenuOption_6() {
		int choice;
		do {
			try {
				System.out.print("Choose: ");
				choice = Integer.parseInt(MainApplication.input.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid entry");
				choice = 0;
			}
		}while (choice<1 || choice>6);
		return choice;
	}

}
