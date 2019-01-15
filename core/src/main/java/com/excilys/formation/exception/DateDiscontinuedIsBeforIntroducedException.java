package com.excilys.formation.exception;


public class DateDiscontinuedIsBeforIntroducedException extends NotPermittedComputerException {
	private static final long serialVersionUID = 1L;
	String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public DateDiscontinuedIsBeforIntroducedException() {
		ErrorMsg = "A discontinued date can't be before an Introduced date, can't achieve the creation or update of this computer";
	}
}
