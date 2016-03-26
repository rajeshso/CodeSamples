/**
 * This source code is the property of Lloyds Banking Group PLC.
 * 
 * All Rights Reserved.
 */
package com.lloydsbanking.interview;

/**
 * @author Rajesh
 *
 */
public class WidgetMachineException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public WidgetMachineException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public WidgetMachineException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WidgetMachineException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public WidgetMachineException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
