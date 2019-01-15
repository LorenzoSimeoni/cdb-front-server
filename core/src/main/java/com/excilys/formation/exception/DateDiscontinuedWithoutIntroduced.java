package com.excilys.formation.exception;

public class DateDiscontinuedWithoutIntroduced extends NotPermittedComputerException{
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public DateDiscontinuedWithoutIntroduced() {
		ErrorMsg = "You can't have Discontinued date without introduced date, can't achieve the creation or update of this computer";
	}
}
