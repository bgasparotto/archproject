package com.bgasparotto.archproject.web.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.UserService;

/**
 * <p>
 * User's registration managed bean.
 * </p>
 * <p>
 * Provides implementation for registering a new user on the system.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 */
@Named
@RequestScoped
public class UserMb {
	private User user;

	@Inject
	private UserService userService;

	/**
	 * Constructor.
	 */
	@SuppressWarnings("deprecation")
	public UserMb() {
		user = new User();
	}

	/**
	 * Gets the UserMb's {@code user}.
	 *
	 * @return The UserMb's {@code user}
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Registers an user with the information filled on the form.
	 * 
	 * @param authentication
	 *            The authentication details of the user being registered
	 * @return The implicit navigation outcome for the next page to be rendered
	 */
	public String register(Authentication authentication) {
		try {
			userService.register(authentication);
			return "registered";
		} catch (ServiceException e) {
			return "error";
		}
	}
	
	public String validate(User user) {
		Credential credential = user.getCredential();
		Authentication authentication = credential.getAuthentication();
		Login login = authentication.getLogin();
		String username = login.getUsername();
		
		Registration registration = user.getRegistration();
		String verificationCode = registration.getVerificationCode();
		
		try {
			userService.validate(username, verificationCode);
		} catch (ServiceException e) {
			return "error";
		}
		
		return null;
	}
}