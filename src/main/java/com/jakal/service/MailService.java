package com.jakal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jakal.constants.StaticUrl;
import com.jakal.models.PeriodModel;
import com.jakal.service.mail.NotifyByMail;
import com.jakal.storage.ContactDao;

@Component
public class MailService {
	NotifyByMail notify;
	ContactDao dbContact;
	
	@Autowired
	public MailService(NotifyByMail notify, ContactDao dbContact) {
		this.notify = notify;
		this.dbContact = dbContact;
	}
	
	public void notifyNewSuggestion(String suggestion) {		
		new Thread() {
			List<String> addresses = dbContact.getAllAddresses();
			
			String subject = "New suggestion! " + suggestion;
			String body = "There is a new suggestion for " + suggestion + ". See "+ StaticUrl.HOME_URL +" for details.";

			public void run() {
				notify.sendMail(
						subject, 
						body, 
						addresses.toArray(new String[0]));				
			}
		}.start();
	}
	
	public void notifyPeriod(PeriodModel.Current period) {
		List<String> addresses = dbContact.getAllAddresses();

		String subject = "Notification, period changed...";
		String body = "Go to " + StaticUrl.HOME_URL + " see what is new.";
		
		switch (period) {
			case SUGGEST:
				subject = "Time to add your favourite share!!!";
				body = "Go to " + StaticUrl.HOME_URL + " and add some nice contribution.";
				break;
			case VOTE: 
				subject = "Time time to VOTE!!!";
				body = "Go to " + StaticUrl.HOME_URL + " and vote on the best option.";
				break;
			case DISPLAY: 
				subject = "Voting time over, and the winner is...";
				body = "Go to " + StaticUrl.HOME_URL + " and see who won.";
				break;
			default:
				System.out.println("[ERROR] Unknown notifyPeriod: " + period.toString());
				break;
		}
		
		notify.sendMail(
				subject, 
				body, 
				addresses.toArray(new String[0]));
	}

}
