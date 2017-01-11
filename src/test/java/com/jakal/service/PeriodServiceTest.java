package com.jakal.service;

import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

import com.jakal.enums.Period;

public class PeriodServiceTest {

	@Test
	public void calculatePeriod() {
		
		int dayNum = 1;
		Calendar calendar = Calendar.getInstance();

		PeriodService service = new PeriodService();
		Period period;
		
		for (int i = 1; i <= 31; i++) {
			calendar.set(2017, 01, dayNum);
			period = service.getPeriod(calendar);
			
			
		}
		
		fail("Not yet implemented");
	}

}
