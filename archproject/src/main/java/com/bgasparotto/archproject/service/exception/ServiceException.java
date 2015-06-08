package com.bgasparotto.archproject.service.exception;

/**
 * General service exception that can be throw by service operations (from the
 * service layer).
 * 
 * @author Bruno Gasparotto
 *
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = 6432456909790403203L;

	/**
	 * See {@link Exception#Exception() Exception()}
	 */
	public ServiceException() {
	}

	/**
	 * See {@link Exception#Exception(String) Exception(String)}
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * See {@link Exception#Exception(String, Throwable) Exception(String,
	 * Throwable)}
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * See {@link Exception#Exception(Throwable) Exception(Throwable)}
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
}