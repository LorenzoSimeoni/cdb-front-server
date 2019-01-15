package com.excilys.formation.exception;

public class UserAlreayExist extends NotPermittedUserException{

	private static final long serialVersionUID = 1L;
	String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public UserAlreayExist() {
		ErrorMsg="You gived an user name which already exist";
	}
}
