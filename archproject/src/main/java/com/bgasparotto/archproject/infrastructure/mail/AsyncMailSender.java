/**
 * 
 */
package com.bgasparotto.archproject.infrastructure.mail;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

/**
 * Asynchronous e-mail sender.
 * 
 * @author Bruno Gasparotto
 *
 */
class AsyncMailSender implements Runnable {
	private final MimeMessage message;
	private final Logger logger;

	/**
	 * Constructor.
	 *
	 * @param message
	 *            The e-mail message to be sent
	 * @param logger
	 *            Logger to log error events
	 */
	AsyncMailSender(MimeMessage message, Logger logger) {
		this.message = message;
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
			message.setSentDate(new Date());
			Transport.send(message);
		} catch (MessagingException e) {
			String m = "Failed to send an e-mail message.";
			logger.error(m);
		}
	}

	/**
	 * Sends the async e-mail message received on this object instantiation.
	 */
	void send() {
		new Thread(this).start();
	}
}