package com.bgasparotto.archproject.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bgasparotto.archproject.model.identity.AbstractEntity;

/**
 * Entity that represents an {@code user} of the system.
 * 
 * @author Bruno Gasparotto
 *
 */
@Entity
@Table(name = "user", schema = "security")
@AttributeOverride(	name = "id",
					column = @Column(	name = "id_user",
										columnDefinition = "serial") )
public class User extends AbstractEntity {

	@Embedded
	private Credential credential;

	@Column(name = "registration_date")
	private LocalDateTime registrationDate;

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * Initializes a object using default values for its attributes, and
	 * {@code null} as its {@code id}.
	 * </p>
	 */
	public User() {
		this(null, new Credential(), LocalDateTime.now());
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * Initializes a object populating its attributes using the given
	 * parameters.
	 * </p>
	 * 
	 * @param id
	 *            The user's {@code id}
	 * @param credential
	 *            The user's {@code credential}
	 * @param registrationDate
	 *            The user's {@code registration date}
	 */
	public User(Long id,
				Credential credential,
				LocalDateTime registrationDate) {
		super(id);
		this.credential = credential;
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the {@code User}'s {@code credential}.
	 *
	 * @return {@code User}'s {@code credential}
	 */
	public Credential getCredential() {
		return credential;
	}

	/**
	 * Get the User's {@code registrationDate}.
	 *
	 * @return User's {@code registrationDate}
	 */
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets the {@code User}'s {@code credential}.
	 *
	 * @param The
	 *            {@code User}'s {@code credential} to set
	 */
	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	/**
	 * Set the User's {@code registrationDate}.
	 *
	 * @param registrationDate
	 *            The {@code registrationDate} to set
	 */
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", credential=");
		builder.append(credential);
		builder.append(", registrationDate=");
		builder.append(formatter.format(registrationDate));
		builder.append("]");
		return builder.toString();
	}
}