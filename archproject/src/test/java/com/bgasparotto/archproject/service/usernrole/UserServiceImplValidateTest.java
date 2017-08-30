package com.bgasparotto.archproject.service.usernrole;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.service.ServiceTestCase;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Unit service tests for
 * {@link UserServiceImpl#changePassword(String, String, String)}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplValidateTest
		extends ServiceTestCase<User, UserServiceImpl, UserDao> {

	/**
	 * Constructor.
	 */
	public UserServiceImplValidateTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Test
	public void shouldValidate() throws Exception {
		String username = "someuser";
		User user = TestingUserFactory.newUserInstance();
		user.getLogin().setUsername(username);
		String verificationCode = user.getRegistration().getVerificationCode();
		
		Mockito.when(daoMock.findByUsername(username)).thenReturn(user);
		
		User returnedUser = service.validate(username, verificationCode);
		Assert.assertNotNull(returnedUser);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnInvalidUsername() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		String username = user.getLogin().getUsername();
		String verificationCode = user.getRegistration().getVerificationCode();
		
		Mockito.when(daoMock.findByUsername(Mockito.any())).thenReturn(null);
		service.validate(username, verificationCode);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnInvalidVerification() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		String username = user.getLogin().getUsername();
		String verificationCode = "invalidcode";
		
		Mockito.when(daoMock.findByUsername(username)).thenReturn(user);
		service.validate(username, verificationCode);
	}
}