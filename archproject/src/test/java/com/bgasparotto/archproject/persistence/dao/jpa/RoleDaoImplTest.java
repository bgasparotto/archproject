package com.bgasparotto.archproject.persistence.dao.jpa;

import org.junit.Before;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.persistence.dao.RoleDao;

/**
 * Unit persistence tests for {@link RoleDaoImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class RoleDaoImplTest extends JpaDaoTest<Role, RoleDaoImpl> {
	private RoleDao dao;

	/**
	 * Constructor.
	 */
	public RoleDaoImplTest() {
		super(RoleDaoImpl.class);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		this.dao = (RoleDao) getDao();
	}

	@Override
	protected Role getPersistedEntity() {
		Role role = dao.findById(5L);
		return role;
	}

	@Override
	protected Long getPersistedEntityId() {
		return 5L;
	}

	@Override
	protected Role getUnpersistedEntity() {
		Role role = new Role(5L, "updatedRole5");
		return role;
	}

	@Override
	protected int getExpectedListSize() {
		return 8;
	}
}