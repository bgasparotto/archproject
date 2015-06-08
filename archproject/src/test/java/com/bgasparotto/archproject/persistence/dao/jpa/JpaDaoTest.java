package com.bgasparotto.archproject.persistence.dao.jpa;

import java.lang.reflect.Constructor;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.persistence.dbunit.JpaDbUnitTestCase;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;

/**
 * <p>
 * Abstract test class which implements unit tests for the basic persistence
 * operations defined in {@link JpaDao}.
 * </p>
 * <p>
 * This abstract class is intended for extension from any test class that wants
 * to write persistence tests for entities which implements the
 * {@link LongIdentifiable} interface and has a {@code DAO} which extends from
 * {@code JpaDao}.<br>
 * The already implemented unit tests ensure that all the basic operations
 * defined in the {@code JpaDao} interface work on the testing DAO.
 * </p>
 * <p>
 * For properly execution of the tests, a implementation for the abstract
 * methods declared in the class must be provided.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 * @param <T>
 *            The type of the entity being persisted under the testing
 *            {@code DAO}
 * @param <U>
 *            The type of the <strong>implementation</strong> of the testing
 *            {@code DAO}
 */
public abstract class JpaDaoTest<T extends LongIdentifiable, U extends JpaDao<T>>
		extends JpaDbUnitTestCase {
	private JpaDao<T> dao;
	private Class<U> clazz;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 *            Class of the <strong>implementation</strong> of the testing
	 *            {@code DAO}
	 */
	public JpaDaoTest(Class<U> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Provide an entity on the persistence context to be tested.
	 * 
	 * @return Entity on the persistence context
	 */
	protected abstract T getPersistedEntity();

	/**
	 * Provide an {@code id} of an existing entity on database.
	 * 
	 * @return {@code Id} of an existing entity
	 */
	protected abstract Long getPersistedEntityId();

	/**
	 * Provide an entity detached from the persistence context.
	 * 
	 * @return Entity on the persistence context
	 */
	protected abstract T getUnpersistedEntity();

	/**
	 * Provide a expected number of records of the the testing entity on
	 * database.
	 * 
	 * @return Expected number of records
	 */
	protected abstract int getExpectedListSize();

	/**
	 * Gets the {@code JpaDao} instance being used by the tests.
	 * 
	 * @return {@code JpaDao} instance
	 */
	protected final JpaDao<T> getDao() {
		return dao;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		try {
			EntityManager entityManager = getEntityManager();
			Logger logger = LoggerFactory.getLogger(clazz);

			Constructor<U> daoConstructor = clazz.getConstructor(
					EntityManager.class, Logger.class);
			U dao = daoConstructor.newInstance(entityManager, logger);
			this.dao = dao;

		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize DAO", e);
		}
	}

	public final void testFindById() {
		Long id = getPersistedEntityId();

		T entity = dao.findById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id.longValue(), entity.getId().longValue());
	}

	public final void testFindNullByNullId() {
		T entity = dao.findById(null);
		Assert.assertNull(entity);
	}

	public final void testFindNullForIdLessThanOne() {
		T entity = dao.findById(0L);
		Assert.assertNull(entity);

		entity = dao.findById(-1L);
		Assert.assertNull(entity);

		entity = dao.findById(Long.MIN_VALUE);
		Assert.assertNull(entity);
	}

	public final void testReturnNullForNonExistingPositiveId() {
		Long nonExistingId = (long) getExpectedListSize() * 10;

		T entity = dao.findById(nonExistingId);
		Assert.assertNull(entity);
	}

	public final void testFindAll() {
		List<T> entities = dao.findAll();
		Assert.assertEquals(getExpectedListSize(), entities.size());
	}

	public final void testPersist() throws GeneralPersistenceException {
		T entity = getUnpersistedEntity();
		entity.setId(null);

		Long insertedId = dao.persist(entity);
		Assert.assertNotNull(insertedId);
		Assert.assertTrue(insertedId > 0);
		Assert.assertNotNull(entity.getId());
	}

	public final void testFailWhenTryingToPersistNull() {
		try {
			dao.persist(null);
			fail("Shouldn't persisted null!");
		} catch (GeneralPersistenceException e) {
			Assert.assertEquals("Can't persist null", e.getMessage());
		}
	}
	
	public final void testFailWhenTryingToPersistEntityWithNotNullExistingId() {
		T entity = getUnpersistedEntity();
		entity.setId(getPersistedEntityId());
		
		try {
			dao.persist(entity);
			fail("Shouldn't persisted entity with an id!");
		} catch (GeneralPersistenceException e) {
			Assert.assertEquals("Failed to persist an entity", e.getMessage());
		}
	}
	
	public final void testFailWhenTryingToPersistEntityWithNotNullNonExistingId() {
		T entity = getUnpersistedEntity();
		Long nonExistingId = Long.valueOf(getExpectedListSize());
		entity.setId(nonExistingId);
		
		try {
			dao.persist(entity);
			fail("Shouldn't persisted entity with an id!");
		} catch (GeneralPersistenceException e) {
			Assert.assertEquals("Failed to persist an entity", e.getMessage());
		}
	}

	public final void testMergeAttachedEntity()
			throws GeneralPersistenceException {
		T entity = getPersistedEntity();

		T mergedEntity = dao.merge(entity);
		Assert.assertTrue(entity == mergedEntity);
	}

	public final void testMergeDetachedEntity()
			throws GeneralPersistenceException {
		T entity = getUnpersistedEntity();
		entity.setId(null);

		entity.setId(1L);
		T mergedEntity = dao.merge(entity);

		Assert.assertTrue(entity != mergedEntity);
		Assert.assertEquals(entity.getId().longValue(), mergedEntity.getId()
				.longValue());
		Assert.assertEquals(entity.toString(), mergedEntity.toString());
	}

	public final void testFailMergingNull() {
		try {
			dao.merge(null);
			fail("Shouldn't merged null!");
		} catch (GeneralPersistenceException e) {
			Assert.assertEquals("Can't merge null", e.getMessage());
		}
	}

	public final void testDeleteAttachedEntity()
			throws GeneralPersistenceException {
		T entity = getPersistedEntity();

		Long entityId = entity.getId();
		Assert.assertNotNull(dao.findById(entityId));

		dao.delete(entity);
		Assert.assertNull(dao.findById(entityId));
	}

	public final void testDeleteDetachedEntity()
			throws GeneralPersistenceException {
		T entity = getUnpersistedEntity();

		Long entityId = entity.getId();
		Assert.assertNotNull(dao.findById(entityId));

		dao.delete(entity);
		Assert.assertNull(dao.findById(entityId));
	}

	public final void testDeleteNull() {
		try {
			T type = null;
			dao.delete(type);
			fail("Shouldn't deleted null!");
		} catch (GeneralPersistenceException e) {
			Assert.assertEquals("Can't delete null", e.getMessage());
		}
	}

	public final void testDeleteById() throws GeneralPersistenceException {
		Long id = getPersistedEntityId();

		Assert.assertNotNull(dao.findById(id));

		dao.delete(id);
		Assert.assertNull(dao.findById(id));
	}
}