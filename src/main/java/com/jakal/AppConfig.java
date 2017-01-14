package com.jakal;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class AppConfig {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	@Autowired
	public DataSource conifgureDatabase(
			@Value("${db.user}") String username, 
			@Value("${db.password}") String password) 
	{
		log.info("configuring settings for database");
		DriverManagerDataSource manager = new DriverManagerDataSource();
    	manager.setUsername(username);
    	manager.setPassword(password);
    	manager.setUrl("jdbc:mysql://localhost/jakal");
    	
    	return manager;

	}
	
    @Bean
    @Autowired
    public JavaMailSender configureJavaMailService(
    		@Value("${mail.host}") String host,
    		@Value("${mail.username}") String username,
    		@Value("${mail.password}") String password) 
    {
    	log.info("configuring java mail sender service");
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        
        javaMailSender.setHost(host);
        javaMailSender.setPassword(password);
        javaMailSender.setUsername(username);
        
        javaMailSender.setPort(465);
        javaMailSender.setProtocol("smtps");
                
        Properties props = new Properties();
        props.put("mail.debug", true);

        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.starttls.required", true);

        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.auth",true);
        
        javaMailSender.setJavaMailProperties(props);

        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage configureSimpleMailMessage() {
    	log.info("setting up mail message");
    	SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
       
    	return simpleMailMessage;
    }

}
