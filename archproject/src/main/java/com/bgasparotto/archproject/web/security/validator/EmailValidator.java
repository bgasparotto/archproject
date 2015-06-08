package com.bgasparotto.archproject.web.security.validator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Concrete implementation of {@link UserCredentialsValidator} for
 * {@code e-mail} checking.
 * 
 * @author Bruno Gasparotto
 *
 */
@Named
@RequestScoped
public class EmailValidator extends UserCredentialsValidator {

	@Override
	protected User findByUniqueField(Object value) throws ServiceException {
		String email = value.toString();
		User user = userService.findByEmail(email);
		return user;
	}

	@Override
	protected String fieldName() {
		return "E-mail";
	}
}