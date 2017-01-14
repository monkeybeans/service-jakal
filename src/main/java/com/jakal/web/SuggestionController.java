package com.jakal.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jakal.models.SuggestionModel;
import com.jakal.service.MailService;
import com.jakal.storage.ContactDao;
import com.jakal.storage.SuggestionDao;
import com.jakal.templates.DynamicsTemplate;

@RestController
@RequestMapping("/v1/suggestions")
public class SuggestionController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	MailService mail;
	SuggestionDao suggestionDao;
	ContactDao contactDao;
	
	@Autowired
	public SuggestionController(MailService mail, SuggestionDao suggestionDao, ContactDao contactDao) {
		this.mail = mail;
		this.suggestionDao = suggestionDao;
		this.contactDao = contactDao;
	}

	@RequestMapping(path="/fresh", method=RequestMethod.GET) 
	public DynamicsTemplate fetchFreshSuggestions() {
		
		return DynamicsTemplate.build(suggestionDao);
	}

	@RequestMapping(path="/{id}", method=RequestMethod.GET) 
	public DynamicsTemplate fetchSuggestion(@PathVariable int id) {
	
		//return suggestionDao.fetchSuggestion(id);
		throw new RuntimeException("Not implemented");
	}

	@RequestMapping(path="/winner", method=RequestMethod.GET) 
	public DynamicsTemplate fetchWinnerSuggestions() {
		
		//return suggestionDao.fetchWinnerSuggestions();
		throw new RuntimeException("Not implemented");
	}

	@RequestMapping(path="", method=RequestMethod.POST)
	public DynamicsTemplate createSuggestion(@RequestBody(required=false) SuggestionModel suggestion) {
		
		log.info("Recieved suggestion" + suggestion);
		if (suggestion != null) {
			log.info("Suggestion recieved: " + suggestion.name);
			suggestionDao.addSuggestion(suggestion);
			mail.notifyNewSuggestion(suggestion.name);
		}
		
		return DynamicsTemplate.build(suggestionDao);
	}
	
	@RequestMapping(path="/{id}/vote", method=RequestMethod.PUT) 
	public DynamicsTemplate voteOnSuggestion(@PathVariable int id) {
		log.info("Suggestion was voted on id: " + id);

		suggestionDao.voteOnSuggestion(id);
		
		return DynamicsTemplate.build(suggestionDao);
	}

}
