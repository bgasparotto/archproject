package com.bgasparotto.archproject.web.security;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.service.usernrole.UserService;
import com.bgasparotto.archproject.service.usernrole.UserServiceImpl;

public class ChangePasswordMbTest {
	private ChangePasswordMb mb;

	@Before
	public void setUp() throws Exception {
		UserService mockService = mock(UserServiceImpl.class);
		Logger logger = LoggerFactory.getLogger(ChangePasswordMb.class);

		mb = new ChangePasswordMb(mockService, logger);
	}

	@Test
	public void shouldGetNotNullDependencies() throws Exception {
		Password oldPassword = mb.getOldPassword();
		Assert.assertNotNull(oldPassword);

		Password newPassword = mb.getNewPassword();
		Assert.assertNotNull(newPassword);

		UserService userService = mb.getUserService();
		Assert.assertNotNull(userService);

		Logger logger = mb.getLogger();
		Assert.assertNotNull(logger);
	}
}