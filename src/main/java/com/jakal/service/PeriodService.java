package com.jakal.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.jakal.models.PeriodModel;

@Repository
@Configuration
public class PeriodService {
	@Value("${override.period}")
	String overridePeriod;

	int startDaySuggest; 
	int startDayVote;
	int startDayDisplay;
	
	public PeriodService(
			@Value("${period.start.day.suggest}")
			int startDaySuggest, 
			@Value("${period.start.day.vote}")
			int startDayVote, 
			@Value("${period.start.day.display}")
			int startDayDisplay) {
		this.startDaySuggest = startDaySuggest; 
		this.startDayVote = startDayVote;
		this.startDayDisplay = startDayDisplay;
	}
	
	public PeriodModel getPeriod() {
		return new PeriodModel(Calendar.getInstance(), startDaySuggest, startDayVote, startDayDisplay);
	}
	
	public String getPeriodString() {
		return getPeriod().current.toString().toLowerCase();
	}
}
