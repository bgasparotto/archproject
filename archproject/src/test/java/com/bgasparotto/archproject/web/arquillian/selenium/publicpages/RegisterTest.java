package com.bgasparotto.archproject.web.arquillian.selenium.publicpages;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bgasparotto.archproject.web.arquillian.selenium.SeleniumTestCase;
import com.bgasparotto.archproject.web.arquillian.selenium.SeleniumTools;

public class RegisterTest extends SeleniumTestCase {

	@Override
	protected String targetUrl() {
		return "localhost:8080/archproject";
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		WebElement signUpLink = driver.findElement(By.name("sign-up"));
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
	
	/* Mostly auto-generated by Selenium IDE on Mozilla Firefox 44. */
	@Test
	@UsingDataSet("DbUnit/dbunit-test-db.xml")
	public void shouldRegisterAndLogIn() throws Exception {
		driver.findElement(By.id("username")).sendKeys("newuser");
		driver.findElement(By.id("email")).sendKeys("newuser@gmail.com");
		driver.findElement(By.id("password")).sendKeys("somepassword");
		driver.findElement(By.id("confirmPassword")).sendKeys("somepassword");
		driver.findElement(By.id("registerButton")).click();
		SeleniumTools.waitForElementPresent(driver, By.linkText("here"));
		driver.findElement(By.linkText("here")).click();
		driver.findElement(By.linkText("Sign in")).click();
		driver.findElement(By.id("j_username")).sendKeys("newuser");
		driver.findElement(By.id("j_password")).sendKeys("somepassword");
		driver.findElement(By.id("loginButton")).click();
		driver.findElement(By.id("logoutButton")).click();
	}
}