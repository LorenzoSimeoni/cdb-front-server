package com.excilys.formation.exception;

public class NameUserException extends NotPermittedUserException{
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public NameUserException() {
		ErrorMsg = "You gave a user with no name, can't achieve the creation";
	}
}
