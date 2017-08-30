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
 * Unit service tests for {@link UserServiceImpl#findByEmail(String)}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplFindByEmailTest
		extends ServiceTestCase<User, UserServiceImpl, UserDao> {

	public UserServiceImplFindByEmailTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Test
	public void shouldFindExistingEmail() throws Exception {
		String email = "someuser@gmail.com";
		User expectedUser = TestingUserFactory.newUserInstance();
		Mockito.when(daoMock.findByEmail(email))
				.thenReturn(expectedUser);

		User user = service.findByEmail(email);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenEmailDoesntExist() throws Exception {
		String email = "nonexistent@gmail.com";
		Mockito.when(daoMock.findByEmail(email)).thenReturn(null);

		User user = service.findByEmail(email);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesNullEmail() throws Exception {
		Mockito.when(daoMock.findByEmail(null)).thenReturn(null);
		
		User user = service.findByEmail(null);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesEmptyEmail() throws Exception {
		Mockito.when(daoMock.findByEmail("")).thenReturn(null);
		
		User user = service.findByEmail("");
		Assert.assertNull(user);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnEmailPersistenceError()
			throws Exception {

		Mockito.when(daoMock.findByEmail(Mockito.anyString()))
				.thenThrow(new GeneralPersistenceException());

		service.findByEmail("anything");
	}
}