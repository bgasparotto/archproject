package com.bgasparotto.archproject.persistence.dbunit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Abstract DbUnit test case for persistence tests with JPA.
 * </p>
 * <p>
 * This implementation is intended to use JUnit 3 as the test runner, because no
 * successful implementation of JUnit 4 as the test runner along with an in
 * memory database such as {@code H2} was achieved yet.
 * </p>
 * <p>
 * Since it uses JUnit 3 as the test runner, the prefix {@code test} on the
 * methods intended to be test is mandatory, and the {@link Test} annotation
 * will not be processed by the runner.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 */
public abstract class JpaDbUnitTestCase extends DbUnitTestCase {
	private EntityManagerFactory factory;
	private EntityManager entityManager;
	private EntityTransaction transaction;

	/**
	 * Creates the EntityManager, begins a transaction and setup the database
	 * for DbUnit.
	 */
	@Before
	protected void setUp() throws Exception {
		factory = Persistence
				.createEntityManagerFactory(DbUnitParameters.PERSISTENCE_UNIT
						.getValue());

		entityManager = factory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();

		super.setUp();
	}

	/**
	 * Commits the transaction and closes JPA and DbUnit resources.
	 */
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