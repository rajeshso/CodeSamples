/**
 * The Exception to indicate if the Number is not supported by the library.
 */
package com.worldpay.numberutil;

/**
 * @author Rajesh
 *
 */
public class InvalidNumberException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidNumberException() {
		super();
	}

	/**
	 * @param message
	 */
	public InvalidNumberException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidNumberException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidNumberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
