package com.bgasparotto.archproject.service;

import java.util.List;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.persistence.dao.GenericDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Abstract service implementation.
 * 
 * @author Bruno Gasparotto
 *
 * @param <T>
 *            Type of entity to be operated by this service
 */
public abstract class AbstractService<T extends LongIdentifiable>
		implements GenericService<T> {

	/**
	 * The {@code DAO} of the corresponding type whose this service is for.
	 */
	protected GenericDao<T> dao;

	protected Logger logger;

	/**
	 * Constructor.
	 * 
	 * @param dao
	 *            {@code DAO} implementation of the same type {@code T} of the
	 *            service
	 * @param logger
	 *            Logger to be used by this service
	 */
	public AbstractService(GenericDao<T> dao, Logger logger) {
		this.dao = dao;
		this.logger = logger;
	}

	@Override
	public T findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	public Long insert(T type) throws ServiceException {
		try {
			Long generatedId = dao.persist(type);
			return generatedId;
		} catch (GeneralPersistenceException e) {
			String message = "Failed to insert entity.";
			logger.error(message, e);
			throw new ServiceException(message, e);
		}
	}

	@Override
	public T update(T type) throws ServiceException {
		try {
			T mergedEntity = dao.merge(type);
			return mergedEntity;
		} catch (GeneralPersistenceException e) {
			String message = "Failed to update entity.";
			logger.error(message, e);
			throw new ServiceException(message, e);
		}
	}

	@Override
	public void delete(T type) throws ServiceException {
		try {
			dao.delete(type);
		} catch (GeneralPersistenceException e) {
			String message = "Failed to delete entity.";
			logger.error(message, e);
			throw new ServiceException(message, e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (GeneralPersistenceException e) {
			String message = "Failed to delete entity.";
			logger.error(message, e);
			throw new ServiceException(message, e);
		}
	}
}