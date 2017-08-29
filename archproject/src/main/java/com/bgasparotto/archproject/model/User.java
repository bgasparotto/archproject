package com.bgasparotto.archproject.model;

import java.util.Optional;

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
@AttributeOverride(
		name = "id", 
		column = @Column(name = "id_user", columnDefinition = "serial"))
public class User extends AbstractEntity {

	@Embedded
	private Credential credential;

	@Embedded
	private Registration registration;

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * Initialises an object using system default values for its attributes and
	 * {@code null} for its {@code id}.
	 * </p>
	 * 
	 * <p>
	 * Consider using {@link User#User(Long, Credential, Registration)
	 * User(Long, Credential, Registration)} instead.
	 * </p>
	 * 
	 */
	public User() {
		this(null, new Credential(), new Registration());
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
	 * @param registration
	 *            The User's {@code registration}
	 */
	public User(Long id, Credential credential, Registration registration) {
		super(id);
		this.credential = credential;
		this.registration = registration;
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
	 * See {@link Credential#getAuthentication()}
	 */
	public Authentication getAuthentication() {
		Optional<Credential> o = Optional.ofNullable(credential);
		return o.map(Credential::getAuthentication).orElse(null);
	}

	/**
	 * See {@link Credential#getLogin()}
	 */
	public Login getLogin() {
		Optional<Credential> o = Optional.ofNullable(credential);
		return o.map(Credential::getLogin).orElse(null);
	}

	/**
	 * See {@link Credential#getPassword()}
	 */
	public Password getPassword() {
		Optional<Credential> o = Optional.ofNullable(credential);
		return o.map(Credential::getPassword).orElse(null);
	}

	/**
	 * See {@link Credential#getRolesGroup()}
	 */
	public RolesGroup getRolesGroup() {
		Optional<Credential> o = Optional.ofNullable(credential);
		return o.map(Credential::getRolesGroup).orElse(null);
	}

	/**
	 * Gets the User's {@code registration}.
	 *
	 * @return The User's {@code registration}
	 */
	public Registration getRegistration() {
		return registration;
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
	 * Sets the User's {@code registration}.
	 *
	 * @param registration
	 *            The User's {@code registration} to set
	 */
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[credential=");
		builder.append(credential);
		builder.append(", registration=");
		builder.append(registration);
		builder.append("]");
		return builder.toString();
	}
}