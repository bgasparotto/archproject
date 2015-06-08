package com.bgasparotto.archproject.web.security.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.UserService;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Abstract validator to avoid duplicity of User's unique fields, such as
 * {@code username} and {@code e-mail}.
 * 
 * @author Bruno Gasparotto
 *
 */
public abstract class UserCredentialsValidator implements Validator {

	@Inject
	protected UserService userService;

	@Inject
	private Logger logger;

	/**
	 * Find an user by some of its unique fields.
	 * 
	 * @param value
	 *            The value of the unique field to check for the user
	 * @return The {@code User} instance if the unique field is already in use,
	 *         {@code null} otherwise
	 * @throws ServiceException
	 *             if the operation fails
	 */
	protected abstract User findByUniqueField(Object value)
			throws ServiceException;

	/**
	 * Gets name of the unique field being checked.
	 * 
	 * @return The unique field name
	 */
	protected abstract String fieldName();

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		User user = null;

		try {
			user = findByUniqueField(value);
		} catch (ServiceException e) {
			logger.error("Failed to check if " + fieldName()
					+ " is already in use.", e);
			return;
		}

		if (user != null) {
			String summary = fieldName() + " validation failed.";
			String detail = fieldName() + " already in use";
			FacesMessage facesMessage = new FacesMessage(summary, detail);
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(facesMessage);
		}
	}
}