package com.bgasparotto.archproject.persistence.dao.jpa;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.dbunit.JpaDbUnitTestCase;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;

/**
 * Unit persistence tests for {@link UserDaoImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserDaoImplTest extends JpaDbUnitTestCase {
	private UserDao dao;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
		dao = new UserDaoImpl(getEntityManager(), logger);
	}

	@Test
	public void testShouldFindTheUser() {
		User user = dao.findById(1L);
		Assert.assertNotNull(user);

		Assert.assertEquals(1, user.getId().longValue());
		Assert.assertEquals("user1", user.getUsername());
		Assert.assertEquals("$ecret@1", user.getPassword());
		Assert.assertEquals("user1@domain.com", user.getEmail());
	}

	@Test
	public void testShouldReturnNullForNullId() {
		User user = dao.findById(null);
		Assert.assertNull(user);
	}

	@Test
	public void testShouldReturnNullForIdLessThanOne() {
		User user = dao.findById(0L);
		Assert.assertNull(user);

		user = dao.findById(-1L);
		Assert.assertNull(user);

		user = dao.findById(Long.MIN_VALUE);
		Assert.assertNull(user);
	}

	@Test
	public void testShouldReturnNullForNonExistingPositiveId() {
		User user = dao.findById(10L);
		Assert.assertNull(user);
	}

	@Test
	public void testFindByUsername() throws GeneralPersistenceException {
		Assert.assertNull(dao.findById(1000L));

		User user = dao.findByUsername("user1");
		Assert.assertNotNull(user);
		Assert.assertEquals(1, user.getId().longValue());
		Assert.assertEquals("user1", user.getUsername());
		Assert.assertEquals("$ecret@1", user.getPassword());
		Assert.assertEquals("user1@domain.com", user.getEmail());
	}

	@Test
	public void testFindByEmail() throws GeneralPersistenceException {
		Assert.assertNull(dao.findById(1000L));

		User user = dao.findByEmail("user1@domain.com");
		Assert.assertNotNull(user);
		Assert.assertEquals(1, user.getId().longValue());
		Assert.assertEquals("user1", user.getUsername());
		Assert.assertEquals("$ecret@1", user.getPassword());
		Assert.assertEquals("user1@domain.com", user.getEmail());
	}

	@Test
	public void testFindAll() {
		List<User> users = dao.findAll();
		Assert.assertEquals(8, users.size());
	}

	@Test
	public void shouldPersistTheUser() throws GeneralPersistenceException {

		/* Create the user that will be persisted */
		User user = new User();
		user.setUsername("new.user");
		user.setPassword("new$ecret");
		user.setEmail("new.user@d.com");

		Long insertedId = dao.persist(user);
		Assert.assertNotNull(insertedId);
		Assert.assertTrue(insertedId > 0);
		Assert.assertNotNull(user.getId());
	}

	@Test
	public void testUpdateDetachedEntity() throws GeneralPersistenceException {

		/* Merge an object that is not part of the persistence context. */
		User user = new User(1L, "updatedUser1", "new$ecret@1",
				"new_user1@domain.com");
		User updated = dao.merge(user);
		Assert.assertNotNull(updated);

		User updatedUser = dao.findById(1L);
		Assert.assertEquals(user.toString(), updatedUser.toString());
	}

	@Test
	public void testUpdateAttachedEntity() {
		User user = dao.findById(1L);
		Assert.assertEquals("$ecret@1", user.getPassword());
		user.setPassword("anothernew$ecret");

		user = dao.findById(1L);
		Assert.assertEquals("anothernew$ecret", user.getPassword());
	}

	@Test
	public void testDelete() throws GeneralPersistenceException {
		User user = dao.findById(4L);
		Assert.assertNotNull(user);
		dao.delete(user);
		Assert.assertNull(dao.findById(4L));
	}

	@Test
	public void testDeleteById() throws GeneralPersistenceException {
		User user = dao.findById(4L);
		Assert.assertNotNull(user);
		dao.delete(4L);
		Assert.assertNull(dao.findById(4L));
	}
}