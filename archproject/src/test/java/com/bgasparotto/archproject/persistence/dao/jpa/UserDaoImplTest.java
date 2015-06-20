package com.bgasparotto.archproject.persistence.dao.jpa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
				"new_user1@domain.com", LocalDateTime.now());
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
		User user = new User(null, "someone", "somesecret", "somemail",
				LocalDateTime.now());
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

	public void testShouldReturnUsersWithRegistrationDates() {
		List<User> users = dao.findAll();
		for (User u : users) {
			Assert.assertNotNull(u.getRegistrationDate());
		}

		/* Pick two users and assert they registration dates. */
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		LocalDateTime registrationDate1 = users.get(0).getRegistrationDate();
		String expected1 = "2015-06-20T00:00:00";
		Assert.assertEquals(expected1, formatter.format(registrationDate1));

		LocalDateTime registrationDate2 = users.get(1).getRegistrationDate();
		String expected2 = "2015-06-21T23:59:59";
		Assert.assertEquals(expected2, formatter.format(registrationDate2));
	}
}