package com.jakal.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jakal.models.Suggestion; 

@Repository
public class SuggestionDao extends JdbcDaoSupport{

	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SuggestionDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Suggestion> fetchFreshSuggestions() {
		String sql = 
				"select id, name, description, (select count(*) " +
				"from votes v where v.suggestion_id = s.id) numVotes " +
				"from suggestions  s where fresh = 1";
		
		RowMapper<Suggestion> mapper = new RowMapper<Suggestion>() {
			public Suggestion mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Suggestion(
						rs.getInt("id"), 
						rs.getString("name"),
						rs.getString("suggestion"),
						rs.getInt("numVotes"));
			}
		};
		
		return jdbcTemplate.query(sql, mapper);
	}
	
	public int voteOnSuggestion(int id) {
		String sql = "insert into votes (suggestion_id) values (?)";
		
		return jdbcTemplate.update(sql, id);
	}

	public List<Suggestion> getSuggestionWinners() {
		String sql = 
				"select id, name, description, (select count(*) " +
				"from votes v where v.suggestion_id = s.id) numVotes " +
				"from suggestions s where fresh = 1 and winner = 1 limit 12";

		RowMapper<Suggestion> mapper = new RowMapper<Suggestion>() {
			public Suggestion mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Suggestion(
						rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("description"),  
						rs.getInt("numVotes"));
			}
		};

		return jdbcTemplate.query(sql, mapper);
	}
	
	public int resetFreshSuggestions() {
		String sqlIds = "select id from suggestions where fresh = 1";
		List<Integer> ids = jdbcTemplate.queryForList(sqlIds, Integer.class);

		String questionMarkTemplate = ids.stream().map((i) -> "?").collect(Collectors.joining(","));
		String sqlReset = "update suggestions set fresh = 0 where id in (" + questionMarkTemplate + ")";

		return jdbcTemplate.update(sqlReset, ids);
	}

	public int addSuggestion(Suggestion suggestion) {
		String sql = "insert into suggestions (name, description, submitted) values (?, ?, now())";
		
		return jdbcTemplate.update(sql, suggestion.name, suggestion.description);
	}
}
