package com.bgasparotto.archproject.persistence.exception;

/**
 * General persistence exception that can be throw by persistence operations.
 * 
 * @author Bruno Gasparotto
 *
 */
public class GeneralPersistenceException extends Exception {
	private static final long serialVersionUID = -6057737927996676949L;

	/**
	 * See {@link Exception#Exception() Exception()}
	 */
	public GeneralPersistenceException() {
	}

	/**
	 * See {@link Exception#Exception(String) Exception(String)}
	 */
	public GeneralPersistenceException(String message) {
		super(message);
	}

	/**
	 * See {@link Exception#Exception(String, Throwable) Exception(String,
	 * Throwable)}
	 */
	public GeneralPersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * See {@link Exception#Exception(Throwable) Exception(Throwable)}
	 */
	public GeneralPersistenceException(Throwable cause) {
		super(cause);
	}
}