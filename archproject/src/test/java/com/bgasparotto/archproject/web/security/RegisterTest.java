package com.bgasparotto.archproject.web.security;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bgasparotto.archproject.web.arquillian.selenium.SeleniumTestCase;

public class RegisterTest extends SeleniumTestCase {

	@Override
	protected String targetUrl() {
		return "localhost:8080/archproject";
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		By signUp = By.name("sign-up");
		WebElement signUpLink = driver.findElement(signUp);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(signUp));
		signUpLink.click();
	}
	
	@Test
	public void shouldFindSignUpElements() {
		WebElement usernameInput = driver.findElement(By.name("username"));
		Assert.assertNotNull(usernameInput);
		
		WebElement emailInput = driver.findElement(By.name("email"));
		Assert.assertNotNull(emailInput);
		
		WebElement passwordInput = driver.findElement(By.name("password"));
		Assert.assertNotNull(passwordInput);
		
		WebElement confirmPasswordInput = driver
				.findElement(By.name("confirmPassword"));
		Assert.assertNotNull(confirmPasswordInput);
		
		WebElement registerButton = driver
				.findElement(By.name("registerButton"));
		Assert.assertNotNull(registerButton);
	}
}