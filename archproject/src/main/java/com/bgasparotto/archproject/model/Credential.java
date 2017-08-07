package com.bgasparotto.archproject.model;

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
	private Roles roles;

	/**
	 * Constructor.
	 *
	 * @deprecated Not for public use. This default constructor is meant to be
	 *             used only by frameworks.
	 */
	@Deprecated
	public Credential() {
		this(new Authentication(), new Roles());
	}

	/**
	 * Constructor.
	 *
	 * @param authentication
	 *            The Credential's {@code authentication}
	 * @param roles
	 *            The Credential's {@code roles}
	 */
	public Credential(Authentication authentication, Roles roles) {
		this.authentication = authentication;
		this.roles = roles;
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
	 * Gets the Credential's {@code roles}.
	 *
	 * @return The Credential's {@code roles}
	 */
	public Roles getRoles() {
		return roles;
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
	 * Sets the Credential's {@code roles}.
	 *
	 * @param roles
	 *            The Credential's {@code roles} to set
	 */
	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[authentication=");
		builder.append(authentication);
		builder.append(", roles=");
		builder.append(roles);
		builder.append("]");
		return builder.toString();
	}
}