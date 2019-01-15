package com.excilys.formation.exception;

public class NotPermittedCompanyException extends Exception {
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public NotPermittedCompanyException() {
		ErrorMsg = "Wrong parameter for a company";
	}
}
