package com.bgasparotto.archproject.persistence.dbunit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

/**
 * Abstract DbUnit test case for persistence tests with JPA.
 * 
 * @author Bruno Gasparotto
 *
 */
public abstract class JpaDbUnitTestCase extends DbUnitTestCase {
	private EntityManagerFactory factory;
	private EntityManager entityManager;
	private EntityTransaction transaction;

	@Before
	protected void setUp() throws Exception {
		super.setUp();

		factory = Persistence
				.createEntityManagerFactory(DbUnitParameters.PERSISTENCE_UNIT
						.getValue());

		entityManager = factory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}

	@After
	protected void tearDown() throws Exception {
		transaction.commit();
		entityManager.close();
		factory.close();

		super.tearDown();
	}

	/**
	 * Get the current {@code EntityManager} instance.
	 * 
	 * @return {@code EntityManager} instance
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}
}