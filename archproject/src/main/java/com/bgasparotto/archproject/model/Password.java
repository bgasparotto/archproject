package com.bgasparotto.archproject.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * Represents a {@code password}.
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
	 * Constructor.
	 *
	 * @deprecated Not for public use. This default constructor is meant to be
	 *             used only by frameworks.
	 */
	@Deprecated
	public Password() {
		this.value = "";
	}

	/**
	 * Constructor.
	 *
	 * @param value
	 */
	public Password(String value) {
		this.value = value;
	}

	/**
	 * Gets the {@code Password}'s {@code value}.
	 *
	 * @return {@code Password}'s {@code value}
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the {@code Password}'s {@code value}.
	 *
	 * @param The
	 *            {@code Password}'s {@code value} to set
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