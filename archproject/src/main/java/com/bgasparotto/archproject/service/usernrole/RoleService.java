package com.bgasparotto.archproject.service.usernrole;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.service.GenericService;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Services for the {@link Role} entity.
 * 
 * @author Bruno Gasparotto
 * 
 * @see GenericService
 *
 */
public interface RoleService extends GenericService<Role> {

	/**
	 * Find the default role of the system, which is the most basic one.
	 * 
	 * @return Role to be used as default
	 * 
	 * @throws ServiceException
	 *             if no default role was found or defined
	 */
	Role findDefault() throws ServiceException;
}