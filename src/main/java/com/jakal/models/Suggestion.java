package com.jakal.models;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Suggestion {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static int numSuggestions = 0;
	public int id;
	public String name;
	public String description;
	public String submitter;
	public Date submitted;
	public int numVotes = 0;
	public boolean isWinner = false;
	public boolean isFresh = true;
	public boolean isTimeToVote = false;
	
	public Suggestion() {}
	
	public Suggestion(int id, String name, String description, int numVotes) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.numVotes = numVotes;
	}
}
