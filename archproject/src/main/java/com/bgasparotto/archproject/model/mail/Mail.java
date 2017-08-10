package com.bgasparotto.archproject.model.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * <p>
 * Class that represents the basic fields of an e-mail message.
 * </p>
 * <p>
 * It needs later refactoration in order to adhere to Object Calisthenics.
 * </p>
 * 
 * @author Bruno Gasparotto
 *
 */
public class Mail {
	private String from;
	private String to;
	private String subject;
	private String message;

	/**
	 * 
	 * Constructor.
	 *
	 * @param from
	 *            The sender's e-mail address
	 * @param to
	 *            The recipient's e-mail address
	 * @param subject
	 *            The e-mail's subject
	 * @param message
	 *            The e-mail's message
	 */
	public Mail(String from, String to, String subject, String message) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.message = message;
	}

	/**
	 * Gets the Mail's {@code from}.
	 *
	 * @return the {@code Mail}'s {@code from}
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Gets the Mail's {@code to}.
	 *
	 * @return the {@code Mail}'s {@code to}
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Gets the Mail's {@code subject}.
	 *
	 * @return the {@code Mail}'s {@code subject}
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Gets the Mail's {@code message}.
	 *
	 * @return the {@code Mail}'s {@code message}
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * <p>
	 * Gets the {@code MimeMessage} version of {@code this} given the received
	 * {@code Session}.
	 * </p>
	 * <p>
	 * A new object will be returned on every call to this method.
	 * </p>
	 * 
	 * @param session
	 *            The valid e-mail to attach the message to
	 * @return The corresponding {@code MimeMessage} of {@code this} object
	 * @throws MessagingException
	 *             if the {@code session} or the recipient address are invalid
	 */
	public MimeMessage asMimeMessage(Session session)
			throws MessagingException {
		
		MimeMessage mime = new MimeMessage(session);
		mime.setFrom(new InternetAddress(from));
		mime.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mime.setSubject(subject);
		mime.setText(message);		

		return mime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}