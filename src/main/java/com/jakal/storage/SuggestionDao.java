package com.jakal.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jakal.models.SuggestionModel; 

@Repository
public class SuggestionDao extends JdbcDaoSupport{
	
	@Autowired
	public SuggestionDao(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	public List<SuggestionModel> fetchFreshSuggestions() {
		JdbcTemplate jdbcTemplate = super.getJdbcTemplate();

		String sql = 
				"select id, name, description, (select count(*) " +
				"from votes v where v.suggestion_id = s.id) numVotes " +
				"from suggestions  s where fresh = 1";
		
		RowMapper<SuggestionModel> mapper = new RowMapper<SuggestionModel>() {
			public SuggestionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new SuggestionModel(
						rs.getInt("id"), 
						rs.getString("name"),
						rs.getString("description"),
						rs.getInt("numVotes"));
			}
		};
		
		return jdbcTemplate.query(sql, mapper);
	}
	
	public int voteOnSuggestion(int id, String voterId) {
		JdbcTemplate jdbcTemplate = super.getJdbcTemplate();

		String sql = "insert into votes (suggestion_id, voter_id, vote_date, round) values (?, ?, now(), ?)";
		
		return jdbcTemplate.update(sql, id, voterId, 1);
	}

	public List<SuggestionModel> getSuggestionWinners() {
		JdbcTemplate jdbcTemplate = super.getJdbcTemplate();

		String sql = 
				"select id, name, description, (select count(*) " +
				"from votes v where v.suggestion_id = s.id) numVotes " +
				"from suggestions s where fresh = 1 and winner = 1 limit 12";

		RowMapper<SuggestionModel> mapper = new RowMapper<SuggestionModel>() {
			public SuggestionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new SuggestionModel(
						rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("description"),  
						rs.getInt("numVotes"));
			}
		};

		return jdbcTemplate.query(sql, mapper);
	}
	
	public int resetFreshSuggestions() {
		JdbcTemplate jdbcTemplate = super.getJdbcTemplate();

		String sqlIds = "select id from suggestions where fresh = 1";
		List<Integer> ids = jdbcTemplate.queryForList(sqlIds, Integer.class);

		String questionMarkTemplate = ids.stream().map((i) -> "?").collect(Collectors.joining(","));
		String sqlReset = "update suggestions set fresh = 0 where id in (" + questionMarkTemplate + ")";

		return jdbcTemplate.update(sqlReset, ids);
	}

	public int addSuggestion(SuggestionModel suggestion) {
		JdbcTemplate jdbcTemplate = super.getJdbcTemplate();

		String sql = "insert into suggestions (name, description, submitted) values (?, ?, now())";
		
		return jdbcTemplate.update(sql, suggestion.name, suggestion.description);
	}
}
