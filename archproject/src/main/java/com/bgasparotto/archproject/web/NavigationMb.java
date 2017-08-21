package com.bgasparotto.archproject.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class NavigationMb {
	private String page;

	/**
	 * Constructor.
	 *
	 */
	public NavigationMb() {
		this("welcome.xhtml");
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

	public void nav(String page) {
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
}