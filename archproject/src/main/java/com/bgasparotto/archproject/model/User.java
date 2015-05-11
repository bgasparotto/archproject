package com.bgasparotto.archproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;

/**
 * Entity that represents a {@code user} of the system.
 * 
 * @author Bruno Gasparotto
 *
 */
@Entity
@Table(schema = "security")
public class User implements LongIdentifiable {

	@Id
	@Column(name = "id_user", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String email;

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
		this(null, "", "", "");
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
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
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
		builder.append("]");
		return builder.toString();
	}
}