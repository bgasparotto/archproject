package com.bgasparotto.archproject.web.security;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.InvalidVerificationCodeException;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.TestingUserFactory;
import com.bgasparotto.archproject.service.usernrole.UserService;
import com.bgasparotto.archproject.service.usernrole.UserServiceImpl;
import com.bgasparotto.archproject.web.security.ValidationMb.Status;

/**
 * Unit tests for {@link ValidationMb}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class ValidationMbTest {
	private ValidationMb mb;

	@Before
	public void setUp() throws Exception {
		UserService mockService = mock(UserServiceImpl.class);
		mb = new ValidationMb(mockService);
	}

	@Test
	public void shouldGetNotNullDependencies() throws Exception {
		User user = mb.getUser();
		Assert.assertNotNull(user);

		Status status = mb.getStatus();
		Assert.assertNotNull(status);

		UserService userService = mb.getUserService();
		Assert.assertNotNull(userService);
	}

	@Test
	public void shouldInitialiseOnInProgressState() throws Exception {
		Status status = mb.getStatus();
		Assert.assertEquals(Status.IN_PROGRESS, status);
	}

	@Test
	public void shouldValidateAnUser() throws Exception {
		User user = TestingUserFactory.newUserInstance();

		Login login = user.getLogin();
		String username = login.getUsername();

		Registration registration = user.getRegistration();
		String verificationCode = registration.getVerificationCode();

		UserService userService = mb.getUserService();
		when(userService.validate(username, verificationCode)).thenReturn(user);

		String navigationCase = mb.validate(user);
		Assert.assertNull(navigationCase);
		Status status = mb.getStatus();
		Assert.assertEquals(Status.SUCCESS, status);
	}

	@Test
	public void shouldFailOnInvalidVerificationCode() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		UserService userService = mb.getUserService();
		when(userService.validate(anyString(), anyString()))
				.thenThrow(new InvalidVerificationCodeException());

		String navigationCase = mb.validate(user);
		Assert.assertNull(navigationCase);
		Status status = mb.getStatus();
		Assert.assertEquals(Status.INVALID_VERIFICATION_CODE, status);
	}

	@Test
	public void shouldFailOnServiceError() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		UserService userService = mb.getUserService();
		when(userService.validate(anyString(), anyString()))
				.thenThrow(new ServiceException());

		String navigationCase = mb.validate(user);
		Assert.assertEquals("error", navigationCase);
		Status status = mb.getStatus();
		Assert.assertEquals(Status.ERROR, status);
	}
}