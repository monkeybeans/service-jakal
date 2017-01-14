package com.jakal.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jakal.models.PeriodModel;
import com.jakal.service.MailService;
import com.jakal.service.PeriodService;
import com.jakal.storage.SuggestionDao;

@Component
public class ChangeOfPeriod {
	static int num = 0;
	Logger log = LoggerFactory.getLogger(this.getClass());

	PeriodService periodService;
	MailService mailService;
	SuggestionDao suggestionDao;
	
	@Autowired
	ChangeOfPeriod(MailService mailService, SuggestionDao suggestionDao, PeriodService periodService) {
		this.periodService = periodService;
		this.mailService = mailService;
		this.suggestionDao = suggestionDao;
	}
	
	@Scheduled(cron = "0 0 5 * * ?")
	public void checkPeriodChange() {
		PeriodModel newPeriod = periodService.getPeriod();
		log.info("Checking period: " + newPeriod.current);
		
		if (newPeriod.daysElapsed == 0) {
			log.info("New period: " + newPeriod.current);

			mailService.notifyPeriod(newPeriod.current);
			
			if (newPeriod.current == PeriodModel.Current.SUGGEST) {
				suggestionDao.resetFreshSuggestions();				
			}
		}
		
	}
	
	@Scheduled(cron = "0 0 5 * * MON")
	public void annoyPersonsWhenThereIsFewSuggestions() {
		//@TODO: send mail when suggestion is low.
	}

}
