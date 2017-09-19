package com.bgasparotto.archproject.web.arquillian.selenium.index;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.bgasparotto.archproject.web.arquillian.selenium.SeleniumTestCase;

public class IndexTest extends SeleniumTestCase {

	@Override
	protected String targetUrl() {
		return "localhost:8080/archproject";
	}

	@Test
	public void shouldFindLoginButton() {
		WebElement signInLink = driver.findElement(By.name("sign-in"));
		Assert.assertNotNull(signInLink);
	}

	@Test
	public void shouldFindRegisterButton() {
		WebElement signUpLink = driver.findElement(By.name("sign-up"));
		Assert.assertNotNull(signUpLink);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldNotFindLogoutButton() {
		driver.findElement(By.name("logout"));
	}
}