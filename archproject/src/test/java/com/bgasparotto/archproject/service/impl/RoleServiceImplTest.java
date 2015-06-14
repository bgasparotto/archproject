package com.bgasparotto.archproject.service.impl;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.persistence.dao.RoleDao;

/**
 * Unit service tests for {@link RoleServiceImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class RoleServiceImplTest extends
		AbstractServiceTest<Role, RoleServiceImpl, RoleDao> {

	/**
	 * Constructor.
	 */
	public RoleServiceImplTest() {
		super(RoleServiceImpl.class, RoleDao.class);
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
}