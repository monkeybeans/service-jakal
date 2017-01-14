package com.jakal.templates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jakal.models.PeriodModel;
import com.jakal.models.SuggestionModel;
import com.jakal.service.PeriodService;

public class ConfigTemplate {
	public String period;
	public int timeToNewPeriod;
	public int elapsedPeriodTime;
	public SuggestionModel lastWinner;


	private ConfigTemplate(String period, int timeToNewPeriod, int elapsedPeriodTime, SuggestionModel lastWinner) {		super();
		this.period = period;
		this.timeToNewPeriod = timeToNewPeriod;
		this.elapsedPeriodTime = elapsedPeriodTime;
		this.lastWinner = lastWinner;
	}

	public static ConfigTemplate build(PeriodService periodService) {
		PeriodModel period = periodService.getPeriod();
		
		return new ConfigTemplate(
					period.current.name().toLowerCase(),
					period.daysLeft,
					period.daysElapsed,
					null
				);
	}

}
