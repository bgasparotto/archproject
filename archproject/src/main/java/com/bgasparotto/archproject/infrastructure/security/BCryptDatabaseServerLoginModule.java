package com.bgasparotto.archproject.infrastructure.security;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;
import org.mindrot.jbcrypt.BCrypt;

/**
 * An extension of {@link DatabaseServerLoginModule} that uses the
 * {@code BCrypt} algorithm to check if a given password matches the hashed one
 * previously stored on database.
 * 
 * @author Bruno Gasparotto
 *
 */
public class BCryptDatabaseServerLoginModule extends DatabaseServerLoginModule {

	@Override
	protected boolean validatePassword(String inputPassword,
			String expectedPassword) {

		if ((inputPassword == null) || ("".equals(inputPassword))) {
			return false;
		}

		boolean matches = BCrypt.checkpw(inputPassword, expectedPassword);
		return matches;
	}
}