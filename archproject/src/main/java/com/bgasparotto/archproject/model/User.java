package com.bgasparotto.archproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bgasparotto.archproject.infrastructure.validator.EmailValidator;
import com.bgasparotto.archproject.model.identity.LongIdentifiable;

/**
 * Entity that represents an {@code user} of the system.
 * 
 * @author Bruno Gasparotto
 *
 */
@Entity
@Table(name = "user", schema = "security")
public class User implements LongIdentifiable {

	@Id
	@Column(name = "id_user", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(	min = 3,
			max = 64,
			message = "Username's lenght must be between {min} and {max}")
	private String username;

	@Size(	min = 6,
			max = 60,
			message = "Password's lenght must be between {min} and {max}")
	private String password;

	@Pattern(	regexp = EmailValidator.RFC_2822_REGEXP,
				message = "Invalid e-mail format")
	private String email;

	@ManyToMany
	@JoinTable(	name = "user_role",
				schema = "security",
				joinColumns = { @JoinColumn(name = "id_user",
											columnDefinition = "int4") },
				inverseJoinColumns = { @JoinColumn(	name = "id_role",
													columnDefinition = "int4") })
	private Set<Role> roles;

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
		this(null, "", "", "", new HashSet<Role>());
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
	 * @param username
	 *            The user's {@code username}
	 * @param password
	 *            The user's {@code password}
	 * @param email
	 *            The user's {@code e-mail}
	 */
	public User(Long id, String username, String password, String email) {
		this(id, username, password, email, new HashSet<Role>());
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
	 * @param username
	 *            The user's {@code username}
	 * @param password
	 *            The user's {@code password}
	 * @param email
	 *            The user's {@code e-mail}
	 * @param roles
	 *            The user's {@code roles}
	 */
	public User(Long id,
				String username,
				String password,
				String email,
				Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Get the User's {@code username}.
	 *
	 * @return User's {@code username}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the User's {@code password}.
	 *
	 * @return User's {@code password}
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Get the User's {@code email}.
	 *
	 * @return User's {@code email}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Get the User's {@code roles}.
	 *
	 * @return User's {@code roles}
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Set the User's {@code username}.
	 *
	 * @param username
	 *            The {@code username} to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Set the User's {@code password}.
	 *
	 * @param password
	 *            The {@code password} to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Set the User's {@code email}.
	 *
	 * @param email
	 *            The {@code email} to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Set the User's {@code roles}.
	 * 
	 * @param roles
	 *            The {@code roles} to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles.clear();
		this.roles.addAll(roles);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);

		/*
		 * Remove "roles" defined by the two following lines from this
		 * toString() overriding if the relationship mapping between User and
		 * Role happens to become bidirectional, to avoid a StackOverflowError
		 * due to a possible recursive call, depending on how the toString()
		 * method is defined on Role Entity.
		 */
		builder.append(", roles=");
		builder.append(roles);
		builder.append("]");
		return builder.toString();
	}
}