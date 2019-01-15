package com.excilys.formation.exception;

public class RoleIdException  extends NotPermittedUserException{

	private static final long serialVersionUID = 1L;
	String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public RoleIdException() {
		ErrorMsg="You gived a wrong Role ID, can't achieve the creation of user";
	}
}
