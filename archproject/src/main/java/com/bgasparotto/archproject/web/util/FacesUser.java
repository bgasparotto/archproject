package com.bgasparotto.archproject.web.util;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Class containing methods to work with the container managed user associated
 * to the current context.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class FacesUser {

	/**
	 * Attempts to return the login name of the user making the current request.
	 * 
	 * @return the login name of the user making the current request if any,
	 *         {@code null} otherwise
	 */
	public String getRemoteUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		String remoteUser = externalContext.getRemoteUser();
		return remoteUser;
	}
}