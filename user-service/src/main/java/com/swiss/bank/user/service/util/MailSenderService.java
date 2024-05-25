package com.swiss.bank.user.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailSenderService {

	@Autowired
	JavaMailSender javaMailSender;

	public void sendMail(String message, String subject, String to, String cc) throws MessagingException {
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, true);
		mimeMessageHelper.setCc(cc);
		mimeMessageHelper.setText(message);
		mimeMessageHelper.setTo(to);
		javaMailSender.send(mailMessage);

	}
}
