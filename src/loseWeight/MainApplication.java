package loseWeight;

import java.util.Scanner;

import db_Connection.DB_LoginScreen;
import menus.LoginScreen;
import objectItems.User;

public class MainApplication {

	public static Scanner input = new Scanner(System.in);
	public static User onlineUser;

	public static void main(String[] args) {
		DB_LoginScreen dao = new DB_LoginScreen();
		dao.readDB();

		int mainMenuChoice = 0;
		do {
			mainMenuChoice = LoginScreen.process();
		}while(mainMenuChoice!=3);

	}
}

