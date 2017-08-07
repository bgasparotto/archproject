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
	private Username username;

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
		this(new Username(), new Password());
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param username
	 *            The Authentication's {@code username}
	 * @param password
	 *            The Authentication's {@code password}
	 */
	public Authentication(Username username, Password password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Gets the Authentication's {@code username}.
	 *
	 * @return The Authentication's {@code username}
	 */
	public Username getUsername() {
		return username;
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
	 * Sets the Authentication's {@code username}.
	 *
	 * @param username
	 *            The Authentication's {@code username} to set
	 */
	public void setUsername(Username username) {
		this.username = username;
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
		builder.append("[username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
}