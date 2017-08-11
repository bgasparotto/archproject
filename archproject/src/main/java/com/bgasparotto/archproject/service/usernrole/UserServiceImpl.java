package com.bgasparotto.archproject.service.usernrole;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;

import com.bgasparotto.archproject.infrastructure.validator.EmailValidator;
import com.bgasparotto.archproject.infrastructure.validator.Rfc2822EmailValidator;
import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.model.Roles;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.model.Username;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.AbstractService;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.mail.MailService;

/**
 * {@link UserService} implementation.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class UserServiceImpl extends AbstractService<User>
		implements UserService {

	@Inject
	private RoleService roleService;
	
	@Inject
	private MailService mailService;

	/**
	 * Constructor.
	 * 
	 * @param userDao
	 *            {@link UserDao} implementation to be used by this service
	 * @param logger
	 *            {@link Logger} implementation to be used by this service
	 */
	@Inject
	public UserServiceImpl(UserDao userDao, Logger logger) {
		super(userDao, logger);
	}

	/**
	 * Set the {@code roleService}.
	 *
	 * @param roleService
	 *            {@link RoleService} implementation to be used by this service
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
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
	public Long register(Authentication authentication)
			throws ServiceException {
		Objects.requireNonNull(authentication, "Authentication can't be null.");

		Username username = authentication.getUsername();
		Objects.requireNonNull(username,
				"Authentication's username can't be null.");

		String usernameValue = username.getUsername();
		if ((usernameValue == null) || (usernameValue.isEmpty())) {
			throw new IllegalStateException("Username can't be null or empty.");
		}

		String email = username.getEmail();
		if ((email == null) || (email.isEmpty())) {
			throw new IllegalStateException("Email can't be null or empty.");
		}

		Password password = authentication.getPassword();
		Objects.requireNonNull(password,
				"Authentication's password can't be null.");
		String passwordValue = password.getValue();
		if ((passwordValue == null) || (passwordValue.isEmpty())) {
			throw new IllegalStateException("Password can't be null or empty.");
		}

		/* Encrypts the user's password using BCrypt. */
		String salt = BCrypt.gensalt();
		String encryptedPassword = BCrypt.hashpw(passwordValue, salt);
		password.setValue(encryptedPassword);

		/* Create a user and assign the most basic and default role. */
		Role defaultRole = roleService.findDefault();
		Roles roles = new Roles(defaultRole);
		Credential credential = new Credential(authentication, roles);
		LocalDateTime now = LocalDateTime.now();
		String validationCode = UUID.randomUUID().toString();
		Registration registration = new Registration(now, validationCode);
		User user = new User(null, credential, registration);

		/* Inserts the new user using the encrypted password. */
		Long insertedId = this.insert(user);
		
		/* Sends the validation code by e-mail. */
		try {
			mailService.sendValidationEmail(user);
		} catch (ServiceException e) {
			String message = "Failed to send validation e-mail. User " + usernameValue + " may need"
					+ " to send it again.";
			logger.error(message);
		}
		
		return insertedId;
	}

	@Override
	public User authenticate(String usernameOrEmail, String password) {
		if ((usernameOrEmail == null) || (password == null)) {
			return null;
		}

		EmailValidator emailValidator = new Rfc2822EmailValidator();
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
		String storedEncryptedPassword = user.getCredential()
				.getAuthentication().getPassword().getValue();
		if (BCrypt.checkpw(password, storedEncryptedPassword)) {
			return user;
		}

		return null;
	}
}