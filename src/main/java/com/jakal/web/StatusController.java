package com.jakal.web;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jakal.models.Status;
import com.jakal.service.PeriodService;

@RestController
@Configuration
public class StatusController {
	@Value("${spring.mail.password}")
	static String pass;
	
	@Autowired
	@RequestMapping("/status")
	public Status status(Status status) {
		return status;
	}
	
	@RequestMapping("/ping")
	public String ping() {
		return "pong";
	}

	@RequestMapping(path="/period", method=RequestMethod.GET)
	public String period(Status status) {
		return new PeriodService().getPeriodString(Calendar.getInstance());
	}

}
