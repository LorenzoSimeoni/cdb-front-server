package com.excilys.formation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class IdComputerException extends WebExceptions {
	private static final long serialVersionUID = 1L;
	
	public IdComputerException() {
		super("The ID doesn't correspond to an existing computer");
	}
}
