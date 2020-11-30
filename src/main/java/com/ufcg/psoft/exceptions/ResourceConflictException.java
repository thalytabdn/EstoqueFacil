package com.ufcg.psoft.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {

	public ResourceConflictException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;
}
