package com.bgasparotto.archproject.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.Roles;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.model.Username;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.service.usernrole.UserServiceImpl;

/**
 * Unit service tests for {@link UserServiceImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplTest
		extends AbstractServiceTest<User, UserServiceImpl, UserDao> {

	/**
	 * Constructor.
	 */
	public UserServiceImplTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Override
	protected User getExpectedEntity() {
		Username username = new Username("someuser", "someuser@gmail.com");
		Password password = new Password("somepassword");
		Authentication authentication = new Authentication(username, password);
		Credential credential = new Credential(authentication, new Roles());
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
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().length());
	}
}