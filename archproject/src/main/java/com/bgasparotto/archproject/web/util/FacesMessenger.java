package com.bgasparotto.archproject.web.util;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Faces messages creator.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class FacesMessenger {

	/**
	 * Adds a {@code INFO} faces message to the current context.
	 * 
	 * @param summary
	 *            Localised summary message text
	 * @param detail
	 *            Localised detail message text
	 */
	public void info(String summary, String detail) {
		add(summary, detail, null);
	}

	/**
	 * Adds a {@code ERROR} faces message to the current context.
	 * 
	 * @param summary
	 *            Localised summary message text
	 * @param detail
	 *            Localised detail message text
	 */
	public void error(String summary, String detail) {
		add(summary, detail, FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * Adds a faces message with the specified not null severity level to the
	 * current context.
	 * 
	 * @param summary
	 *            Localised summary message text
	 * @param detail
	 *            Localised detail message text
	 * @param severity
	 *            The severity level
	 */
	public void add(String summary, String detail, Severity severity) {
		FacesMessage facesMessage = new FacesMessage(summary, detail);
		if (severity != null) {
			facesMessage.setSeverity(severity);
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, facesMessage);
	}
}