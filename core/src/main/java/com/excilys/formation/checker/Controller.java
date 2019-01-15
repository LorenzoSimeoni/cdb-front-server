package com.excilys.formation.checker;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller {
	private final static Logger LOGGER = LogManager.getLogger(Controller.class.getName());

	public static int userGiveAnInt(Scanner sc) {
		int userInt = -1;
		try {
			userInt = sc.nextInt();
		} catch (InputMismatchException e) {
			LOGGER.info("You have to give an Integer but you put something else", e);
		}			
		return userInt;
	}
	
	public static long userGiveALong(Scanner sc) {
		long userLong = -1;
		try {
			userLong = sc.nextInt();
		} catch (InputMismatchException e) {
			LOGGER.info("You have to give an Long but you put something else", e);
		}			
		return userLong;
	}
	
	public static boolean testStringIsAInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			LOGGER.info("You have to give an Number but you put something else", e);
			return false;
		}
		return true;
	}
	
	public static boolean testStringIsALong(String str) {
		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			LOGGER.info("You have to give an Number but you put something else", e);
			return false;
		}
		return true;
	}

	public static boolean testStringIsADate(String str) {
		try {
			LocalDateTime.parse(str);
		} catch (DateTimeParseException e) {
			LOGGER.info("You have to give a Date but you put something else", e);
			return false;
		}
		return true;
	}
}
