package com.bgasparotto.archproject.service.usernrole;

import org.junit.Assert;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.ServiceTestCase;
import com.bgasparotto.archproject.service.mail.MailService;

/**
 * Unit service tests for {@link UserServiceImpl#authenticate(String, String)}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplAuthenticateTest
		extends ServiceTestCase<User, UserServiceImpl, UserDao> {

	public UserServiceImplAuthenticateTest() {
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

	@Test
	public void shouldReturnNullWhenPassingNullParameters() throws Exception {
		User user = service.authenticate("someusername", null);
		Assert.assertNull(user);

		user = service.authenticate("somemail@gmail.com", null);
		Assert.assertNull(user);

		user = service.authenticate(null, "somepassword");
		Assert.assertNull(user);
	}

	@Test
	public void shouldReturnNullForUnexistentUsername() throws Exception {
		String username = "itdoesntexist";
		Mockito.when(daoMock.findByUsername(username)).thenReturn(null);

		User user = service.authenticate(username, "somepassword");
		Assert.assertNull(user);
		Mockito.verify(daoMock, Mockito.times(1)).findByUsername(username);
	}

	@Test
	public void shouldReturnNullForUnexistentEmail() throws Exception {
		String email = "itdoesntexist@gmail.com";
		Mockito.when(daoMock.findByEmail(email)).thenReturn(null);

		User user = service.authenticate("itdoesntexist@gmail.com", "somepw");
		Assert.assertNull(user);
		Mockito.verify(daoMock, Mockito.times(1)).findByEmail(email);
	}

	@Test
	public void shouldReturnNullOnExceptionThrown() throws Exception {
		Mockito.when(daoMock.findByUsername(Mockito.anyString()))
				.thenThrow(new GeneralPersistenceException());

		User user = service.authenticate("someuser", "somepw");
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullOnInvalidPassword() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		Credential credential = user.getCredential();
		Authentication authentication = credential.getAuthentication();
		Password password = authentication.getPassword();
		
		String salt = BCrypt.gensalt();
		String hashedPw = BCrypt.hashpw("somepassword", salt);
		password.setValue(hashedPw);
		
		Mockito.when(daoMock.findByUsername(Mockito.anyString()))
				.thenReturn(user);

		user = service.authenticate("someuser", "somepw");
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldSucceedValidationByUsername() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		Credential credential = user.getCredential();
		Authentication authentication = credential.getAuthentication();
		Password password = authentication.getPassword();
		
		String salt = BCrypt.gensalt();
		String passwordValue = "somepassword";
		String hashedPw = BCrypt.hashpw(passwordValue, salt);
		password.setValue(hashedPw);
		
		Mockito.when(daoMock.findByUsername(Mockito.anyString()))
				.thenReturn(user);

		user = service.authenticate("someuser", passwordValue);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldSucceedValidationByEmail() throws Exception {
		User user = TestingUserFactory.newUserInstance();
		Credential credential = user.getCredential();
		Authentication authentication = credential.getAuthentication();
		Password password = authentication.getPassword();
		
		String salt = BCrypt.gensalt();
		String passwordValue = "somepassword";
		String hashedPw = BCrypt.hashpw(passwordValue, salt);
		password.setValue(hashedPw);
		
		Mockito.when(daoMock.findByEmail(Mockito.anyString()))
				.thenReturn(user);

		user = service.authenticate("someuser@gmail.com", passwordValue);
		Assert.assertNotNull(user);
	}
}