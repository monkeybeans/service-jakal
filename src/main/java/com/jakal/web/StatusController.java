package com.jakal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jakal.service.PeriodService;

@RestController
@Configuration
public class StatusController {
	PeriodService periodService;
	
	@Autowired
	public StatusController(PeriodService periodService) {
		this.periodService = periodService;
	}
		
	@RequestMapping("/ping")
	public String ping() {
		return "pong";
	}
}
