package com.bgasparotto.archproject.service.mail;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.bgasparotto.archproject.infrastructure.interceptor.ExceptionLogging;
import com.bgasparotto.archproject.infrastructure.mail.MailSender;
import com.bgasparotto.archproject.model.Login;
import com.bgasparotto.archproject.model.Registration;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.model.mail.Mail;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * {@code MailService} implementation.
 * 
 * @author Bruno Gasparotto
 *
 */
@RequestScoped
@ExceptionLogging
public class MailServiceImpl implements MailService {

	@Inject
	private MailSender sender;
	
	@Inject
	private HttpServletRequest request;

	@Override
	public void sendValidationEmail(User user) throws ServiceException {
		Login login = user.getLogin();
		String username = login.getUsername();
		String email = login.getEmail();

		Registration registration = user.getRegistration();
		String verificationCode = registration.getVerificationCode();
		
		String message = buildVerificationMessage(username, verificationCode);

		String from = "bgasparotto-archproject@gmail.com";
		String subject = "Registration Details";
		Mail mail = new Mail(from, email, subject, message);
		
		sendMail(mail);
	}

	/**
	 * Builds the verification message to be send by e-mail.
	 * 
	 * @param username
	 *            The username of the destination user
	 * @param verificationCode
	 *            The verification code of the user
	 * @return The message to be sent to the user
	 */
	private String buildVerificationMessage(String username,
			String verificationCode) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Thanks for your registration ").append(username);
		builder.append(", open the link below to validate your account: \n");
		
		String baseUrl = buildVerificationBaseUrl();
		builder.append(baseUrl).append("/pages/public/validation.xhtml?");
		builder.append("username=").append(username);
		builder.append("&");
		builder.append("verificationCode=").append(verificationCode);

		return builder.toString();
	}
	
	/**
	 * Builds the base URL to be combined with the validation page and
	 * parameters.
	 * 
	 * @return The base URL of the application.
	 */
	private String buildVerificationBaseUrl() {
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		
		url = url.substring(0, url.length() - uri.length()) + path;
		return url;
	}

	@Override
	public void sendMail(Mail mail) throws ServiceException {
		Session session = sender.getSession();

		try {
			MimeMessage message = mail.asMimeMessage(session);
			sender.send(message);
		} catch (MessagingException e) {
			String message = "Failed to send an e-mail message.";
			throw new ServiceException(message, e);
		}
	}
}