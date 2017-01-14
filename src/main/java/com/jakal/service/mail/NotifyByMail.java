package com.jakal.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotifyByMail {
	Logger log = LoggerFactory.getLogger(this.getClass());

	private JavaMailSender mailSender;
    private SimpleMailMessage templateMessage;
    private String mailEnabled;
	
	@Autowired
	public NotifyByMail(
			JavaMailSender mailSender, 
			SimpleMailMessage templateMessage, 
			@Value("${mail.enabled}") String mailEnabled) {
				
		this.mailSender = mailSender;
		this.templateMessage = templateMessage;
		this.mailEnabled = mailEnabled;
	}

    public void sendMail(String subject, String text, String[] addresses) {
    	
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(addresses);
        msg.setSubject("[jakal] " + subject);
        msg.setText(text);
      
        try{
        	if ("true".equals(mailEnabled)) {
        		log.info("sent mail " + msg.getSubject() + " to: " + String.join(",", msg.getTo()));
                this.mailSender.send(msg);        		
        	} else {
        		log.info(
        					"Mail sending disabled(mail.enabled=" + mailEnabled +") but would have sent:\n" + 
        					"<<to>> " + String.join(", ", msg.getTo()) + "<<to>>\n" +
        					"<<subject>> " + msg.getSubject() + "<<subject>>\n" +
        					"<<body>> " + msg.getText() + "<<body>>"
        				);
        	}
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
            throw ex;
        }
    }
}
