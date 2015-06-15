package com.bgasparotto.archproject.service.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.persistence.dao.RoleDao;
import com.bgasparotto.archproject.service.RoleService;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * {@link RoleService} implementation.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class RoleServiceImpl extends AbstractService<Role> implements
		RoleService {

	/** The default role is always the first one registered on the system. */
	private static final Long DEFAULT_ROLE_ID = 1L;

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

	@Override
	public Role findDefault() throws ServiceException {
		Role defaultRole = this.findById(DEFAULT_ROLE_ID);

		if (defaultRole == null) {
			String message = "Default Role not found.";
			logger.error(message);
			throw new ServiceException(message);
		}

		return defaultRole;
	}
}