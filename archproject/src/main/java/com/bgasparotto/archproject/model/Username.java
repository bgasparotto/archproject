package com.bgasparotto.archproject.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bgasparotto.archproject.infrastructure.validator.Rfc2822EmailValidator;

/**
 * Represents an username.
 * 
 * @author Bruno Gasparotto
 *
 */
@Embeddable
public class Username {

	@Size(	min = 3,
			max = 64,
			message = "Username's lenght must be between {min} and {max}")
	private String username;

	@Pattern(	regexp = Rfc2822EmailValidator.RFC_2822_REGEXP,
				message = "Invalid e-mail format")
	private String email;

	/**
	 * Constructor.
	 *
	 * @deprecated Not for public use. This default constructor is meant to be
	 *             used only by frameworks.
	 */
	@Deprecated
	public Username() {
		this("", "");
	}

	/**
	 * Constructor.
	 *
	 * @param username
	 *            The Username's {@code username}
	 * @param email
	 *            The Username's {@code email}
	 */
	public Username(String username, String email) {
		this.username = username;
		this.email = email;
	}

	/**
	 * Gets the Username's {@code username}.
	 *
	 * @return The Username's {@code username}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the Username's {@code email}.
	 *
	 * @return The Username's {@code email}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the Username's {@code username}.
	 *
	 * @param username
	 *            The Username's {@code username} to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the Username's {@code email}.
	 *
	 * @param email
	 *            The Username's {@code email} to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[username=");
		builder.append(username);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}
}