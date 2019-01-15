package com.excilys.formation.exception;

public class NotPermittedUserException extends Exception {
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public NotPermittedUserException() {
		ErrorMsg = "Wrong parameter for a User";
	}
}
