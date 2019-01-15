package com.excilys.formation.cli;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.checker.Controller;
import com.excilys.formation.config.ConsoleConfig;
import com.excilys.formation.dto.ComputerDTO;

public class FeatureCLI {
	private static Scanner sc;
	static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConsoleConfig.class);
	static ClientRest clientRest = applicationContext.getBean("clientRest", ClientRest.class);
	
	public void features() {
		int key;
		boolean quitter = true;
		while(quitter) {
			displayMenu();
			sc = new Scanner(System.in);
			key = Controller.userGiveAnInt(sc);
			switch (key) {
			case 1:
				parseShow(sc);
				System.out.println("\n   \n ************************************************************************************************************** \n \n");
				break;
			case 2:
				parseCreate(sc);
				System.out.println("\n \n ************************************************************************************************************** \n \n");
				break;
			case 3:
				parseUpdate(sc);
				System.out.println("\n \n ************************************************************************************************************** \n \n");
				break;
			case 4:
				parseDelete(sc);
				System.out.println("\n \n ************************************************************************************************************** \n \n");
				break;
			case 5:
				quitter = false;
				break;
			default:
				break;
			}
		}
	}
	
	public void parseShow(Scanner sc) {
		int key = 0;
		displayShowMenu();
		key = Controller.userGiveAnInt(sc);
		switch (key) {
			case 1:
				System.out.println(clientRest.getAllEmployee());
				break;
			case 2:
				displayComputerDetailsID(sc);
				break;
			default:
				break;			
		}
	}
	
	public void displayComputerDetailsID(Scanner sc) {
		System.out.println("YOU CHOOSE TO PRINT COMPUTER DETAILS BY ID, GIVE AN ID PLEASE");
		long id = Controller.userGiveALong(sc);
		if(id != -1) {
			System.out.println(clientRest.getComputer(id));
		}
	}
	
	public void parseCreate(Scanner sc) {
		displayCreateMenu();
		System.out.println("Give a computer Name");
		sc.nextLine();
		String name = sc.nextLine();
		if(name.equals("null")|| name.equals("")) {
			name = null;
		}
		System.out.println("Give an introduced Date (if you don't want to fill a date do an enter)");
		String introduced = sc.nextLine();
		String discontinued = null;
		if(introduced.equals("null") || introduced.equals("")) {
			introduced = null;
		} else {
			System.out.println("Give a discontinued date (if you don't want to fill a date do an enter)");
			discontinued = sc.nextLine();
			if(discontinued.equals("null") || discontinued.equals("")) {
				discontinued = null;
			} 
		}
		System.out.println("Give a Company ID (if you don't want, write null or do an enter)");
		String companyId = sc.nextLine();
		if(companyId.equals("null") || companyId.equals("")) {
			companyId = null;
		}
		System.out.println(clientRest.createComputer(new ComputerDTO(name, introduced, discontinued, companyId)));
	}
	
	public void parseDelete(Scanner sc) {
		long id = 0;
		int key = 0;
		displayDeleteMenu();
		key = Controller.userGiveAnInt(sc);
		switch (key) {
			case 1:
				System.out.println("YOU CHOOSE TO DELETE A COMPUTER");
				System.out.println("GIVE AND ID");
				
				id = Controller.userGiveALong(sc);
				if(id != -1) {
					System.out.println(clientRest.deleteComputer(id));
				}
				break;
		}
	}
	
	public void parseUpdate(Scanner sc) {
		displayUpdateMenu();
		System.out.println("Give a computer ID");
		long id = Controller.userGiveALong(sc);
		if(id != -1) {
			clientRest.getComputer(id);
			System.out.println("Give a computer Name (if you don't want to change the name do an enter)");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.println("Give an introduced Date (if you don't want to fill a date do an enter)");
			String introduced = sc.nextLine();
			if(introduced.equals("null")) {
				introduced = null;
			}
			System.out.println("Give a discontinued date (if you don't want to fill a date do an enter)");
			String discontinued = sc.nextLine();
			if(discontinued.equals("null")) {
				discontinued = null;
			}
			System.out.println("Give a Company ID (if you don't want, write null or do an enter)");
			String companyId = sc.nextLine();
			if(companyId.equals("null")) {
				companyId = null;
			}
			System.out.println(clientRest.updateComputer(new ComputerDTO(id, name, introduced, discontinued, companyId)));
		}
	}
	
	public void displayMenu() {
		System.out.println("WELCOME IN THE COMPUTER DATABASE APPLICATION");
		System.out.println("MENU : ");
		System.out.println("          1. SHOW");
		System.out.println("          2. CREATE");
		System.out.println("          3. UPDATE");
		System.out.println("          4. DELETE");
		System.out.println("          5. EXIT");
	}
	
	public void displayShowMenu() {
		System.out.println("WHAT DO YOU WANT TO SHOW ?");
		System.out.println("CHOICES : ");
		System.out.println("          1. COMPUTERS");
		System.out.println("          2. COMPUTER DETAILS BY ID");
	}
	
	public void displayCreateMenu() {
		System.out.println("YOU CHOOSE TO CREATE A NEW COMPUTER");
		System.out.println("Give fields when asked, if you want to exit write 'exit'");
	}
	
	public void displayUpdateMenu() {
		System.out.println("YOU CHOOSE TO UPDATE A NEW COMPUTER");
		System.out.println("Give fields when asked, if you want to exit write 'exit'");
	}
	
	public void displayDeleteMenu() {
		System.out.println("WHAT DO YOU WANT TO DELETE ?");
		System.out.println("          1. COMPUTER");
	}
}
