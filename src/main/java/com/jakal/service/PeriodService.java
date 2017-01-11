package com.jakal.service;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.jakal.enums.Period;

public class PeriodService {
	private final int VOTING_START_DAY = 6;
	private final int VOTING_END_DAY = 11;
	private final int DISPLAY_START_DAY = 12;
	private final int DISPLAY_END_DAY = 15;

	public String getPeriodString(Calendar calendar) {
		return getPeriod(calendar).toString().toLowerCase();
	}

	public Period getPeriod(Calendar calendar) {
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
