package com.bgasparotto.archproject.service.exception;

/**
 * Exception to be thrown when an operation is attempt to be performed on an
 * nonexistent user.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserDoesNotExistException extends ServiceException {
	private static final long serialVersionUID = -4904724189326117911L;

	/**
	 * See {@link ServiceException#ServiceException() ServiceException()}
	 */
	public UserDoesNotExistException() {
	}

	/**
	 * See {@link ServiceException#ServiceException(String)
	 * ServiceException(String)}
	 */
	public UserDoesNotExistException(String message) {
		super(message);
	}

	/**
	 * See {@link ServiceException#ServiceException(String, Throwable)
	 * ServiceException(String, Throwable)}
	 */
	public UserDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * See {@link ServiceException#ServiceException(Throwable)
	 * ServiceException(Throwable)}
	 */
	public UserDoesNotExistException(Throwable cause) {
		super(cause);
	}
}