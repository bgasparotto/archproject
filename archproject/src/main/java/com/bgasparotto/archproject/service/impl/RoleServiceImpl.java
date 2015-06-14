package com.bgasparotto.archproject.service.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.persistence.dao.RoleDao;
import com.bgasparotto.archproject.service.RoleService;

/**
 * {@link RoleService} implementation.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class RoleServiceImpl extends AbstractService<Role> implements
		RoleService {

	/**
	 * Constructor.
	 * 
	 * @param roleDao
	 *            {@code RoleDao} implementation to be used by this service
	 */
	@Inject
	public RoleServiceImpl(RoleDao roleDao, Logger logger) {
		super(roleDao, logger);
	}
}