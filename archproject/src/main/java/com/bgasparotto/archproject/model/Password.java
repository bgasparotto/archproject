package com.bgasparotto.archproject.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Represents a password.
 * 
 * @author Bruno Gasparotto
 *
 */
@Embeddable
public class Password {

	@Column(name = "password")
	@Size(	min = 6,
			max = 60,
			message = "Password's lenght must be between {min} and {max}")
	private String value;

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
	 * Consider using {@link Password#Password(String) Password(String)} or
	 * {@link Password#Password(String, boolean) Password(String, boolean)}
	 * instead.
	 * </p>
	 * 
	 */
	public Password() {
		this("");
	}

	/**
	 * Constructor.
	 *
	 * @param value
	 *            The Password's {@code plain text} value to be stored
	 *            <strong>AS IS</strong>
	 */
	public Password(String value) {
		this(value, false);
	}
	
	/**
	 * Constructor.
	 *
	 * @param value
	 *            The Password's value to be stored.
	 * @param encrypt
	 *            {@code true} if the password is required to be encrypted
	 *            before being stored, {@code false} otherwise
	 */
	public Password(String value, boolean encrypt) {
		if (encrypt) {
			encryptAndSet(value);
			return;
		}
		
		this.value = value;
	}

	/**
	 * Gets the Password's {@code value}.
	 *
	 * @return The Password's {@code value}
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>
	 * Sets the Password's {@code value} <strong>AS IS</strong> without any
	 * encryption. This method is usually invoked by frameworks or services of a
	 * Java container.
	 * </p>
	 * <p>
	 * For proper encryption for a plain text password before setting it, use
	 * {@link Password#encryptAndSet(String) encryptAndSet(String)} instead.
	 * </p>
	 *
	 * @param value
	 *            The Password's {@code value} to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Encrypts the {@code plain text} password's value using {@code BCrypt}
	 * algorithm and sets the encrypted value to this current instance.
	 * 
	 * @param value
	 *            Plain text password to be encrypted and set
	 * 
	 * @throws IllegalArgumentException
	 *             if the provided {@code value} is {@code null} or
	 *             {@code empty}
	 */
	public void encryptAndSet(String value) {
		
		/* Encrypts the user's password using BCrypt. */
		String salt = BCrypt.gensalt();
		String encryptedPassword = BCrypt.hashpw(value, salt);
		this.setValue(encryptedPassword);
	}
	
	/**
	 * Checks if the provided plain text password matches the one previously
	 * stored in its encrypted form using the {@code BCrypt} algorithm.
	 * 
	 * @param password
	 *            The plain text password value to be checked
	 * @return {@code true} if the passwords match, {@code false} if they don't
	 *         or the provided {@code password} is null or empty.
	 */
	public boolean checkWithBCrypt(String password) {
		if ((password == null) || (password.isEmpty())) {
			return false;
		}
		
		return BCrypt.checkpw(password, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
}