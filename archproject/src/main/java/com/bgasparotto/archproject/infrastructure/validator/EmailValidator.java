package com.bgasparotto.archproject.infrastructure.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * E-mail validator using {@code regex} according to <a
 * href="https://www.ietf.org/rfc/rfc2822.txt">RFC 2822</a> specification and
 * no external libraries.
 * 
 * @author Bruno Gasparotto
 *
 */
public class EmailValidator {

	/**
	 * The {@code RFC_2822} regular expression pattern.
	 */
	public static final String RFC_2822_REGEXP = "^"
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:"
			+ "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
			+ "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";

	private static final Pattern RFC_2822;

	static {
		RFC_2822 = Pattern.compile(RFC_2822_REGEXP);
	}

	/**
	 * Validate an email address.
	 * 
	 * @param email
	 *            The email to be validated
	 * @return {@code true} if the given email is valid, {@code false} otherwise
	 */
	public boolean validate(String email) {
		if ((email == null) || (email.isEmpty())) {
			return false;
		}

		Matcher matcher = RFC_2822.matcher(email);
		return matcher.matches();
	}
}