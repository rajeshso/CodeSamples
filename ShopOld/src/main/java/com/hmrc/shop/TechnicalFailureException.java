/**
 * 
 */
package com.hmrc.shop;

/**
 * This Exception is thrown if there are any exception that the user would not be able to handle.
 * The exception indicates that the set up of the system is incorrect.
 * The purpose of the exception is the graceful shutdown.
 * @author Rajesh
 *
 */
public class TechnicalFailureException extends Exception {
	private static final long serialVersionUID = 1L;

	public TechnicalFailureException(String message) {
		super(message);
	}
}