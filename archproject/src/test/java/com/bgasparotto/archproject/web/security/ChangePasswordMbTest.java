package com.bgasparotto.archproject.web.security;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.TestingUserFactory;
import com.bgasparotto.archproject.service.usernrole.UserService;
import com.bgasparotto.archproject.service.usernrole.UserServiceImpl;
import com.bgasparotto.archproject.web.util.FacesMessenger;
import com.bgasparotto.archproject.web.util.FacesUser;

public class ChangePasswordMbTest {
	private ChangePasswordMb mb;

	@Before
	public void setUp() throws Exception {
		UserService service = mock(UserServiceImpl.class);
		FacesMessenger messenger = mock(FacesMessenger.class);
		FacesUser facesUser = mock(FacesUser.class);

		mb = new ChangePasswordMb(service, messenger, facesUser);
	}

	@Test
	public void shouldGetNotNullDependencies() throws Exception {
		Password oldPassword = mb.getOldPassword();
		Assert.assertNotNull(oldPassword);

		Password newPassword = mb.getNewPassword();
		Assert.assertNotNull(newPassword);

		UserService userService = mb.getUserService();
		Assert.assertNotNull(userService);

		FacesMessenger messenger = mb.getMessenger();
		Assert.assertNotNull(messenger);

		FacesUser facesUser = mb.getFacesUser();
		Assert.assertNotNull(facesUser);
	}

	@Test
	public void shouldChangePasswordAndDisplayInfoMessage() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		Login login = user.getLogin();
		String username = login.getUsername();

		FacesUser facesUser = mb.getFacesUser();
		when(facesUser.getRemoteUser()).thenReturn(username);

		UserService userService = mb.getUserService();
		when(userService.changePassword(any(), any(), any())).thenReturn(user);

		String navigation = mb.changePassword();
		Assert.assertNull(navigation);
		verify(facesUser).getRemoteUser();
		FacesMessenger messenger = mb.getMessenger();
		verify(messenger).info(any(), any());
	}

	@Test
	public void shouldNotChangePasswordAndDisplayErrorMessage()
			throws Exception {
		FacesUser facesUser = mb.getFacesUser();
		when(facesUser.getRemoteUser()).thenReturn("anyuser");

		UserService userService = mb.getUserService();
		when(userService.changePassword(any(), any(), any())).thenReturn(null);

		String navigation = mb.changePassword();
		Assert.assertNull(navigation);
		verify(facesUser).getRemoteUser();
		FacesMessenger messenger = mb.getMessenger();
		verify(messenger).error(any(), any());
	}

	@Test
	public void shouldFailToChangePasswordAndReturnError()
			throws Exception {
		FacesUser facesUser = mb.getFacesUser();
		when(facesUser.getRemoteUser()).thenReturn("anyuser");

		UserService userService = mb.getUserService();
		when(userService.changePassword(any(), any(), any()))
				.thenThrow(new ServiceException());

		String navigation = mb.changePassword();
		Assert.assertEquals("error", navigation);
		verify(facesUser).getRemoteUser();
	}
}