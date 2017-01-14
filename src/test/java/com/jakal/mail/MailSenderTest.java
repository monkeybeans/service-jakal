package com.jakal.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jakal.service.mail.NotifyByMail;

@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class MailSenderTest {
	@Autowired
	NotifyByMail notify;

	// @BeforeClass
	// public static void configureProperties() {
	// System.out.println("Configuring properties file");
	// }

	@Test
	public void sendMail() {

		String[] addresses = { "mber02@kth.se" };

		notify.sendMail("Jakal mail testing", "This is a test...", addresses);

	}

	// it send mail to addresses
	//

}
