package com.bgasparotto.archproject.service.exception;

/**
 * Exception to be thrown when an invalid verification code is provided in cases
 * it is needed to perform user operations.
 * 
 * @author Bruno Gasparotto
 *
 */
public class InvalidVerificationCodeException extends ServiceException {
	private static final long serialVersionUID = 8701263917590815458L;

	/**
	 * See {@link ServiceException#ServiceException() ServiceException()}
	 */
	public InvalidVerificationCodeException() {
	}

	/**
	 * See {@link ServiceException#ServiceException(String)
	 * ServiceException(String)}
	 */
	public InvalidVerificationCodeException(String message) {
		super(message);
	}

	/**
	 * See {@link ServiceException#ServiceException(String, Throwable)
	 * ServiceException(String, Throwable)}
	 */
	public InvalidVerificationCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * See {@link ServiceException#ServiceException(Throwable)
	 * ServiceException(Throwable)}
	 */
	public InvalidVerificationCodeException(Throwable cause) {
		super(cause);
	}
}