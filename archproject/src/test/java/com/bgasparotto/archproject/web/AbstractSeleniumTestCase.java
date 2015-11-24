package com.bgasparotto.archproject.web;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * <p>
 * Abstract web test case for front-end tests with Selenium.
 * </p>
 * <p>
 * This implementation uses a {@link HtmlUnitDriver} as {@link WebDriver}, in
 * order to make these tests portable and independent from external resources.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 */
public abstract class AbstractSeleniumTestCase {
	protected WebDriver driver;

	/**
	 * Provides the target test {@code URL}
	 * 
	 * @return The target test {@code URL}
	 */
	protected abstract String targetUrl();

	/**
	 * Initializes the {@code WebDriver} and gets the url returned by the
	 * implementation of {@link AbstractSeleniumTestCase#targetUrl()
	 * targetUrl()}.
	 * 
	 * @throws Exception
	 *             If any raised by Selenium
	 */
	@Before
	public void setUp() throws Exception {
		driver = new HtmlUnitDriver();

		String targetUrl = targetUrl();
		driver.get(targetUrl);
	}

	/**
	 * Quits the {@code WebDriver}.
	 * 
	 * @throws Exception
	 *             If any raised by Selenium
	 */
	@After
	protected void tearDown() throws Exception {
		driver.quit();
	}
}