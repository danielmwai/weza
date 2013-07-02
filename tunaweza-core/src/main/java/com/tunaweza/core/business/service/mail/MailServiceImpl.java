/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.service.mail;

import com.tunaweza.core.business.model.user.User;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("mailService")
public class MailServiceImpl implements MailService {
	public static Logger logger = Logger.getLogger(MailServiceImpl.class);
	
	@Autowired
	private JavaMailSenderImpl mailSender;

//	@Override
	public void sendMail(User recipient, String password)
 {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
		helper.setFrom("JJPeople <noreply@jjpeople.com>");
		helper.setTo(recipient.getEmail());
		helper.setSubject("Test subject");
		helper.setText(String.format("Testing", recipient
				.getFirstName(), recipient.getLastName(), recipient
				.getUsername(), password));
		mailSender.send(message);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	@Override
	public void sendMail(String message, String email) {
		
		logger.info("Inside the sendMail function \n");
		MimeMessage emailMessage = mailSender.createMimeMessage();
		try{

		logger.info("emailMessage created \n");
		MimeMessageHelper helper = new MimeMessageHelper(emailMessage, true);
		
		logger.info("helper set \n");
		helper.setFrom("JJPeople <noreply@jjpeople.com>");
		helper.setTo(email);
		helper.setSubject("JJPeople: JJTeach Account Details");
		helper.setText(message, true);
		
		logger.info("Actual sending of email");
	
		mailSender.send(emailMessage);
			logger.info("Mail sent");
		} catch (Exception ex) {
			logger.info("Oops!Mail Not Sent");
			logger.info(ex);
		}

	}
	
	@Override
	public void sendDisablingMail(String message, String email)throws Exception 
	{
		MimeMessage emailMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(emailMessage, true);

		helper.setFrom("JJPeople <noreply@jjpeople.com>");
		helper.setTo(email);
		helper.setSubject("JJPeople: JJPeople Training");
		helper.setText(message, true);
		try {
			mailSender.send(emailMessage);
			logger.info("Mail Sent");
		} catch (Exception ex) {
			logger.info("Oops!Mail Not Sent");
			logger.info(ex);
		}

	}
}

