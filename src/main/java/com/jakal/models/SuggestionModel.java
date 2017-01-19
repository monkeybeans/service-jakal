package com.jakal.models;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SuggestionModel {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@JsonIgnoreProperties 
	public int id;
	public String name;
	public String description;
	@JsonIgnoreProperties 
	public String submitter;
	@JsonIgnoreProperties 
	public Date submitted;
	@JsonIgnoreProperties 
	public int numVotes = 0;

	public SuggestionModel() {
	}

	
	public SuggestionModel(int id, String name, String description, int numVotes) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.numVotes = numVotes;
	}
}
