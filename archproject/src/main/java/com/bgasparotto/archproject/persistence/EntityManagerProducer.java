package com.bgasparotto.archproject.persistence;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

/**
 * {@link EntityManager} producer for the {@code archproject} persistence unit.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class EntityManagerProducer {

	@Inject
	private Logger logger;

	@PersistenceContext(unitName = "arch-project")
	private EntityManager entityManager;

	/**
	 * Gets a {@link EntityManager} instance.
	 * 
	 * @return {@code EntityManager} instance
	 */
	@Produces
	public EntityManager getInstance() {
		logger.info("Creating entity manager");
		return entityManager;
	}
}