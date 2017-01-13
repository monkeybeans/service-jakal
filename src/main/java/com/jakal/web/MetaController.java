package com.jakal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jakal.service.PeriodService;
import com.jakal.storage.SuggestionDao;
import com.jakal.templates.ConfigTemplate;
import com.jakal.templates.DynamicsTemplate;
import com.jakal.templates.HistoryTemplate;

@RestController()
@RequestMapping("/v1/meta")
public class MetaController {
	PeriodService periodService;
	SuggestionDao suggestionDao;
	
	@Autowired
	public MetaController(PeriodService periodService, SuggestionDao suggestionDao) {
		this.periodService = periodService;
		this.suggestionDao = suggestionDao;
	}
	
	@RequestMapping(path="/config", method=RequestMethod.GET)
	@Autowired
	public ConfigTemplate fetchConfig() {
		return ConfigTemplate.build(periodService);
	}
	
	@RequestMapping(path="/dynamics", method=RequestMethod.GET) 
	public DynamicsTemplate fetchDynamics() {
		return DynamicsTemplate.build(suggestionDao);
	}
	
	@RequestMapping(path="/history", method=RequestMethod.GET) 
	public HistoryTemplate fetchHistory() {
		return new HistoryTemplate();
	}
}
