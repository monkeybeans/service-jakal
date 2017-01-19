package com.jakal.templates;

import com.jakal.models.PeriodModel;
import com.jakal.service.PeriodService;

public class ConfigTemplate {
	public String period;
	public int daysToNextPeriod;
	public int elapsedPeriodDays;


	private ConfigTemplate(String period, int timeToNewPeriod, int elapsedPeriodTime) {		super();
		this.period = period;
		this.daysToNextPeriod = timeToNewPeriod;
		this.elapsedPeriodDays = elapsedPeriodTime;
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
