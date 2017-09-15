package com.bgasparotto.archproject.service.usernrole;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.bgasparotto.archproject.infrastructure.validator.EmailValidator;
import com.bgasparotto.archproject.infrastructure.validator.Rfc2822EmailValidator;
import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.model.RolesGroup;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.AbstractService;
import com.bgasparotto.archproject.service.exception.InvalidVerificationCodeException;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.exception.UserDoesNotExistException;
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
	 * Sets the UserServiceImpl's {@code roleService}.
	 *
	 * @param roleService
	 *            the {@code UserServiceImpl}'s {@code roleService} to set
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * Sets the UserServiceImpl's {@code mailService}.
	 *
	 * @param mailService
	 *            the {@code UserServiceImpl}'s {@code mailService} to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
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
			throw new ServiceException("Failed to find an user by its username",
					e);
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
			throw new ServiceException("Failed to find an user by its email",
					e);
		}
	}

	@Override
	public Long register(Authentication authentication)
			throws ServiceException {
		Objects.requireNonNull(authentication, "Authentication can't be null.");

		Login login = authentication.getLogin();
		Objects.requireNonNull(login,
				"Authentication's login can't be null.");

		String username = login.getUsername();
		if ((username == null) || (username.isEmpty())) {
			throw new IllegalStateException(
					"Login's username can't be null or empty.");
		}

		String email = login.getEmail();
		if ((email == null) || (email.isEmpty())) {
			throw new IllegalStateException(
					"Login's email can't be null or empty.");
		}

		Password password = authentication.getPassword();
		Objects.requireNonNull(password,
				"Authentication's password can't be null.");
		String passwordValue = password.getValue();
		if ((passwordValue == null) || (passwordValue.isEmpty())) {
			throw new IllegalStateException("Password can't be null or empty.");
		}
		password.encryptAndSet(passwordValue);

		/* Create a user and assign the most basic and default role. */
		Role defaultRole = roleService.findDefault();
		RolesGroup rolesGroup = new RolesGroup(defaultRole);
		Credential credential = new Credential(authentication, rolesGroup);
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
			String message = "Failed to send validation e-mail. User "
					+ username + " may need to send it again.";
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
			 * If the input is successfully validated as an e-mail, the user
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

		/* Tests the existent password against the provided one. */
		Password userPassword = user.getPassword();
		if (userPassword.checkWithBCrypt(password)) {
			return user;
		}

		return null;
	}

	@Override
	public User changePassword(String usernameOrEmail, String oldPassword,
			String newPassword) throws ServiceException {
		
		User user = this.authenticate(usernameOrEmail, oldPassword);
		if (user == null) {
			return null;
		}		
		
		Password password = user.getPassword();
		password.encryptAndSet(newPassword);
		
		try {
			User updatedUser = dao.mergeFlush(user);
			return updatedUser;
		} catch (GeneralPersistenceException e) {
			throw new ServiceException("Failed to update an user.", e);
		}
	}

	@Override
	public User validate(String username, String verificationCode)
			throws ServiceException {
		User user = this.findByUsername(username);
		if (user == null) {
			throw new UserDoesNotExistException("User " + username
					+ " doesn't exist.");
		}
		
		Registration registration = user.getRegistration();
		String existentVerificationCode = registration.getVerificationCode();
		if (!existentVerificationCode.equals(verificationCode)) {
			String message = "Invalid verification code for user " + username;
			throw new InvalidVerificationCodeException(message);
		}
		
		return user;
	}
}