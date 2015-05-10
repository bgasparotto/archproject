package com.bgasparotto.archproject.persistence.dao.jpa;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.persistence.dao.GenericDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;

/**
 * Abstract {@code DAO} implemented using {@code JPA}.
 * 
 * @author Bruno Gasparotto
 *
 * @param <T>
 *            Type of entity to be persisted by this {@code DAO}
 */
public abstract class JpaDao<T extends LongIdentifiable> implements
		GenericDao<T> {
	protected EntityManager entityManager;
	protected Class<T> clazz;
	protected Logger logger;

	/**
	 * Constructor.
	 * 
	 * @param entityManager
	 *            The {@code EntityManager} to be used by this {@code DAO}
	 * @param clazz
	 *            The entity class existing on the persistence unit of the given
	 *            {@code EntityManager}
	 * @param logger
	 *            The {@code Logger} to be used by this {@code DAO}
	 * @throws IllegalArgumentException
	 *             If any of the parameters are {@code null}
	 */
	public JpaDao(EntityManager entityManager, Class<T> clazz, Logger logger) {
		if (entityManager == null) {
			throw new IllegalArgumentException(
					"EntityManager for JpaDao can't be null.");
		}

		if (clazz == null) {
			throw new IllegalArgumentException(
					"Class type for JpaDao can't be null.");
		}

		if (logger == null) {
			throw new IllegalArgumentException(
					"Logger for JpaDao can't be null.");
		}

		this.entityManager = entityManager;
		this.clazz = clazz;
		this.logger = logger;
	}

	@Override
	@Transactional
	public T findById(Long id) {
		if (id == null) {
			return null;
		}

		T type = entityManager.find(clazz, id);
		return type;
	}

	@Override
	@Transactional
	public List<T> findAll() {
		try {
			Query query = entityManager.createQuery("from " + clazz.getName());

			/*
			 * The List<T> cast is safe because this query search only for T
			 * types.
			 */
			@SuppressWarnings("unchecked")
			List<T> result = (List<T>) query.getResultList();
			return result;
		} catch (PersistenceException e) {
			logger.error("Failed to perform a search on database", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Long persist(T type) throws GeneralPersistenceException {
		try {
			entityManager.persist(type);
			return type.getId();
		} catch (PersistenceException e) {
			String message = "Failed to persist an entity";
			logger.error(message, e);
			throw new GeneralPersistenceException(message, e);
		}

	}

	@Override
	@Transactional
	public T merge(T type) throws GeneralPersistenceException {
		try {
			T mergedType = entityManager.merge(type);
			return mergedType;
		} catch (IllegalArgumentException | TransactionRequiredException e) {
			String message = "Failed to merge an entity";
			logger.error(message, e);
			throw new GeneralPersistenceException(message, e);
		}
	}

	@Override
	@Transactional
	public void delete(T type) throws GeneralPersistenceException {
		try {
			entityManager.remove(type);
		} catch (IllegalArgumentException | TransactionRequiredException e) {
			String message = "Failed to delete an entity";
			logger.error(message, e);
			throw new GeneralPersistenceException(message, e);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) throws GeneralPersistenceException {
		try {
			T type = entityManager.getReference(clazz, id);
			entityManager.remove(type);
		} catch (IllegalArgumentException | EntityNotFoundException e) {
			String message = "Failed to delete an entity";
			logger.error(message, e);
			throw new GeneralPersistenceException(message, e);
		}
	}
}