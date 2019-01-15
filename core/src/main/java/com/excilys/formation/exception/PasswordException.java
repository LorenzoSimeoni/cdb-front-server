package com.excilys.formation.exception;

public class PasswordException extends NotPermittedUserException {
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public PasswordException() {
		ErrorMsg = "You gived a user with no password, can't achieve the creation";
	}
}
