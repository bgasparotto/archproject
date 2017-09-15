package com.bgasparotto.archproject.persistence.dao.jpa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Password;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.model.RolesGroup;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.RoleDao;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;

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
		Login login = new Login("updatedUser1",
				"new_user1@domain.com");
		Password password = new Password("new$ecret@1");
		Authentication authentication = new Authentication(login, password);
		Credential credential = new Credential(authentication, new RolesGroup());
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

	public void testShouldPersistUserWithExistingRole() throws Exception {

		/* TODO Refactor to avoid duplicated code on new tests. */
		EntityManager entityManager = getEntityManager();
		Logger logger = LoggerFactory.getLogger(getClass());
		RoleDao roleDao = new RoleDaoImpl(entityManager, logger);

		/* Created an user and assign a attached role to it. */
		Login login = new Login("someone", "somemail");
		Password password = new Password("somesecret");
		Authentication authentication = new Authentication(login, password);
		Credential credential = new Credential(authentication, new RolesGroup());
		String verificationCode = UUID.randomUUID().toString();
		Registration registration = new Registration(LocalDateTime.now(),
				verificationCode);
		User user = new User(1L, credential, registration);
		Role role = roleDao.findById(1L);
		user.getRolesGroup().getRoles().add(role);

		/*
		 * Merge the user with the existing role and check if its id was
		 * generated.
		 */
		User mergedUser = dao.merge(user);
		Long persistedId = mergedUser.getId();
		Assert.assertNotNull(persistedId);
		Assert.assertTrue(persistedId > 0);

		/* Search the user for the given id to confirm that it exists. */
		user = dao.findById(persistedId);
		Assert.assertNotNull(user.getId());

		/* See if the persisted user has the exactly role that was set. */
		Set<Role> roles = user.getRolesGroup().getRoles();
		Assert.assertEquals(1, roles.size());
		Role firstRole = roles.iterator().next();
		Assert.assertEquals(role.getId().longValue(),
				firstRole.getId().longValue());
	}

	public void testShouldReturnUsersWithRegistrationDates() {
		List<User> users = dao.findAll();

		users.forEach(u -> Assert
				.assertNotNull(u.getRegistration().getRegistrationDate()));

		/* Pick two users and assert they registration dates. */
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		LocalDateTime registrationDate1 = users.get(0).getRegistration()
				.getRegistrationDate();
		String expected1 = "2015-06-20T00:00:00";
		Assert.assertEquals(expected1, formatter.format(registrationDate1));

		LocalDateTime registrationDate2 = users.get(1).getRegistration()
				.getRegistrationDate();
		String expected2 = "2015-06-21T23:59:59";
		Assert.assertEquals(expected2, formatter.format(registrationDate2));
	}
	
	public void testShouldFindUserByUsername() throws Exception {
		String expectedUsername = "admin";
		User user = dao.findByUsername(expectedUsername);
		Assert.assertNotNull(user);
		
		Login login = user.getLogin();
		Assert.assertEquals(expectedUsername, login.getUsername());
	}
	
	public void testShouldntFindAnyUserByUnexistentUsername() throws Exception {
		User user = dao.findByUsername("nouser");
		Assert.assertNull(user);
	}
	
	public void testShouldReturnNullUserForNullUsername() throws Exception {
		User user = dao.findByUsername(null);
		Assert.assertNull(user);
	}
	
	public void testShouldThrowGPEWhenTryingToFindByUsername()
			throws Exception {
		
		EntityManager emMock = Mockito.mock(EntityManager.class);
		Query queryMock = Mockito.mock(Query.class);
		Mockito.when(queryMock.getSingleResult()).thenThrow(new PersistenceException());	
		Mockito.when(emMock.createQuery(Mockito.anyString())).thenReturn(queryMock);
		
		dao.setEntityManager(emMock);
		
		try {
			dao.findByUsername("someuser");
			fail("Should have thrown GeneralPersistenceException.");
		} catch (GeneralPersistenceException e) {
			Assert.assertNotNull(e);
		}
	}
	
	public void testShouldFindUserByEmail() throws Exception {
		String expectedEmail = "admin@domain.com";
		User user = dao.findByEmail(expectedEmail);
		Assert.assertNotNull(user);
		
		Login login = user.getLogin();
		Assert.assertEquals(expectedEmail, login.getEmail());
	}
	
	public void testShouldntFindAnyUserByUnexistentEmail() throws Exception {
		User user = dao.findByEmail("noemail");
		Assert.assertNull(user);
	}
	
	public void testShouldReturnNullUserForNullEmail() throws Exception {
		User user = dao.findByEmail(null);
		Assert.assertNull(user);
	}
	
	public void testShouldThrowGPEWhenTryingToFindByEmail()
			throws Exception {
		
		EntityManager emMock = Mockito.mock(EntityManager.class);
		Query queryMock = Mockito.mock(Query.class);
		Mockito.when(queryMock.getSingleResult()).thenThrow(new PersistenceException());	
		Mockito.when(emMock.createQuery(Mockito.anyString())).thenReturn(queryMock);
		
		dao.setEntityManager(emMock);
		
		try {
			dao.findByEmail("someuser@gmail.com");
			Assert.assertFalse(true); // Should never be called.
		} catch (GeneralPersistenceException e) {
			Assert.assertNotNull(e);
		}
	}
}