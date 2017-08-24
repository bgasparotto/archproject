package com.bgasparotto.archproject.web;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Navigation {@code managed bean} to be used to replace the page of the main's
 * content.
 * 
 * @author Bruno Gasparotto
 *
 */
@Named
@ViewScoped
public class NavigationMb implements Serializable {
	private static final long serialVersionUID = -4503113974610704908L;
	private String page;

	/**
	 * Initialises this managed bean with the welcome page.
	 * 
	 * Constructor.
	 *
	 */
	public NavigationMb() {
		this.page = "welcome.xhtml";
	}

	/**
	 * Constructor.
	 *
	 * @param page
	 *            The initial page to display
	 */
	public NavigationMb(String page) {
		this.page = page;
	}

	/**
	 * Gets the NavigationMb's {@code page}.
	 *
	 * @return The NavigationMb's {@code page}
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Sets the NavigationMb's {@code page}.
	 *
	 * @param page
	 *            The NavigationMb's {@code page} to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * Navigate to the destination page changing the container's content.
	 * 
	 * @param page
	 *            Page to navigate to
	 */
	public void nav(String page) {
		this.page = page;
	}
}