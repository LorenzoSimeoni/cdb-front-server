package com.excilys.formation.exception;

public class NameCompanyException extends NotPermittedCompanyException{

	private static final long serialVersionUID = 1L;
	String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public NameCompanyException() {
		ErrorMsg="You gived a wrong Company name";
	}
}
