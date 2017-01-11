package com.jakal.storage;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jakal.models.Suggestion;

@Component
public class Mapper {
	Logger log = LoggerFactory.getLogger(this.getClass());

	ApplicationContext context;
	Connector conn;
	
	@Autowired
	public Mapper(Connector conn) {
		this.conn = conn;
	}

	public void addSuggestion(Suggestion suggestion) {
		try {
			conn.addSuggestion(suggestion.name, suggestion.description);
		} catch (SQLException e) {
			System.err.println("MySQL message:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public Suggestion fetchSuggestion(int id) {
		try {
			return conn.getSuggestion(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Suggestion> fetchWinnerSuggestions() {
		final int NUM_ROWS_LIMIT = 50;

		try {
			return  conn.getSuggestionWinners(NUM_ROWS_LIMIT);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Suggestion> fetchFreshSuggestions() {
		try {
			return  conn.getFreshSuggestions();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void voteOnSuggestion(int id) {
		try {
			conn.addVoteToSuggestion(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
