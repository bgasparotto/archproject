package com.bgasparotto.archproject.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.persistence.dao.GenericDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * <p>
 * Abstract test class which implements unit tests for the basic service
 * operations defined in {@link GenericService}, mocking and internally
 * initializing its dependencies.
 * </p>
 * <p>
 * The already implemented unit tests ensure that all the basic operations
 * defined in the {@code GenericService} interface work on the testing service.
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
public abstract class AbstractServiceTest<T extends LongIdentifiable, U extends AbstractService<T>, V extends GenericDao<T>>
		extends ServiceTestCase<T, U, V> {
	
	public AbstractServiceTest(Class<U> serviceClass, Class<V> daoClass) {
		super(serviceClass, daoClass);
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