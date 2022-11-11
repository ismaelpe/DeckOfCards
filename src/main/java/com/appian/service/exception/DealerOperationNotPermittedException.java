package com.appian.service.exception;

/**
 * The Class DealerOperationNotPermittedException (unchecked exception)
 * 
 * Represents a runtime error when the state transition is not allowed
 * 
 */
public class DealerOperationNotPermittedException extends RuntimeException {	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1679355280668418358L;

	/**
	 * Instantiates a new dealer operation not permitted exception.
	 *
	 * @param message the message
	 */
	public DealerOperationNotPermittedException(String message) {
		super(message);
	}
}