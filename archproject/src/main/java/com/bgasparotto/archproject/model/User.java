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
 * Entity that represents an user of the system.
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
	 * Initialises an object using default values for its attributes and
	 * {@code null} for its {@code id}.
	 * </p>
	 * 
	 * @deprecated Not for public use. This default constructor is meant to be
	 *             used only by frameworks.
	 */
	@Deprecated
	public User() {
		this(null, new Credential(), LocalDateTime.now());
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * Initialises an object populating its attributes using the given
	 * parameters.
	 * </p>
	 * 
	 * @param id
	 *            The User's {@code id}
	 * @param credential
	 *            The User's {@code credential}
	 * @param registrationDate
	 *            The User's {@code registrationDate}
	 */
	public User(Long id,
				Credential credential,
				LocalDateTime registrationDate) {
		super(id);
		this.credential = credential;
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the User's {@code credential}.
	 *
	 * @return The User's {@code credential}
	 */
	public Credential getCredential() {
		return credential;
	}

	/**
	 * Gets the User's {@code registrationDate}.
	 *
	 * @return The User's {@code registrationDate}
	 */
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets the User's {@code credential}.
	 *
	 * @param credential
	 *            The User's {@code credential} to set
	 */
	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	/**
	 * Sets the User's {@code registrationDate}.
	 *
	 * @param registrationDate
	 *            The User's {@code registrationDate} to set
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