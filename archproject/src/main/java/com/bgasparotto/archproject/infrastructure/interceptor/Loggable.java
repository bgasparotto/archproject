package com.bgasparotto.archproject.infrastructure.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

/**
 * Logging interceptor.
 * 
 * @author Bruno Gasparotto
 *
 */
@Interceptor
public class Loggable {
	private Logger logger;

	/**
	 * Constructor.
	 *
	 * @param logger
	 *            The {@code Logger} to be used by this interceptor
	 */
	@Inject
	public Loggable(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Intercepts a method invocation to write error log if an exception is
	 * throw during its execution.
	 * 
	 * @param joinPoint
	 *            The join point of the method on the stack
	 * @return The intercepted method result, or {@code null} if its return type
	 *         is {@code void} or its part of an interceptor lifecycle
	 * @throws Exception
	 *             thrown by the invoked method after logging
	 */
	@AroundInvoke
	public Object log(InvocationContext joinPoint) throws Exception {
		try {
			Object o = joinPoint.proceed();
			return o;
		} catch (Exception e) {
			String message = e.getMessage();
			logger.error(message, e);
			throw e;
		}
	}
}