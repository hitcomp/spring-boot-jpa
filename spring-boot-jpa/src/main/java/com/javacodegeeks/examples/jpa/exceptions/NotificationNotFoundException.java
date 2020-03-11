package com.javacodegeeks.examples.jpa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotificationNotFoundException() {
		super("Notice does not exist");
	}
}
