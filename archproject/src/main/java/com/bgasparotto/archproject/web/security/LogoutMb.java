package com.bgasparotto.archproject.web.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Logout managed bean.
 * </p>
 * <p>
 * Provides implementation for invalidating a {@code HTTP} session.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 */
@Named
@RequestScoped
public class LogoutMb {
	
	/**
	 * Check if the user is/was logged out based on the external context.
	 * 
	 * @return {@code true} if the user is logged out, {@code false} otherwise
	 */
	public boolean isLoggedOut() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String remoteUser = externalContext.getRemoteUser();

		boolean loggedOut = (remoteUser == null);
		return loggedOut;
	}

	/**
	 * Logs out an user, by invalidating its session.
	 * 
	 * @return The relative path of the {@code index.xhtml}
	 */
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.invalidate();

		/* URL to be processed for redirection to index page. */
		return "/index.xhtml?faces-redirect=true";
	}
}