package com.jakal.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jakal.models.Suggestion;
import com.jakal.service.MailService;
import com.jakal.storage.Mapper;

@RestController
public class SuggestionController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	Mapper mapper;
	MailService mail;
	
	@Autowired
	public SuggestionController(Mapper mapper, MailService mail) {
		this.mapper = mapper;
		this.mail = mail;
	}

	@RequestMapping(path="/suggestions", method=RequestMethod.POST)
	public void createSuggestion(@RequestBody(required=false) Suggestion suggestion) {
		
		log.info("Recieved suggestion" + suggestion);
		if (suggestion != null) {
			log.info("Suggestion recieved: " + suggestion.name);
			mapper.addSuggestion(suggestion);
			mail.notifyNewSuggestion(suggestion.name);
		}
	}
	
	@RequestMapping(path="/suggestions/{id}/vote", method=RequestMethod.PUT) 
	public void voteOnSuggestion(@PathVariable int id) {
		log.info("Suggestion was voted on id: " + id);

		mapper.voteOnSuggestion(id);
	}

	@RequestMapping(path="/suggestions/{id}", method=RequestMethod.GET) 
	public Suggestion fetchSuggestion(@PathVariable int id) {
	
		return mapper.fetchSuggestion(id);
	}


	@RequestMapping(path="/suggestions/fresh", method=RequestMethod.GET) 
	public List<Suggestion> fetchFreshSuggestions() {
		
		return mapper.fetchFreshSuggestions();
	}
	
	@RequestMapping(path="/suggestions/winner", method=RequestMethod.GET) 
	public List<Suggestion> fetchWinnerSuggestions() {
		
		return mapper.fetchWinnerSuggestions();
	}

}
