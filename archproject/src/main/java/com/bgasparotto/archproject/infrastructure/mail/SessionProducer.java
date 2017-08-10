package com.bgasparotto.archproject.infrastructure.mail;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.mail.Session;

/**
 * E-mail's {@code Session} producer.
 * 
 * @author Bruno Gasparotto
 *
 */
@ApplicationScoped
public class SessionProducer {

	/*
	 * This resource has to be configured in the application server.
	 */
	@Produces
	@Resource(mappedName = "java:jboss/mail/gmail")
	private Session session;
}