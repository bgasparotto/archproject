package com.bgasparotto.archproject.persistence.dao.jpa;

import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.RoleDao;
import com.bgasparotto.archproject.persistence.dao.UserDao;

/**
 * Unit persistence tests for {@link UserDaoImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserDaoImplTest extends JpaDaoTest<User, UserDaoImpl> {
	private UserDao dao;

	/**
	 * Constructor.
	 */
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

	public void testShouldPersistUserWithExistingRole() throws Exception {

		/* TODO Refactor to avoid duplicated code on new tests. */
		EntityManager entityManager = getEntityManager();
		Logger logger = LoggerFactory.getLogger(getClass());
		RoleDao roleDao = new RoleDaoImpl(entityManager, logger);

		/* Created an user and assign a attached role to it. */
		User user = new User(null, "someone", "somesecret", "somemail");
		Role role = roleDao.findById(1L);
		user.getRoles().add(role);

		/* Persist the user and check if its id was generated. */
		Long persistedId = dao.persist(user);
		Assert.assertNotNull(persistedId);
		Assert.assertTrue(persistedId > 0);

		/* Search the user for the given id to confirm that it exists. */
		user = dao.findById(persistedId);
		Assert.assertNotNull(user.getId());

		/* See if the persisted user has the exactly role that was set. */
		Set<Role> roles = user.getRoles();
		Assert.assertEquals(1, roles.size());
		Role firstRole = roles.iterator().next();
		Assert.assertEquals(role.getId().longValue(), firstRole.getId()
				.longValue());
	}
}