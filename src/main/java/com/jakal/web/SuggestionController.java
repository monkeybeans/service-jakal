package com.jakal.web;

import javax.servlet.http.HttpServletRequest;

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
import com.jakal.web.helpers.RequestHelper;

@RestController
@RequestMapping("/api/v1/suggestions")
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
	public DynamicsTemplate voteOnSuggestion(@PathVariable int id, HttpServletRequest request) {
		log.info("Suggestion was voted on id: " + id + " by: " + RequestHelper.scrambleIp(request));

		suggestionDao.voteOnSuggestion(id);
		
		return DynamicsTemplate.build(suggestionDao);
	}

}
