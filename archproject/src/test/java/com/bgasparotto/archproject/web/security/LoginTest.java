package com.bgasparotto.archproject.web.security;

import org.junit.Assert;
import org.junit.Test;

import com.bgasparotto.archproject.web.ArquillianTestCase;

public class LoginTest extends ArquillianTestCase {

	@Test
	public void shouldRun() throws Exception {
		System.out.println("LoginTest!");
		Assert.assertTrue(true);
	}

	@Override
	protected String targetUrl() {
		return "www.google.com";
	}
}