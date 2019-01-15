package com.excilys.formation.exception;

import com.excilys.formation.controller.ViewName;

public class PageNumberException extends WebExceptions {
	private static final long serialVersionUID = 1L;
	
	private String View;
	
	public String getView() {
		return View;
	}
	
	public PageNumberException() {
		super("This page doesn't exist");
		View = ViewName.NOTFOUND.toString();
	}
}
