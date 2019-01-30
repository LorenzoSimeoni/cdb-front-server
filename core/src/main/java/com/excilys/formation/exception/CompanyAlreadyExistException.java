package com.excilys.formation.exception;

public class CompanyAlreadyExistException extends NotPermittedCompanyException{

	private static final long serialVersionUID = 1L;
	String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public CompanyAlreadyExistException() {
		ErrorMsg="You gave a Company name which already exist";
	}
}
