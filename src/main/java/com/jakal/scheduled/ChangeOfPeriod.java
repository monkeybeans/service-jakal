package com.jakal.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jakal.models.Period;
import com.jakal.service.MailService;
import com.jakal.service.PeriodService;
import com.jakal.storage.Connector;
import com.jakal.storage.Suggestion;

@Component
public class ChangeOfPeriod {
	static int num = 0;
	Logger log = LoggerFactory.getLogger(this.getClass());

	Period handledPeriod = null;
	PeriodService periodService;
	MailService mailService;
	Connector conn;
	
	@Autowired
	ChangeOfPeriod(MailService mailService, Connector conn, PeriodService periodService) {
		this.periodService = periodService;
		this.mailService = mailService;
		this.conn = conn;
	}
	
	@Scheduled(cron = "0 0 5 * * ?")
	public void checkPeriodChange() {
		Period newPeriod = periodService.getPeriod();
		log.info("Checking period: " + newPeriod.current);
		
		if (newPeriod.daysElapsed == 0) {
			log.info("New period: " + newPeriod.current);

			mailService.notifyPeriod(newPeriod.current);
			
			if (newPeriod.current == Period.Current.SUGGEST) {
				new Suggestion(conn).resetFreshSuggestions();				
			}
			
			handledPeriod = newPeriod;
		}
		
	}
	
	@Scheduled(cron = "0 0 5 * * MON")
	public void annoyPersonsWhenThereIsFewSuggestions() {
		//@TODO: send mail when suggestion is low.
	}

}
