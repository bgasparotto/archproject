package com.bgasparotto.archproject.infrastructure.validator;

/**
 * E-mail address validator.
 * 
 * @author Bruno Gasparotto
 *
 */
@FunctionalInterface
public interface EmailValidator {

	/**
	 * Validate an email address.
	 * 
	 * @param email
	 *            The email to be validated
	 * @return {@code true} if the given email is valid, {@code false} otherwise
	 */
	boolean validate(String email);
}