package com.jakal.service;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.jakal.models.Period;

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
	
	public Period getPeriod() {
		return new Period(Calendar.getInstance(), startDaySuggest, startDayVote, startDayDisplay);
	}
	
	public String getPeriodString() {
		return getPeriod().current.toString().toLowerCase();
	}
}
