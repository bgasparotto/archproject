package com.bgasparotto.archproject.web.security;

import org.junit.Assert;
import org.junit.Test;

import com.bgasparotto.archproject.web.ArquillianTestCase;

public class LoginTest extends ArquillianTestCase {

	@Override
	protected String targetUrl() {
		return "localhost:8080/archproject";
	}

	@Test
	public void shouldRun() throws Exception {
		System.out.println("LoginTest!");
		Assert.assertTrue(true);
	}
}