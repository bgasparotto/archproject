package com.bgasparotto.archproject.service.usernrole;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.ServiceTestCase;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Unit service tests for
 * {@link UserServiceImpl#changePassword(String, String, String)}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplChangePasswordTest
		extends ServiceTestCase<User, UserServiceImpl, UserDao> {

	/**
	 * Constructor.
	 */
	public UserServiceImplChangePasswordTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Test
	public void shouldChangeThePassword() throws Exception {
		String username = "someuser";
		User user = TestingUserFactory.newUserInstance();
		user.getLogin().setUsername(username);
		String newPassword = "anewpassword";
		
		Mockito.when(daoMock.findByUsername(username)).thenReturn(user);
		Mockito.when(daoMock.mergeFlush(user)).thenReturn(user);
		
		User returnedUser = service.changePassword(username, "somepassword",
				newPassword);
		
		Assert.assertNotNull(returnedUser);
		Password returnedPassword = returnedUser.getPassword();
		Assert.assertTrue(returnedPassword.checkWithBCrypt(newPassword));
	}
	
	@Test
	public void shouldNotChangeOnInvalidPassword() throws Exception {
		String username = "someuser";
		User user = TestingUserFactory.newUserInstance();
		user.getLogin().setUsername(username);

		Mockito.when(daoMock.findByUsername(username)).thenReturn(user);
		Mockito.when(daoMock.mergeFlush(user)).thenReturn(user);

		User returnedUser = service.changePassword(username,
				"someinvalidpassword",
				"anewpassword");

		Assert.assertNull(returnedUser);
	}
	
	@Test
	public void shouldNotChangeOnInvalidUser() throws Exception {
		String username = "someuser";
		User user = TestingUserFactory.newUserInstance();
		user.getLogin().setUsername(username);
		String oldPassword = "somepassword";
		String newPassword = "anewpassword";
		
		Mockito.when(daoMock.findByUsername(username)).thenReturn(null);
		Mockito.when(daoMock.mergeFlush(user)).thenReturn(user);
		
		User returnedUser = service.changePassword(username, oldPassword,
				newPassword);
		
		Assert.assertNull(returnedUser);
	}
	
	@Test(expected=ServiceException.class)
	public void shouldThrowExceptionOnPersistenceError() throws Exception {
		String username = "someuser";
		User user = TestingUserFactory.newUserInstance();
		user.getLogin().setUsername(username);
		String oldPassword = "somepassword";
		String newPassword = "anewpassword";
		
		Mockito.when(daoMock.findByUsername(username)).thenReturn(user);
		GeneralPersistenceException e = new GeneralPersistenceException();
		Mockito.when(daoMock.mergeFlush(user)).thenThrow(e);
		
		service.changePassword(username, oldPassword, newPassword);
	}
}