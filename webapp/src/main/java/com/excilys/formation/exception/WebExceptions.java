package com.excilys.formation.exception;

import com.excilys.formation.controller.ViewName;

public class WebExceptions extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String View;
	
	public String getView() {
		return View;
	}
	
	public WebExceptions() {
		super("InternalError");
		View = ViewName.INTERNALSERVERERROR.toString();
	}
	
	public WebExceptions(String str) {
		super(str);
		View = ViewName.INTERNALSERVERERROR.toString();
	}
}
