package com.bgasparotto.archproject.persistence.dao.jpa;

import org.junit.Before;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;

/**
 * Unit persistence tests for {@link UserDaoImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserDaoImplTest extends JpaDaoTest<User, UserDaoImpl> {
	private UserDao dao;

	public UserDaoImplTest() {
		super(UserDaoImpl.class);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		this.dao = (UserDao) getDao();
	}

	@Override
	protected User getPersistedEntity() {
		User user = dao.findById(1L);
		return user;
	}

	@Override
	protected Long getPersistedEntityId() {
		return 1L;
	}

	@Override
	protected User getUnpersistedEntity() {
		User user = new User(1L, "updatedUser1", "new$ecret@1",
				"new_user1@domain.com");
		return user;
	}

	@Override
	protected int getExpectedListSize() {
		return 8;
	}
}