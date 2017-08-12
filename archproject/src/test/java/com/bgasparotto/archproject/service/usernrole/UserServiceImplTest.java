package com.bgasparotto.archproject.service.usernrole;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.RolesGroup;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.AbstractServiceTest;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Unit service tests for {@link UserServiceImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplTest
		extends AbstractServiceTest<User, UserServiceImpl, UserDao> {
	private UserService userService;
	private UserDao daoMock;

	/**
	 * Constructor.
	 */
	public UserServiceImplTest() {
		super(UserServiceImpl.class, UserDao.class);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		userService = (UserService) getService();
		daoMock = getDaoMock();
	}

	@Override
	protected User getExpectedEntity() {
		Login login = new Login("someuser", "someuser@gmail.com");
		Password password = new Password("somepassword");
		Authentication authentication = new Authentication(login, password);
		Credential credential = new Credential(authentication, new RolesGroup());
		String verificationCode = UUID.randomUUID().toString();
		Registration registration = new Registration(LocalDateTime.now(),
				verificationCode);
		User user = new User(1L, credential, registration);
		
		return user;
	}

	@Override
	protected int getExpectedListSize() {
		return 8;
	}
	
	@Test
	public void shouldFindExistingUsername() throws Exception {
		String username = "someuser";
		Mockito.when(daoMock.findByUsername(username))
				.thenReturn(getExpectedEntity());

		User user = userService.findByUsername(username);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenUsernameDoesntExist() throws Exception {
		String username = "nonexistent";
		Mockito.when(daoMock.findByUsername(username)).thenReturn(null);

		User user = userService.findByUsername(username);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesNullUsername() throws Exception {
		Mockito.when(daoMock.findByUsername(null)).thenReturn(null);
		
		User user = userService.findByUsername(null);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesEmptyUsername() throws Exception {
		Mockito.when(daoMock.findByUsername("")).thenReturn(null);
		
		User user = userService.findByUsername("");
		Assert.assertNull(user);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnUsernamePersistenceError()
			throws Exception {
		
		Mockito.when(daoMock.findByUsername(Mockito.anyString()))
				.thenThrow(new GeneralPersistenceException());

		userService.findByUsername("anything");
	}
	
	@Test
	public void shouldFindExistingEmail() throws Exception {
		String email = "someuser@gmail.com";
		Mockito.when(daoMock.findByEmail(email))
				.thenReturn(getExpectedEntity());

		User user = userService.findByEmail(email);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenEmailDoesntExist() throws Exception {
		String email = "nonexistent@gmail.com";
		Mockito.when(daoMock.findByEmail(email)).thenReturn(null);

		User user = userService.findByEmail(email);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesNullEmail() throws Exception {
		Mockito.when(daoMock.findByEmail(null)).thenReturn(null);
		
		User user = userService.findByEmail(null);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesEmptyEmail() throws Exception {
		Mockito.when(daoMock.findByEmail("")).thenReturn(null);
		
		User user = userService.findByEmail("");
		Assert.assertNull(user);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnEmailPersistenceError()
			throws Exception {

		Mockito.when(daoMock.findByEmail(Mockito.anyString()))
				.thenThrow(new GeneralPersistenceException());

		userService.findByEmail("anything");
	}
}