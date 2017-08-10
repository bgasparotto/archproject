package com.bgasparotto.archproject.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

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

	@Size(	max = 36,
			message = "Verification code maximum lenght must be {max}")
	@Column(name = "verification_code")
	private String verificationCode;

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
	 * @param verificationCode
	 *            The user's verification code for its registration
	 */
	public Registration(LocalDateTime registrationDate,
			String verificationCode) {
		this.registrationDate = registrationDate;
		this.verificationCode = verificationCode;
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
	 * Gets the Registration's {@code verificationCode}.
	 *
	 * @return The Registration's {@code verificationCode}
	 */
	public String getVerificationCode() {
		return verificationCode;
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
	 * Sets the Registration's {@code verificationCode}.
	 *
	 * @param verificationCode
	 *            The Registration's {@code verificationCode} to set
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[registrationDate=");
		builder.append(registrationDate);
		builder.append(", verificationCode=");
		builder.append(verificationCode);
		builder.append("]");
		return builder.toString();
	}
}