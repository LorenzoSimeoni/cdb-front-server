package com.excilys.formation.exception;

public class NotPermittedComputerException extends Exception {
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public NotPermittedComputerException() {
		ErrorMsg = "Wrong parameter for a computer";
	}
}
