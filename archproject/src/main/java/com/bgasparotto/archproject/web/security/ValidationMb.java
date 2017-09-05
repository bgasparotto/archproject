package com.bgasparotto.archproject.web.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.InvalidVerificationCodeException;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.UserService;

/**
 * <p>
 * User's validation managed bean.
 * </p>
 * <p>
 * Provides implementation for validating a new user on the system.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 */
@Named
@RequestScoped
public class ValidationMb {
	private User user;
	private Status status;

	private UserService userService;

	/**
	 * Constructor.
	 *
	 * @param userService
	 *            {@code UserService}'s implementation to be used by this
	 *            managed bean
	 */
	@Inject
	public ValidationMb(UserService userService) {
		this.user = new User();
		this.status = Status.IN_PROGRESS;

		this.userService = userService;
	}

	/**
	 * Gets the ValidationMb's {@code user}.
	 *
	 * @return The ValidationMb's {@code user}
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Gets the ValidationMb's {@code status}.
	 *
	 * @return The ValidationMb's {@code status}
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Gets the ValidationMb's {@code userService}.
	 *
	 * @return The ValidationMb's {@code userService}
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Validates an user.
	 * 
	 * @param user
	 *            User to be validated
	 * @return The implicit navigation outcome for the next page to be rendered,
	 *         if defined, or {@code null} otherwise
	 */
	public String validate(User user) {
		Login login = user.getLogin();
		String username = login.getUsername();

		Registration registration = user.getRegistration();
		String verificationCode = registration.getVerificationCode();

		try {
			userService.validate(username, verificationCode);
			this.status = Status.SUCCESS;
		} catch (InvalidVerificationCodeException e) {
			this.status = Status.INVALID_VERIFICATION_CODE;
		} catch (ServiceException e) {
			this.status = Status.ERROR;
			return "error";
		}

		return null;
	}

	/**
	 * Enum to be externalised demonstrating the validation status of the
	 * current managed bean instance.
	 * 
	 * @author Bruno Gasparotto
	 *
	 */
	public enum Status {
		IN_PROGRESS,
		SUCCESS,
		ERROR,
		INVALID_VERIFICATION_CODE
	}
}