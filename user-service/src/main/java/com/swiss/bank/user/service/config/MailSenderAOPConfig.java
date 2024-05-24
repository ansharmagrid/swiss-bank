package com.swiss.bank.user.service.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiss.bank.user.service.util.MailSenderService;

@Service
@Aspect
public class MailSenderAOPConfig {
	
	@Autowired
	MailSenderService mailSenderService;
	
	@Before("@annotation(com.swiss.bank.user.service.annotation.MailAlert)")
    public void beforeMethodExecution(JoinPoint joinPoint) throws Exception {
//		mailSenderService.sendMail("message", "to", "d.anubhav.sharma@gmail.com", "d.anubhav.sharma@gmail.com");
    }

    @After("@annotation(com.swiss.bank.user.service.annotation.MailAlert)")
    public void afterMethodExecution(JoinPoint joinPoint) throws Exception{
//		mailSenderService.sendMail("message", "to", "d.anubhav.sharma@gmail.com", "d.anubhav.sharma@gmail.com");
    }
    

    @AfterThrowing("@annotation(com.swiss.bank.user.service.annotation.MailAlert)")
    public void afterErrorInMethodExecution(JoinPoint joinPoint) throws Exception{
//		mailSenderService.sendMail("message", "to", "d.anubhav.sharma@gmail.com", "d.anubhav.sharma@gmail.com");
    }
}
