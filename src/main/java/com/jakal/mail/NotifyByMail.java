package com.jakal.mail;

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
			@Value("${enable.mail") String mailEnabled) {
				
		this.mailSender = mailSender;
		this.templateMessage = templateMessage;
		this.mailEnabled = mailEnabled;
	}

//    public void setMailSender(JavaMailSenderImpl mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void setTemplateMessage(SimpleMailMessage templateMessage) {
//        this.templateMessage = templateMessage;
//    }

    public void sendMail(String subject, String text, String[] addresses) {
    	
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(addresses);
        msg.setSubject("[jakal] " + subject);
        msg.setText(text);
      
        try{
        	if ("yes".equals(mailEnabled)) {
                this.mailSender.send(msg);        		
        	} else {
        		log.info("Mail sending disablesd but would have sent: ", 
        				msg.getSubject(), 
        				msg.getText(), 
        				" to ", 
        				msg.getTo());
        	}
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
            throw ex;
        }
    }
}
