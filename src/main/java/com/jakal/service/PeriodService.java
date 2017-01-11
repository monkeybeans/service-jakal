package com.jakal.service;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.jakal.enums.Period;

@Configuration
public class PeriodService {
	private final int VOTING_START_DAY = 6;
	private final int VOTING_END_DAY = 11;
	private final int DISPLAY_START_DAY = 12;
	private final int DISPLAY_END_DAY = 15;
	
	@Value("${override.period}")
	String overridePeriod;

	public String getPeriodString(Calendar calendar) {
		return getPeriod(calendar).toString().toLowerCase();
	}

	public Period getPeriod(Calendar calendar) {
		Pattern p = Pattern.compile("(vote|display|suggest)");
		Matcher m = p.matcher(overridePeriod);
		
		if (m.find()) {
			switch (m.group(1)) {
				case "vote": return Period.VOTE;
				case "display": return Period.DISPLAY;
				case "suggest": return Period.SUGGEST;
				default: throw new RuntimeException("Unknonw ovverride period: " + m.group(1));
			}
		}
		
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		if (day >= VOTING_START_DAY && day <= VOTING_END_DAY) {
			return Period.VOTE;
		} else if (day >= DISPLAY_START_DAY && day <= DISPLAY_END_DAY) {
			return Period.DISPLAY;
		} else {
			return Period.SUGGEST;
		}
	}
}
