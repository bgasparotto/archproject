package com.bgasparotto.archproject.web.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.UserService;

/**
 * Managed bean for the {@code Change Password} menu item.
 * 
 * @author Bruno Gasparotto
 *
 */
@Named
@RequestScoped
public class ChangePasswordMb {
	private Password oldPassword;
	private Password newPassword;

	@Inject
	private UserService userService;

	@Inject
	private Logger logger;

	/**
	 * Constructor.
	 *
	 */
	public ChangePasswordMb() {
		oldPassword = new Password("");
		newPassword = new Password("");
	}

	/**
	 * Gets the ChangePasswordMb's {@code oldPassword}.
	 *
	 * @return The ChangePasswordMb's {@code oldPassword}
	 */
	public Password getOldPassword() {
		return oldPassword;
	}

	/**
	 * Gets the ChangePasswordMb's {@code newPassword}.
	 *
	 * @return The ChangePasswordMb's {@code newPassword}
	 */
	public Password getNewPassword() {
		return newPassword;
	}

	/**
	 * Changes the user's password, displaying success or error messages on the
	 * {@code FacesConext}.
	 */
	public void changePassword() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		String remoteUser = externalContext.getRemoteUser();
		String oldPasswordValue = oldPassword.getValue();
		String newPasswordValue = newPassword.getValue();

		User user = null;
		try {
			user = userService.changePassword(remoteUser, oldPasswordValue,
					newPasswordValue);
		} catch (ServiceException e) {
			logger.error("Failed to change user's password", e);
		}

		if (user == null) {
			String summary = "Incorrect password";
			String detail = "The current password is incorrect.";
			FacesMessage facesMessage = new FacesMessage(summary, detail);
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, facesMessage);
			return;
		}

		String summary = "Success";
		String detail = "The password has been changed successfully.";
		FacesMessage facesMessage = new FacesMessage(summary, detail);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, facesMessage);
	}
}