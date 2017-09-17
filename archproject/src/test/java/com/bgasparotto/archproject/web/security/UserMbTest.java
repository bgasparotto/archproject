package com.bgasparotto.archproject.web.security;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.UserService;
import com.bgasparotto.archproject.service.usernrole.UserServiceImpl;

/**
 * Unit tests for {@link UserMb}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserMbTest {
	private UserMb mb;

	@Before
	public void setUp() throws Exception {
		UserService mock = mock(UserServiceImpl.class);
		mb = new UserMb(mock);
	}
	
	@Test
	public void shouldGetNotNullDependencies() throws Exception {
		Authentication authentication = mb.getAuthentication();
		Assert.assertNotNull(authentication);
		
		UserService userService = mb.getUserService();
		Assert.assertNotNull(userService);
	}

	@Test
	public void shouldReturnRegisteredNavigation() throws Exception {
		UserService userService = mb.getUserService();
		Authentication authentication = mb.getAuthentication();
		when(userService.register(authentication)).thenReturn(anyLong());
		
		String navigationCase = mb.register(authentication);
		Assert.assertEquals("registered", navigationCase);
	}
	
	@Test
	public void shouldReturnErrorNavigation() throws Exception {
		UserService userService = mb.getUserService();
		Authentication authentication = mb.getAuthentication();
		when(userService.register(authentication))
				.thenThrow(new ServiceException());

		String navigationCase = mb.register(authentication);
		Assert.assertEquals("error", navigationCase);
	}
}