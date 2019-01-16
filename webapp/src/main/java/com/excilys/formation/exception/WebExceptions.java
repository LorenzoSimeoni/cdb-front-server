package com.excilys.formation.exception;

public class WebExceptions extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WebExceptions() {
		super("InternalError");
	}
	
	public WebExceptions(String str) {
		super(str);
	}
}
