package com.bgasparotto.archproject.web.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.UserService;
import com.bgasparotto.archproject.web.util.FacesMessenger;
import com.bgasparotto.archproject.web.util.FacesUser;

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

	private UserService userService;
	private FacesMessenger messenger;
	private FacesUser facesUser;

	/**
	 * Constructor.
	 *
	 * @param userService
	 *            {@code UserService}'s implementation to be used by this
	 *            managed bean
	 * @param messenger
	 *            The {@code FacesMessenger} to be used by this managed bean
	 * @param facesUser
	 *            The {@code ChangePasswordMb} to be used by this managed bean
	 */
	@Inject
	public ChangePasswordMb(UserService userService, FacesMessenger messenger,
			FacesUser facesUser) {
		this.oldPassword = new Password("");
		this.newPassword = new Password("");

		this.userService = userService;
		this.messenger = messenger;
		this.facesUser = facesUser;
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
	 * Gets the ChangePasswordMb's {@code userService}.
	 *
	 * @return The ChangePasswordMb's {@code userService}
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Gets the ChangePasswordMb's {@code messenger}.
	 *
	 * @return The ChangePasswordMb's {@code messenger}
	 */
	public FacesMessenger getMessenger() {
		return messenger;
	}

	/**
	 * Gets the ChangePasswordMb's {@code facesUser}.
	 *
	 * @return The ChangePasswordMb's {@code facesUser}
	 */
	public FacesUser getFacesUser() {
		return facesUser;
	}

	/**
	 * Changes the user's password, displaying success or error messages on the
	 * {@code FacesConext}.
	 * 
	 * @return The implicit navigation outcome for the next page to be rendered,
	 *         or {@code null} if it shouldn't change pages
	 */
	public String changePassword() {
		String remoteUser = facesUser.getRemoteUser();
		String oldPasswordValue = oldPassword.getValue();
		String newPasswordValue = newPassword.getValue();

		User user = null;
		try {
			user = userService.changePassword(remoteUser, oldPasswordValue,
					newPasswordValue);
		} catch (ServiceException e) {
			return "error";
		}

		if (user == null) {
			messenger.error("Incorrect password",
					"The current password is incorrect.");
			return null;
		}

		messenger.info("Success",
				"The password has been changed successfully.");
		return null;
	}
}