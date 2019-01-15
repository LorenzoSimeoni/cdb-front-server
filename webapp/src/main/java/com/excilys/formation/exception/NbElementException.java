package com.excilys.formation.exception;

import com.excilys.formation.controller.ViewName;

public class NbElementException extends WebExceptions {

	private static final long serialVersionUID = 1L;
	
	private String View;
	
	public String getView() {
		return View;
	}

	public NbElementException() {
		super("Can't have negative or null nbElement for our pages");
		View = ViewName.NOTFOUND.toString();
	}
}
