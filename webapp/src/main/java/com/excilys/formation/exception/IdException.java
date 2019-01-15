package com.excilys.formation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.excilys.formation.controller.ViewName;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class IdException extends WebExceptions {
	private static final long serialVersionUID = 1L;
	
	private String View;
	
	public String getView() {
		return View;
	}
	
	public IdException() {
		super("The ID doesn't correspond to an existing computer");
		View = ViewName.NOTFOUND.toString();
	}
}
