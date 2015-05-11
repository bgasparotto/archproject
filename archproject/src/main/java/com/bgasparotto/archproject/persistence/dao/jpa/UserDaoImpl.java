package com.bgasparotto.archproject.persistence.dao.jpa;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;

/**
 * {@link UserDao} implementation using {@code JPA}.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class UserDaoImpl extends JpaDao<User> implements UserDao {

	/**
	 * Constructor.
	 * 
	 * @param entityManager
	 *            The {@code EntityManager} to be used by this {@code DAO}
	 */
	@Inject
	public UserDaoImpl(EntityManager entityManager, Logger logger) {
		super(entityManager, User.class, logger);
	}

	@Override
	@Transactional
	public User findByUsername(String username)
			throws GeneralPersistenceException {
		String sQuery = "from User u where u.username = :username";
		Query query = entityManager.createQuery(sQuery);
		query.setParameter("username", username);

		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			String message = "Failed to find an user by its username.";
			logger.error(message, e);
			throw new GeneralPersistenceException(message, e);
		}
	}

	@Override
	@Transactional
	public User findByEmail(String email) throws GeneralPersistenceException {
		String sQuery = "from User u where u.email = :email";
		Query query = entityManager.createQuery(sQuery);
		query.setParameter("email", email);

		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			String message = "Failed to find an user by its email.";
			logger.error(message, e);
			throw new GeneralPersistenceException(message, e);
		}
	}
}