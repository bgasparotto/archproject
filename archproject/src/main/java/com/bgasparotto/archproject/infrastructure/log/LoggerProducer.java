package com.bgasparotto.archproject.infrastructure.log;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code Logger} producer.
 * 
 * @author Bruno Gasparotto
 *
 */
@ApplicationScoped
public class LoggerProducer {

	/**
	 * Gets a {@link Logger} instance.
	 * 
	 * @param injectionPoint
	 *            The point where the {@code Logger} was injected. This is
	 *            generally provided by the application server
	 * @return {@link Logger} instance
	 */
	@Produces
	public Logger getInstance(InjectionPoint injectionPoint) {
		String name = injectionPoint.getMember().getDeclaringClass().getName();
		Logger logger = LoggerFactory.getLogger(name);
		return logger;
	}
}