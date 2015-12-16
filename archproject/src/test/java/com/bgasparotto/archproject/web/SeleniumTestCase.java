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
public abstract class SeleniumTestCase {

	/**
	 * The separator between the protocol and the target URL of the tests.
	 */
	private static final String PROTOCOL_URL_SEPARATOR = "://";

	protected WebDriver driver;

	/**
	 * Provides the target test {@code URL}.
	 * 
	 * @return The target test {@code URL}
	 */
	protected abstract String targetUrl();

	/**
	 * <p>
	 * The protocol to be used on tests. It will be appended at the beginning of
	 * the {@code String} returned by {@link SeleniumTestCase#targetUrl()
	 * targetUrl()}, separated by a colon and double slashes.
	 * </p>
	 * <p>
	 * The default protocol is {@link Protocol#HTTP HTTP}, if this behavior need
	 * to be changed, this method must be overridden.
	 * </p>
	 * 
	 * @return The protocol to be used on tests
	 */
	protected Protocols protocol() {
		return Protocols.HTTP;
	}

	/**
	 * Initializes the {@code WebDriver} and gets the URL returned by the
	 * implementation of {@link SeleniumTestCase#targetUrl() targetUrl()}.
	 * 
	 * @throws Exception
	 *             If any raised by Selenium
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("setUp()");
		driver = new HtmlUnitDriver();

		String url = protocol() + PROTOCOL_URL_SEPARATOR + targetUrl();
		driver.get(url);
	}

	/**
	 * Quits the {@code WebDriver}.
	 * 
	 * @throws Exception
	 *             If any raised by Selenium
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown()");
		driver.quit();
	}

	/**
	 * Test protocols to be used by Selenium tests.
	 * 
	 * @author Bruno Gasparotto
	 *
	 */
	public enum Protocols {
		HTTP, HTTPS;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
}