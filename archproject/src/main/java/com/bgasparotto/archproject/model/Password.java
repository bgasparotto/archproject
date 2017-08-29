package com.bgasparotto.archproject.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

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
	 * Consider using {@link Password#Password(String) Password(String)}
	 * instead.
	 * </p>
	 * 
	 */
	public Password() {
		this.value = "";
	}

	/**
	 * Constructor.
	 *
	 * @param value
	 *            The Password's {@code value}
	 */
	public Password(String value) {
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
	 * Sets the Password's {@code value}.
	 *
	 * @param value
	 *            The Password's {@code value} to set
	 */
	public void setValue(String value) {
		this.value = value;
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