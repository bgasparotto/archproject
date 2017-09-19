package com.bgasparotto.archproject.web.arquillian.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A collection of <em>Selenium</em> tools written as methods, to help unit
 * tests implementation with a more friendly syntax.
 * 
 * @author Bruno Gasparotto
 *
 */
public class SeleniumTools {
	
	/**
	 * Default time in seconds which is going to be used by Selenium to wait for
	 * the presence of an element, if it's not immediately present when first
	 * searched.
	 */
	public static final long DEFAULT_WAIT = 3;
	
	/* Prevents instantiation. */
	private SeleniumTools() {
	}
	
	/**
	 * Wait until an element becomes present for as long as defined by
	 * {@link SeleniumTools#DEFAULT_WAIT DEFAULT_WAIT}.
	 * 
	 * @param driver
	 *            The WebDriver instance to pass to the expected conditions
	 * @param by
	 *            The locating mechanism
	 */
	public static void waitForElementPresent(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
}