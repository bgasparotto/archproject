package com.bgasparotto.archproject.service.mail;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.model.mail.Mail;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * E-mail sending services.
 * 
 * @author Bruno Gasparotto
 *
 */
public interface MailService {

	/**
	 * Sends a validation e-mail to the user.
	 * 
	 * @param user
	 *            The user which the validation e-mail has to be sent to
	 * @throws ServiceException
	 *             if an error occurs when trying to send the e-mail
	 */
	void sendValidationEmail(User user)
			throws ServiceException;
	
	/**
	 * Sends an e-mail message.
	 * 
	 * @param mail
	 *            The e-mail message to be sent
	 * @throws ServiceException
	 *             if an error occurs whilst trying to send the e-mail message
	 */
	void sendMail(Mail mail) throws ServiceException;
}