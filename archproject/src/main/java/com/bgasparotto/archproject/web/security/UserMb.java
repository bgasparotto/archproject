package com.bgasparotto.archproject.web.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.UserService;
import com.bgasparotto.archproject.service.exception.ServiceException;

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
	public UserMb() {
		user = new User();
	}

	/**
	 * Get the UserMb's {@code user}.
	 *
	 * @return UserMb's {@code user}
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
}