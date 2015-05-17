package com.bgasparotto.archproject.service.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;

import com.bgasparotto.archproject.infrastructure.validator.EmailValidator;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.UserService;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * {@link UserService} implementation.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class UserServiceImpl extends AbstractService<User> implements
		UserService {

	/**
	 * Constructor.
	 * 
	 * @param userDao
	 *            {@code UserDao} implementation to be used by this service
	 */
	@Inject
	public UserServiceImpl(UserDao userDao, Logger logger) {
		super(userDao, logger);
	}

	@Override
	public User findByUsername(String username) throws ServiceException {
		if ((username == null) || (username.isEmpty())) {
			return null;
		}

		try {
			User user = ((UserDao) dao).findByUsername(username);
			return user;
		} catch (GeneralPersistenceException e) {
			String message = "Failed to find an user by its username";
			logger.error(message, e);
			throw new ServiceException(message, e);
		}
	}

	@Override
	public User findByEmail(String email) throws ServiceException {
		if ((email == null) || email.isEmpty()) {
			return null;
		}

		try {
			User user = ((UserDao) dao).findByEmail(email);
			return user;
		} catch (GeneralPersistenceException e) {
			String message = "Failed to find an user by its email";
			logger.error(message, e);
			throw new ServiceException(message, e);
		}
	}

	@Override
	public Long register(User user) throws ServiceException {
		if (user == null) {
			throw new IllegalArgumentException("User can't be null.");
		}

		String username = user.getUsername();
		if ((username == null) || (username.isEmpty())) {
			throw new IllegalStateException(
					"User's username can't be null or empty.");
		}

		String password = user.getPassword();
		if ((password == null) || (password.isEmpty())) {
			throw new IllegalStateException(
					"User's password can't be null or empty.");
		}

		String email = user.getEmail();
		if ((email == null) || (email.isEmpty())) {
			throw new IllegalStateException(
					"User's email can't be null or empty.");
		}

		/* Encrypts the user's password using BCrypt. */
		String salt = BCrypt.gensalt();
		String encryptedPassword = BCrypt.hashpw(password, salt);
		user.setPassword(encryptedPassword);

		/* Inserts the new user using the encrypted password. */
		try {
			Long insertedId = dao.persist(user);
			return insertedId;
		} catch (GeneralPersistenceException e) {
			String messsage = "Failed to register user " + user.getUsername()
					+ ".";
			logger.error(messsage);
			throw new ServiceException(messsage, e);
		}
	}

	@Override
	public User authenticate(String usernameOrEmail, String password) {
		if ((usernameOrEmail == null) || (password == null)) {
			return null;
		}

		EmailValidator emailValidator = new EmailValidator();
		User user = null;

		try {
			/*
			 * If the input is successfully validate as an e-mail, then the user
			 * will be searched by its email address.
			 */
			if (emailValidator.validate(usernameOrEmail)) {
				user = this.findByEmail(usernameOrEmail);
			}

			/*
			 * If no user was found by its email or the input isn't an email,
			 * then its searched by username.
			 */
			if (user == null) {
				user = this.findByUsername(usernameOrEmail);
			}

		} catch (ServiceException e) {
			logger.error("Failed to search an user.", e);
			return null;
		}

		/* If no user was found either, then it doesn't exist. */
		if (user == null) {
			return null;
		}

		/*
		 * Retrieve the stored encrypted password and tests against the given
		 * plain text password.
		 */
		String storedEncryptedPassword = user.getPassword();
		if (BCrypt.checkpw(password, storedEncryptedPassword)) {
			return user;
		}

		return null;
	}
}