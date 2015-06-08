package com.bgasparotto.archproject.service.impl;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;

/**
 * Unit service tests for {@link UserServiceImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplTest extends
		AbstractServiceTest<User, UserServiceImpl, UserDao> {

	/**
	 * Constructor.
	 */
	public UserServiceImplTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Override
	protected User getExpectedEntity() {
		User user = new User(1L, "someuser", "somepassword",
				"someuser@gmail.com");
		return user;
	}

	@Override
	protected int getExpectedListSize() {
		return 8;
	}
}