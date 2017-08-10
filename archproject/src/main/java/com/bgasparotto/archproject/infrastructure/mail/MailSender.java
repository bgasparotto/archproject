package com.bgasparotto.archproject.infrastructure.mail;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

/**
 * E-mail sender.
 * 
 * @author Bruno Gasparotto
 *
 */
@ApplicationScoped
public class MailSender {

	@Inject
	private Session session;

	@Inject
	private Logger logger;

	/**
	 * Sends an e-mail message.
	 * 
	 * @param message
	 *            The message to be sent
	 */
	public void send(MimeMessage message) {
		AsyncMailSender asyncMailSender = new AsyncMailSender(message, logger);
		asyncMailSender.send();
	}

	/**
	 * Gets the MailSender's {@code session}.
	 *
	 * @return The MailSender's {@code session}
	 */
	public Session getSession() {
		return session;
	}
}