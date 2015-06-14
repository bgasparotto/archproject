package com.bgasparotto.archproject.persistence.dao.jpa;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.bgasparotto.archproject.model.Role;
import com.bgasparotto.archproject.persistence.dao.RoleDao;

/**
 * {@link RoleDao} implementation using {@code JPA}.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class RoleDaoImpl extends JpaDao<Role> implements RoleDao {

	/**
	 * Constructor.
	 * 
	 * @param entityManager
	 *            The {@code EntityManager} to be used by this {@code DAO}
	 */
	@Inject
	public RoleDaoImpl(EntityManager entityManager, Logger logger) {
		super(entityManager, Role.class, logger);
	}
}