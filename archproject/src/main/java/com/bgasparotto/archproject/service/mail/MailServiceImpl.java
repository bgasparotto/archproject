package com.bgasparotto.archproject.service.mail;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

import com.bgasparotto.archproject.infrastructure.mail.MailSender;
import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.Credential;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.model.Username;
import com.bgasparotto.archproject.model.mail.Mail;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * {@code MailService} implementation.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
public class MailServiceImpl implements MailService {

	@Inject
	private MailSender sender;

	@Inject
	private Logger logger;

	@Override
	public void sendValidationEmail(User user) throws ServiceException {
		Credential credential = user.getCredential();
		Authentication authentication = credential.getAuthentication();
		Username authenticationUsername = authentication.getUsername();
		String username = authenticationUsername.getUsername();
		String email = authenticationUsername.getEmail();

		Registration registration = user.getRegistration();
		String verificationCode = registration.getVerificationCode();
		
		String message = "Thanks for your registration " + username
				+ ", here's your verification code: " + verificationCode
				+ ". Don't worry, you don't need to use it yet.";

		String from = "bgasparotto-archproject@gmail.com";
		String subject = "Registration Details";
		Mail mail = new Mail(from, email, subject, message);
		
		sendMail(mail);
	}

	@Override
	public void sendMail(Mail mail) throws ServiceException {
		Session session = sender.getSession();

		try {
			MimeMessage message = mail.asMimeMessage(session);
			sender.send(message);
		} catch (MessagingException e) {
			String message = "Failed to send an e-mail message.";
			logger.error(message, e);
			throw new ServiceException(message, e);
		}
	}
}