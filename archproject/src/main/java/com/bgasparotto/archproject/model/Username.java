package com.bgasparotto.archproject.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bgasparotto.archproject.infrastructure.validator.Rfc2822EmailValidator;

/**
 * Represents an {@code username}.
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
	 */
	public Username() {
		this("", "");
	}

	/**
	 * Constructor.
	 *
	 * @param username
	 *            The {@code Username}'s {@code username}
	 * @param email
	 *            The {@code Username}'s {@code email}
	 */
	public Username(String username, String email) {
		this.username = username;
		this.email = email;
	}

	/**
	 * Gets the {@code Username}'s {@code username}.
	 *
	 * @return {@code Username}'s {@code username}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the {@code Username}'s {@code username}.
	 *
	 * @param The
	 *            {@code Username}'s {@code username} to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the {@code Username}'s {@code email}.
	 *
	 * @return {@code Username}'s {@code email}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the {@code Username}'s {@code email}.
	 *
	 * @param The
	 *            {@code Username}'s {@code email} to set
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