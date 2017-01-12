package com.jakal.service;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jakal.models.Period;

public class PeriodServiceTest {
	Calendar calendar;
	
	@Before
	public void setup() {
		calendar = Calendar.getInstance();	
	}
	
	@After
	public void cleanUp() {
		calendar = null;
	}

	@Test
	public void calculatePeriodSuggest() {
		calendar.set(2017, 0, 1);
		Period period = new Period(calendar,1, 10, 20);
		
		assertEquals(0, period.daysElapsed);
		assertEquals(9, period.daysLeft);
		assertEquals(Period.Current.SUGGEST, period.current);
	}

	@Test
	public void calculatePeriodVote() {
		calendar.set(2017, 0, 12);
		Period period = new Period(calendar,1, 10, 20);
		
		assertEquals(2, period.daysElapsed);
		assertEquals(8, period.daysLeft);
		assertEquals(Period.Current.VOTE, period.current);
	}

	@Test
	public void calculatePeriodDisplay() {
		calendar.set(2017, 0, 30);
		Period period = new Period(calendar,1, 10, 20);
		
		assertEquals(10, period.daysElapsed);
		assertEquals(2, period.daysLeft);
		assertEquals(Period.Current.DISPLAY, period.current);
	}
	
	@Test
	public void calculatePeriodEdge() {
		calendar.set(2017, 0, 28);
		Period period = new Period(calendar,5, 19, 3);
		
		assertEquals(9, period.daysElapsed);
		assertEquals(6, period.daysLeft);
		assertEquals(Period.Current.VOTE, period.current);
	}

}
