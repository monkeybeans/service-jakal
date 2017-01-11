package com.jakal.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
public class MailConfig {
	@Value("${spring.mail.host}")
	private String host;
		
	@Value("${mail.username}")
	private String username;
	
	@Value("${mail.password}")
	private String password;
	
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPassword(password);
        javaMailSender.setUsername(username);
        
        Properties props = new Properties();
        
        props.put("mail.smtp.starttls.required", true);
        props.put("mail.smtp.auth",true);
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.port", 25);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.starttls.required", true);        
        props.put("mail.smpt.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.debug", true);
        
        javaMailSender.setJavaMailProperties(props);

        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
       
       return simpleMailMessage;
    }
}
