package com.bgasparotto.archproject.web.security;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bgasparotto.archproject.web.AbstractSeleniumTestCase;

public class LoginTest extends AbstractSeleniumTestCase {

	@Override
	protected String targetUrl() {
		return "www.google.com";
	}

	@Test
	public void test() {
		String search = "bgasparotto";

		WebElement q = driver.findElement(By.name("q"));
		q.sendKeys(search);
		q.submit();

		String pageTitle = driver.getTitle();
		Assert.assertTrue(pageTitle.contains(search));
	}
}