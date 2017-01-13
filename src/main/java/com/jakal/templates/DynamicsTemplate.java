package com.jakal.templates;

import java.util.List;

import com.jakal.models.Suggestion;
import com.jakal.storage.SuggestionDao;

public class DynamicsTemplate {
	public List<Suggestion> suggestions;
	
	private DynamicsTemplate(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	};

	public static DynamicsTemplate build(SuggestionDao suggestionDao) {
		
		return new DynamicsTemplate(
					suggestionDao.fetchFreshSuggestions()
				);
	}
}
