package com.bgasparotto.archproject.service.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.persistence.dao.GenericDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.AbstractService;
import com.bgasparotto.archproject.service.GenericService;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * <p>
 * Abstract test class which implements unit tests for the basic service
 * operations defined in {@link GenericService}, mocking and internally
 * initializing its dependencies.
 * </p>
 * <p>
 * This abstract class is intended for extension from any test class that wants
 * to write service tests for entities which implements the
 * {@link LongIdentifiable} interface and has a service implementation which
 * extends from {@code AbstractService}.<br>
 * The already implemented unit tests ensure that all the basic operations
 * defined in the {@code GenericService} interface work on the testing service.
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
public abstract class AbstractServiceTest<T extends LongIdentifiable, U extends AbstractService<T>, V extends GenericDao<T>> {
	private AbstractService<T> service;
	private Class<U> serviceClass;
	private Class<V> daoClass;

	private V daoMock;

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
	public AbstractServiceTest(Class<U> serviceClass, Class<V> daoClass) {
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

	/**
	 * Gets the {@code GenericService} instance being used by the tests.
	 * 
	 * @return {@code GenericService} instance
	 */
	protected final GenericService<T> getService() {
		return service;
	}

	/**
	 * Gets the DAO's mock being used by the service.
	 * 
	 * @return DAO's mock being used by the service
	 */
	protected final V getDaoMock() {
		return daoMock;
	}

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

	@Test
	public final void shouldFindById() {
		Long id = getExpectedEntity().getId();
		Mockito.when(daoMock.findById(id)).thenReturn(getExpectedEntity());

		T entity = service.findById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id.longValue(), entity.getId().longValue());
	}

	@Test
	public final void shouldFindNullByNullId() {
		Mockito.when(daoMock.findById(null)).thenReturn(null);

		T entity = service.findById(null);
		Assert.assertNull(entity);
	}

	@Test
	public final void shouldFindNullForIdLessThanOne() {
		Mockito.when(daoMock.findById(Mockito.anyLong())).thenReturn(null);

		T entity = service.findById(0L);
		Assert.assertNull(entity);

		entity = service.findById(-1L);
		Assert.assertNull(entity);

		entity = service.findById(Long.MIN_VALUE);
		Assert.assertNull(entity);
	}

	@Test
	public final void shouldFindNullForNonExistingPositiveId() {
		Long nonExistingId = (long) getExpectedListSize() * 10;
		Mockito.when(daoMock.findById(nonExistingId)).thenReturn(null);

		T entity = service.findById(nonExistingId);
		Assert.assertNull(entity);
	}

	@Test
	public final void shouldFindListWithExpectedSize() {
		int expectedListSize = getExpectedListSize();

		List<T> mockList = Mockito.spy(new ArrayList<>());
		Mockito.when(mockList.size()).thenReturn(expectedListSize);
		Mockito.when(daoMock.findAll()).thenReturn(mockList);

		List<T> list = service.findAll();
		Assert.assertEquals(expectedListSize, list.size());
	}

	@Test
	public final void shouldFindEmptyCollection() {
		Mockito.when(daoMock.findAll()).thenReturn(Collections.emptyList());

		List<T> list = service.findAll();
		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public final void shouldInsert() throws Exception {
		T entity = getExpectedEntity();
		entity.setId(null);
		Long expectedId = Long.valueOf(getExpectedListSize());
		Mockito.when(daoMock.persist(entity)).thenReturn(expectedId);

		Long insertedId = service.insert(entity);
		Assert.assertNotNull(insertedId);
		Assert.assertEquals(expectedId.longValue(), insertedId.longValue());
	}

	@Test(expected = ServiceException.class)
	public final void shouldThrowExceptionWhenInsertFails() throws Exception {
		GeneralPersistenceException exception = new GeneralPersistenceException(
				"Can't persist null");
		Mockito.when(daoMock.persist(null)).thenThrow(exception);

		service.insert(null);
	}

	@Test
	public final void shouldUpdate() throws Exception {
		T entity = getExpectedEntity();
		T mockEntity = Mockito.spy(entity);
		Mockito.when(daoMock.merge(entity)).thenReturn(mockEntity);

		T updatedEntity = service.update(entity);
		Assert.assertNotNull(updatedEntity);
		Assert.assertTrue(mockEntity == updatedEntity);
		Assert.assertFalse(entity == updatedEntity);
	}

	@Test(expected = ServiceException.class)
	public final void shouldThrowExceptionWhenUpdateFails() throws Exception {
		GeneralPersistenceException e = new GeneralPersistenceException();
		Mockito.when(daoMock.merge(Mockito.any())).thenThrow(e);

		service.update(null);
	}

	@Test
	public final void shouldDelete() throws Exception {
		T entity = getExpectedEntity();

		service.delete(entity);
		Mockito.verify(daoMock, Mockito.atLeastOnce()).delete(entity);
	}

	@Test(expected = ServiceException.class)
	public final void shouldThrowExceptionWhenDeleteFails() throws Exception {
		T entity = getExpectedEntity();
		GeneralPersistenceException e = new GeneralPersistenceException();
		Mockito.doThrow(e).when(daoMock).delete(entity);

		service.delete(entity);
	}

	@Test
	public final void shouldDeleteById() throws Exception {
		Long someId = Mockito.anyLong();

		service.delete(someId);
		Mockito.verify(daoMock, Mockito.atLeastOnce()).delete(someId);
	}

	@Test(expected = ServiceException.class)
	public final void shouldThrowExceptionWhenDeleteByIdFails()
			throws Exception {
		Long someId = new Long(0);
		GeneralPersistenceException e = new GeneralPersistenceException();
		Mockito.doThrow(e).when(daoMock).delete(Mockito.anyLong());

		service.delete(someId);
	}
}