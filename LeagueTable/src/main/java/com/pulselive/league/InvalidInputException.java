package com.pulselive.league;

public class InvalidInputException extends RuntimeException {
	private static final long serialVersionUID = 6614735319435141152L;

	public InvalidInputException(String message) {
		super(message);
	}
}
