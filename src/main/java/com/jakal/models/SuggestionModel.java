package com.jakal.models;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuggestionModel {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public int id;
	public String name;
	public String description;
	public String submitter;
	public Date submitted;
	public int numVotes = 0;
	
	
	public SuggestionModel(int id, String name, String description, int numVotes) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.numVotes = numVotes;
	}
}
