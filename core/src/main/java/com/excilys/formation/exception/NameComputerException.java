package com.excilys.formation.exception;

public class NameComputerException extends NotPermittedComputerException {
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public NameComputerException() {
		ErrorMsg = "You gived a computer with no name, can't achieve the creation or update";
	}
}