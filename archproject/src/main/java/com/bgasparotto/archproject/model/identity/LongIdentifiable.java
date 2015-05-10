package com.bgasparotto.archproject.model.identity;

/**
 * Interface that must be implemented by entities that will have their instances
 * identified by a {@code Long} type identifier.
 * 
 * @author Bruno Gasparotto
 *
 */
public interface LongIdentifiable {

	/**
	 * Gets the entity's {@code id}.
	 * 
	 * @return Entity's {@code id} which its value is greater than {@code 0}, or
	 *         {@code null} if this entity wasn't yet persisted on database.
	 * 
	 */
	Long getId();

	/**
	 * Set the entity's {@code id}.
	 * 
	 * @param id
	 *            Entity's {@code id}. Can't be {@code null} or less than
	 *            {@code 1}
	 */
	void setId(Long id);
}