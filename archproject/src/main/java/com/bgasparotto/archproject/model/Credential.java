package com.bgasparotto.archproject.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Represents credentials of the system.
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
	 */
	public Credential() {
		this(new Authentication(), new Roles());
	}

	/**
	 * Constructor.
	 *
	 * @param authentication
	 *            The {@code Credential}'s {@code authentication}
	 * @param roles
	 *            The {@code Credential}'s {@code roles}
	 */
	public Credential(Authentication authentication, Roles roles) {
		this.authentication = authentication;
		this.roles = roles;
	}

	/**
	 * Gets the {@code Credential}'s {@code authentication}.
	 *
	 * @return {@code Credential}'s {@code authentication}
	 */
	public Authentication getAuthentication() {
		return authentication;
	}

	/**
	 * Gets the {@code Credential}'s {@code roles}.
	 *
	 * @return {@code Credential}'s {@code roles}
	 */
	public Roles getRoles() {
		return roles;
	}

	/**
	 * Sets the {@code Credential}'s {@code authentication}.
	 *
	 * @param The
	 *            {@code Credential}'s {@code authentication} to set
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	/**
	 * Sets the {@code Credential}'s {@code roles}.
	 *
	 * @param The
	 *            {@code Credential}'s {@code roles} to set
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