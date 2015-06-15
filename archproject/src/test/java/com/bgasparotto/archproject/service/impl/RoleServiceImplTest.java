package com.bgasparotto.archproject.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.persistence.dao.RoleDao;
import com.bgasparotto.archproject.service.RoleService;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Unit service tests for {@link RoleServiceImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class RoleServiceImplTest extends
		AbstractServiceTest<Role, RoleServiceImpl, RoleDao> {
	private RoleService roleService;
	private RoleDao daoMock;

	/**
	 * Constructor.
	 */
	public RoleServiceImplTest() {
		super(RoleServiceImpl.class, RoleDao.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		roleService = (RoleService) getService();
		daoMock = getDaoMock();
	}

	@Override
	protected Role getExpectedEntity() {
		Role role = new Role(5L, "somerole");
		return role;
	}

	@Override
	protected int getExpectedListSize() {
		return 8;
	}

	@Test
	public void shouldFindDefaultRole() throws Exception {
		Role defaultRole = new Role(1L, "SOMEROLE");
		Mockito.when(daoMock.findById(1L)).thenReturn(defaultRole);

		Role foundRole = roleService.findDefault();

		Assert.assertNotNull(foundRole);
		Assert.assertEquals(1L, foundRole.getId().longValue());
		Mockito.verify(daoMock, Mockito.atLeastOnce()).findById(1L);
	}

	@Test(expected = ServiceException.class)
	public void shouldFailToFindDefaultRole() throws Exception {
		Mockito.when(daoMock.findById(1L)).thenReturn(null);
		roleService.findDefault();
	}
}