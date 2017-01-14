package com.jakal.models;

import java.util.Calendar;

public class PeriodModel {
	public final int daysLeft;
	public final int daysElapsed;
	public final Current current;
	
	public PeriodModel(
			Calendar calendar,
			int startDaySuggest,
			int startDayVote,
			int startDayDisplay)
	{		
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		if (calcInPeriod(day, startDaySuggest, startDayVote, maxDays)) {
			current = PeriodModel.Current.SUGGEST;
			daysElapsed = calcElapsedDays(startDaySuggest, day, maxDays);
			daysLeft = calcDaysLeft(startDayVote, day, maxDays);
		} else if (calcInPeriod(day, startDayVote, startDayDisplay, maxDays)) {
			current = PeriodModel.Current.VOTE;
			daysElapsed = calcElapsedDays(startDayVote, day, maxDays);
			daysLeft = calcDaysLeft(startDayDisplay, day, maxDays);
		} else {
			current = PeriodModel.Current.DISPLAY;
			daysElapsed = calcElapsedDays(startDayDisplay, day, maxDays);
			daysLeft = calcDaysLeft(startDaySuggest, day, maxDays);
		}
	}
	
	private boolean calcInPeriod(int now, int start, int next, int maxTime) {
		if (next < start ) { next += maxTime; }
		
		if (now >= start && now < next) {
			return true;
		}
		
		return false;
	}

	private int calcElapsedDays(int start, int now, int maxTime) {
		return now - start >= 0 ? now - start : now - start + maxTime;
	}

	private int calcDaysLeft(int next, int now, int maxTime) {
		return next - now >= 0 ? next - now : next - now + maxTime;
	}
	
		
	public enum Current {
		VOTE,
		SUGGEST,
		DISPLAY;
	}

}
