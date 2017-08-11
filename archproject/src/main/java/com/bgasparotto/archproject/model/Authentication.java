package com.bgasparotto.archproject.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Represents an authentication.
 * 
 * @author Bruno Gasparotto
 *
 */
@Embeddable
public class Authentication {

	@Embedded
	private Login login;

	@Embedded
	private Password password;

	/**
	 * Constructor.
	 *
	 * @deprecated Not for public use. This default constructor is meant to be
	 *             used only by frameworks.
	 */
	@Deprecated
	public Authentication() {
		this(new Login(), new Password());
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param login
	 *            The Authentication's {@code login}
	 * @param password
	 *            The Authentication's {@code password}
	 */
	public Authentication(Login login, Password password) {
		this.login = login;
		this.password = password;
	}

	/**
	 * Gets the Authentication's {@code login}.
	 *
	 * @return The Authentication's {@code login}
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * Gets the Authentication's {@code password}.
	 *
	 * @return The Authentication's {@code password}
	 */
	public Password getPassword() {
		return password;
	}

	/**
	 * Sets the Authentication's {@code login}.
	 *
	 * @param login
	 *            The Authentication's {@code login} to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}

	/**
	 * Sets the Authentication's {@code password}.
	 *
	 * @param password
	 *            The Authentication's {@code password} to set
	 */
	public void setPassword(Password password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[login=");
		builder.append(login);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
}