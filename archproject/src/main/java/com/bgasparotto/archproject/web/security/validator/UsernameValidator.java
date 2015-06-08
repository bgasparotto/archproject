package com.bgasparotto.archproject.web.security.validator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Concrete implementation of {@link UserCredentialsValidator} for
 * {@code username} checking.
 * 
 * @author Bruno Gasparotto
 *
 */
@Named
@RequestScoped
public class UsernameValidator extends UserCredentialsValidator {

	@Override
	protected User findByUniqueField(Object value) throws ServiceException {
		String username = value.toString();
		User user = userService.findByUsername(username);
		return user;
	}

	@Override
	protected String fieldName() {
		return "Username";
	}
}