package com.bgasparotto.archproject.service.usernrole;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.ServiceTestCase;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Unit service tests for {@link UserServiceImpl#findByUsername(String)}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplFindByUsernameTest
		extends ServiceTestCase<User, UserServiceImpl, UserDao> {

	public UserServiceImplFindByUsernameTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Test
	public void shouldFindExistingUsername() throws Exception {
		String username = "someuser";
		User expectedUser = TestingUserFactory.newUserInstance();
		Mockito.when(daoMock.findByUsername(username))
				.thenReturn(expectedUser);

		User user = service.findByUsername(username);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenUsernameDoesntExist() throws Exception {
		String username = "nonexistent";
		Mockito.when(daoMock.findByUsername(username)).thenReturn(null);

		User user = service.findByUsername(username);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesNullUsername() throws Exception {
		Mockito.when(daoMock.findByUsername(null)).thenReturn(null);
		
		User user = service.findByUsername(null);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesEmptyUsername() throws Exception {
		Mockito.when(daoMock.findByUsername("")).thenReturn(null);
		
		User user = service.findByUsername("");
		Assert.assertNull(user);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnUsernamePersistenceError()
			throws Exception {
		
		Mockito.when(daoMock.findByUsername(Mockito.anyString()))
				.thenThrow(new GeneralPersistenceException());

		service.findByUsername("anything");
	}
}