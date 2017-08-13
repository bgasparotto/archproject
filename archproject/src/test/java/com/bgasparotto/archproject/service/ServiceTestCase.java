package com.bgasparotto.archproject.service;

import java.lang.reflect.Constructor;

import org.junit.Before;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.persistence.dao.GenericDao;

/**
 * <p>
 * Abstract class that is intended for extension from any test class that wants
 * to write service tests for entities which implements the
 * {@link LongIdentifiable} interface and has a service implementation which
 * extends from {@code AbstractService}.
 * </p>
 * <p>
 * For properly execution of the tests, a implementation for the abstract
 * methods declared in the class must be provided.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 * @param <T>
 *            The type of the entity being served under the testing service
 * @param <U>
 *            The type of the <strong>implementation</strong> of the testing
 *            service
 * @param <V>
 *            The type of the <strong>interface</strong> of the {@code DAO}
 *            dependency of the testing service
 */
public abstract class ServiceTestCase <T extends LongIdentifiable, U extends AbstractService<T>, V extends GenericDao<T>> {
	private Class<U> serviceClass;
	private Class<V> daoClass;

	protected U service;
	protected V daoMock;

	/**
	 * Constructor.
	 * 
	 * @param serviceClass
	 *            Class of the <strong>implementation</strong> of the testing
	 *            service
	 * @param daoClass
	 *            Class of the <strong>interface</strong> of the {@code DAO}
	 *            dependency of the testing service
	 */
	public ServiceTestCase(Class<U> serviceClass, Class<V> daoClass) {
		this.serviceClass = serviceClass;
		this.daoClass = daoClass;
	}

	/**
	 * Provide an entity on the persistence context to be tested.
	 * 
	 * @return Entity on the persistence context
	 */
	protected abstract T getExpectedEntity();

	/**
	 * Provide a expected number of records of the the testing entity on the
	 * system.
	 * 
	 * @return Expected number of records
	 */
	protected abstract int getExpectedListSize();

	@Before
	public void setUp() throws Exception {
		try {
			Logger logger = LoggerFactory.getLogger(serviceClass);
			this.daoMock = Mockito.mock(daoClass);

			Constructor<U> serviceConstructor = serviceClass.getConstructor(
					daoClass, Logger.class);
			this.service = serviceConstructor.newInstance(daoMock, logger);
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize service", e);
		}
	}
}