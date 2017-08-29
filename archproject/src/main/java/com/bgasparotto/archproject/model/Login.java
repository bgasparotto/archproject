package com.bgasparotto.archproject.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bgasparotto.archproject.infrastructure.validator.Rfc2822EmailValidator;

/**
 * Represents a Login of the system.
 * 
 * @author Bruno Gasparotto
 *
 */
@Embeddable
public class Login {

	@Size(	min = 3,
			max = 64,
			message = "Username's lenght must be between {min} and {max}")
	private String username;

	@Pattern(	regexp = Rfc2822EmailValidator.RFC_2822_REGEXP,
				message = "Invalid e-mail format")
	private String email;

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * Initialises an object using system default values for its attributes.
	 * </p>
	 * 
	 * <p>
	 * Consider using {@link Login#Login(String, String) Login(String, String)}
	 * instead.
	 * </p>
	 * 
	 */
	public Login() {
		this("", "");
	}

	/**
	 * Constructor.
	 *
	 * @param username
	 *            The Login's {@code username}
	 * @param email
	 *            The Login's {@code email}
	 */
	public Login(String username, String email) {
		this.username = username;
		this.email = email;
	}

	/**
	 * Gets the Login's {@code username}.
	 *
	 * @return The Login's {@code username}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the Login's {@code email}.
	 *
	 * @return The Login's {@code email}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the Login's {@code username}.
	 *
	 * @param username
	 *            The Login's {@code username} to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the Login's {@code email}.
	 *
	 * @param email
	 *            The Login's {@code email} to set
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