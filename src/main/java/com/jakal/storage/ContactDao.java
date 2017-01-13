package com.jakal.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDao extends JdbcDaoSupport {
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ContactDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<String> getAllAddresses() {
		String sql = "select email from users where email is not null order by email limit 30";
		
		RowMapper<String> mapper = new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("email");
			}
		};
		
		return jdbcTemplate.query(sql, mapper);
	}
}