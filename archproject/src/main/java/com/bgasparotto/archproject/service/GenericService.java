package com.bgasparotto.archproject.service;

import java.util.List;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * <p>
 * A generic service interface, containing {@code CRUD} operations that may be
 * used by all application entities declared on
 * {@link com.bgasparotto.archproject.model}.
 * </p>
 * <p>
 * All other service interfaces should extend this interface.
 * </p>
 * <p>
 * This generic service definition was based on the {@code GenericDao}, this
 * way, some methods may appear to be similar to the ones declared on
 * {@code GenericDao}, but it's a common aspect since it sometimes acts like a
 * facade to the persistence layer.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 * @param <T>
 *            The type of {@link com.bgasparotto.archproject.model} package
 *            whose the service is for
 */
public interface GenericService<T extends LongIdentifiable> {

	/**
	 * Find an entity by its {@code id}.
	 * 
	 * @param id
	 *            The {@code id} of the entity to find
	 * @return Entity corresponding to the given {@code id}, or {@code null} if
	 *         the given {@code id}:
	 *         <ul>
	 *         <li>Is {@code null}</li>
	 *         <li>Is {@code not null} and its long value is less than {@code 1}
	 *         </li>
	 *         <li>If there is no entity on the system corresponding to the
	 *         given {@code id}</li>
	 *         </ul>
	 */
	T findById(Long id);

	/**
	 * Find all entities.
	 * 
	 * @return A {@code List} containing all entities of this type, or a empty
	 *         list if there is no entities on the system
	 */
	List<T> findAll();

	/**
	 * Insert an entity on the system.
	 * 
	 * @param type
	 *            Entity to be inserted
	 * @return The {@code id} generated for the inserted entity
	 * @throws ServiceException
	 *             if the insert operation failed
	 */
	Long insert(T type) throws ServiceException;

	/**
	 * Update an entity on the system.
	 * 
	 * @param type
	 *            Entity to be updated
	 * @return The updated entity reference obtained by this operation, which
	 *         may be different than the one passed as an argument to this
	 *         method
	 * @throws ServiceException
	 *             if the update operation failed
	 */
	T update(T type) throws ServiceException;

	/**
	 * Delete an entity from the system.
	 * 
	 * @param type
	 *            Entity to be deleted
	 * @throws ServiceException
	 *             if the delete operation failed
	 */
	void delete(T type) throws ServiceException;

	/**
	 * Delete an entity from the system by its {@code id}.
	 * 
	 * @param id
	 *            {@code Id} of the entity to be deleted
	 * @throws ServiceException
	 *             if the delete operation failed
	 */
	void delete(Long id) throws ServiceException;
}