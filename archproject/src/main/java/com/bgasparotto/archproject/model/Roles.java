package com.bgasparotto.archproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Represents a set of roles.
 * 
 * @author Bruno Gasparotto
 *
 */
@Embeddable
public class Roles {

	@ManyToMany
	@JoinTable(	name = "user_role",
				schema = "security",
				joinColumns = { @JoinColumn(name = "id_user",
											columnDefinition = "int4") },
				inverseJoinColumns = {
						@JoinColumn(name = "id_role",
									columnDefinition = "int4") })
	private Set<Role> roles;

	/**
	 * Constructor.
	 */
	public Roles() {
		this(new HashSet<>());
	}

	/**
	 * Constructor.
	 *
	 * @param role
	 *            The first Role to assign
	 */
	public Roles(Role role) {
		this();
		roles.add(role);
	}

	/**
	 * Constructor.
	 *
	 * @param roles
	 *            The initial set of Roles
	 */
	public Roles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the set of {@code Roles}.
	 *
	 * @return Set of {@code Roles}
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the set of {@code Roles}.
	 * 
	 * @param roles
	 *            The set of {@code Roles} to be set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles.clear();
		this.roles.addAll(roles);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		/*
		 * Remove "roles" defined by the two following lines from this
		 * toString() overriding if the relationship mapping between another
		 * entity and Role may happen to become bidirectional, to avoid a
		 * StackOverflowError due to a possible recursive call, depending on how
		 * the toString() method is defined on Role Entity.
		 */
		builder.append("[roles=");
		builder.append(roles);
		builder.append("]");
		return builder.toString();
	}
}