package com.bgasparotto.archproject.service.usernrole;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.ServiceTestCase;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.mail.MailService;

/**
 * Unit service tests for {@link UserServiceImpl#register(Authentication)}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplRegisterTest
		extends ServiceTestCase<User, UserServiceImpl, UserDao> {

	public UserServiceImplRegisterTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		RoleService roleService = Mockito.mock(RoleService.class);
		Role role = new Role(1L, "mockrole");
		Mockito.when(roleService.findDefault()).thenReturn(role);
		service.setRoleService(roleService);

		MailService mailService = Mockito.mock(MailService.class);
		Mockito.doNothing().when(mailService)
				.sendValidationEmail(Mockito.any());
		service.setMailService(mailService);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNPEWhenPassingNull() throws Exception {
		service.register(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNPEWhenPassingNullLogin() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		authentication.setLogin(null);

		service.register(authentication);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNPEWhenPassingNullPassword() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		authentication.setPassword(null);

		service.register(authentication);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowISEWhenPassingNullUsername() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		Login login = authentication.getLogin();
		login.setUsername(null);

		service.register(authentication);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowISEWhenPassingEmptyUsername() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		Login login = authentication.getLogin();
		login.setUsername("");

		service.register(authentication);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowISEWhenPassingNullEmail() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		Login login = authentication.getLogin();
		login.setEmail(null);

		service.register(authentication);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowISEWhenPassingEmptyEmail() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		Login login = authentication.getLogin();
		login.setEmail("");

		service.register(authentication);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNPEWhenPassingNullPasswordObject() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		authentication.setPassword(null);

		service.register(authentication);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowISEWhenPassingNullPasswordValue() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		Password password = authentication.getPassword();
		password.setValue(null);

		service.register(authentication);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowISEWhenPassingEmptyPasswordValue() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		Password password = authentication.getPassword();
		password.setValue("");

		service.register(authentication);
	}

	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnPersistenceError() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();

		Mockito.when(daoMock.persist(Mockito.any()))
				.thenThrow(new GeneralPersistenceException());

		service.register(authentication);
	}

	@Test
	public void shouldRegisterAndGetAnIdBack() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		
		Mockito.when(daoMock.persist(Mockito.any())).thenReturn(1L);

		Long registedId = service.register(authentication);
		Assert.assertNotNull(registedId);
		Assert.assertEquals(1L, registedId.longValue());
	}
	
	@Test
	public void shouldRegisterEvenIfMailingError() throws Exception {
		Authentication authentication = TestingUserFactory
				.newAuthenticationInstance();
		
		// Overrides the default behaviour of the mocked mail service.
		MailService mailService = Mockito.mock(MailService.class);
		Mockito.doThrow(new ServiceException()).when(mailService)
				.sendValidationEmail(Mockito.any());
		service.setMailService(mailService);
		
		Mockito.when(daoMock.persist(Mockito.any())).thenReturn(1L);

		Long registedId = service.register(authentication);
		Assert.assertNotNull(registedId);
		Assert.assertEquals(1L, registedId.longValue());
	}
}