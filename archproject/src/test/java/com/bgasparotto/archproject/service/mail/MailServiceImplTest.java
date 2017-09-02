package com.bgasparotto.archproject.service.mail;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bgasparotto.archproject.infrastructure.mail.MailSender;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.model.mail.Mail;
import com.bgasparotto.archproject.service.exception.ServiceException;
import com.bgasparotto.archproject.service.usernrole.TestingUserFactory;

/**
 * Unit tests for {@link MailServiceImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class MailServiceImplTest {
	private MailService service;
	private MailSender sender;
	private HttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		sender = mock(MailSender.class);
		request = mock(HttpServletRequest.class);

		service = new MailServiceImpl(sender, request);
	}

	@After
	public void tearDown() throws Exception {
		service = null;
		sender = null;
		request = null;
	}
	
	@Test
	public void shouldNotThrowExceptionOnNoArgConstructor() {
		service = new MailServiceImpl();
		Assert.assertNotNull(service);
	}

	@Test
	public void shouldSendMail() throws Exception {
		Mail mail = new Mail("from@mail.com", "to@mail.com", "Sub", "Message.");
		service.sendMail(mail);
		
		verify(sender, times(1)).send(any());
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowServiceExceptionWhenSenderFails() throws Exception {
		Mail mail = mock(Mail.class);
		when(mail.asMimeMessage(any())).thenThrow(new MessagingException());

		service.sendMail(mail);
	}
	
	@Test
	public void shouldSendValidationEmail() throws Exception {
		String somePage = "/archproject/pages/public/somepage.xhtml";
		StringBuffer buffer = new StringBuffer("http://localhost:8080");
		buffer.append(somePage);
		String requestUri = somePage;
		String contextPath = "/archproject";
		when(request.getRequestURL()).thenReturn(buffer);
		when(request.getRequestURI()).thenReturn(requestUri);
		when(request.getContextPath()).thenReturn(contextPath);
		
		User user = TestingUserFactory.newUserInstance();
		service.sendValidationEmail(user);
		
		verify(sender, times(1)).send(any());
	}
}