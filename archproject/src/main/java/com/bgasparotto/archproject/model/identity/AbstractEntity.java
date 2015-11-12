package com.bgasparotto.archproject.model.identity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract class for JPA entities.
 * 
 * @author Bruno Gasparotto
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements LongIdentifiable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	/**
	 * Constructor.
	 *
	 */
	public AbstractEntity() {
	}

	/**
	 * Constructor.
	 *
	 * @param id
	 *            The {@code AbstractEntity}'s {@code id}
	 */
	public AbstractEntity(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}