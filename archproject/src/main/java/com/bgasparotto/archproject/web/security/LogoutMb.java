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