package com.bgasparotto.archproject.model;

import java.util.Optional;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Represents the credentials of the system.
 * 
 * @author Bruno Gasparotto
 *
 */
@Embeddable
public class Credential {

	@Embedded
	private Authentication authentication;

	@Embedded
	private RolesGroup rolesGroup;

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
	 * Consider using {@link Credential#Credential(Authentication, RolesGroup)
	 * Credential(Authentication, RolesGroup)} instead.
	 * </p>
	 * 
	 */
	public Credential() {
		this(new Authentication(), new RolesGroup());
	}

	/**
	 * Constructor.
	 *
	 * @param authentication
	 *            The Credential's {@code authentication}
	 * @param rolesGroup
	 *            The Credential's {@code rolesGroup}
	 */
	public Credential(Authentication authentication, RolesGroup rolesGroup) {
		this.authentication = authentication;
		this.rolesGroup = rolesGroup;
	}

	/**
	 * Gets the Credential's {@code authentication}.
	 *
	 * @return The Credential's {@code authentication}
	 */
	public Authentication getAuthentication() {
		return authentication;
	}

	/**
	 * See {@link Authentication#getLogin()}
	 */
	public Login getLogin() {
		Optional<Authentication> o = Optional.ofNullable(authentication);
		return o.map(Authentication::getLogin).orElse(null);
	}

	/**
	 * See {@link Authentication#getPassword()}
	 */
	public Password getPassword() {
		Optional<Authentication> o = Optional.ofNullable(authentication);
		return o.map(Authentication::getPassword).orElse(null);
	}
	
	/**
	 * Gets the Credential's {@code rolesGroup}.
	 *
	 * @return The Credential's {@code rolesGroup}
	 */
	public RolesGroup getRolesGroup() {
		return rolesGroup;
	}

	/**
	 * Sets the Credential's {@code authentication}.
	 *
	 * @param authentication
	 *            The Credential's {@code authentication} to set
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	/**
	 * Sets the Credential's {@code rolesGroup}.
	 *
	 * @param rolesGroup
	 *            The Credential's {@code rolesGroup} to set
	 */
	public void setRolesGroup(RolesGroup rolesGroup) {
		this.rolesGroup = rolesGroup;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[authentication=");
		builder.append(authentication);
		builder.append(", rolesGroup=");
		builder.append(rolesGroup);
		builder.append("]");
		return builder.toString();
	}
}