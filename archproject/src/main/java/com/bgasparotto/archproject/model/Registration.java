package com.bgasparotto.archproject.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents the user's registration details.
 * 
 * @author Bruno Gasparotto
 *
 */
@Embeddable
public class Registration {

	@Column(name = "registration_date")
	private LocalDateTime registrationDate;

	@Column(name = "validation_code")
	private String validationCode;

	/**
	 * Constructor.
	 *
	 * @deprecated Not for public use. This default constructor is meant to be
	 *             used only by frameworks.
	 */
	@Deprecated
	public Registration() {
		this(LocalDateTime.now(), null);
	}

	/**
	 * Constructor.
	 *
	 * @param registrationDate
	 *            The user's registration date
	 * @param validationCode
	 *            The user's validation code for its registration
	 */
	public Registration(LocalDateTime registrationDate, String validationCode) {
		this.registrationDate = registrationDate;
		this.validationCode = validationCode;
	}

	/**
	 * Gets the Registration's {@code registrationDate}.
	 *
	 * @return The Registration's {@code registrationDate}
	 */
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Gets the Registration's {@code validationCode}.
	 *
	 * @return The Registration's {@code validationCode}
	 */
	public String getValidationCode() {
		return validationCode;
	}

	/**
	 * Sets the Registration's {@code registrationDate}.
	 *
	 * @param registrationDate
	 *            The Registration's {@code registrationDate} to set
	 */
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Sets the Registration's {@code validationCode}.
	 *
	 * @param validationCode
	 *            The Registration's {@code validationCode} to set
	 */
	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[registrationDate=");
		builder.append(registrationDate);
		builder.append(", validationCode=");
		builder.append(validationCode);
		builder.append("]");
		return builder.toString();
	}
}