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


	private ConfigTemplate(String period, int timeToNewPeriod, int elapsedPeriodTime) {		super();
		this.period = period;
		this.timeToNewPeriod = timeToNewPeriod;
		this.elapsedPeriodTime = elapsedPeriodTime;
	}

	public static ConfigTemplate build(PeriodService periodService) {
		PeriodModel period = periodService.getPeriod();
		
		return new ConfigTemplate(
					period.current.name().toLowerCase(),
					period.daysLeft,
					period.daysElapsed
				);
	}

}
