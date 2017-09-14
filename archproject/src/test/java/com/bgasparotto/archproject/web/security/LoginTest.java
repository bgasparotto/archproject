package com.bgasparotto.archproject.web.security;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bgasparotto.archproject.web.ArquillianTestCase;

public class LoginTest extends ArquillianTestCase {

	@Override
	protected String targetUrl() {
		return "localhost:8080/archproject";
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		WebElement signInLink = driver.findElement(By.name("sign-in"));
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("sign-in")));
		signInLink.click();
	}
	
	@Test
	@UsingDataSet("DbUnit/dbunit-test-db.xml")
	public void shouldFindLogInElements() {
		WebElement usernameInput = driver.findElement(By.id("j_username"));
		Assert.assertNotNull(usernameInput);
		
		WebElement passwordInput = driver.findElement(By.id("j_password"));
		Assert.assertNotNull(passwordInput);
		
		WebElement loginButton = driver.findElement(By.name("loginButton"));
		Assert.assertNotNull(loginButton);
	}
}