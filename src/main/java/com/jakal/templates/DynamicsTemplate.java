package com.jakal.templates;

import java.util.List;

import com.jakal.models.SuggestionModel;
import com.jakal.storage.SuggestionDao;

public class DynamicsTemplate {
	public List<SuggestionModel> suggestions;
	
	private DynamicsTemplate(List<SuggestionModel> suggestions) {
		this.suggestions = suggestions;
	};

	public static DynamicsTemplate build(SuggestionDao suggestionDao) {
		
		return new DynamicsTemplate(
					suggestionDao.fetchFreshSuggestions()
				);
	}
}
