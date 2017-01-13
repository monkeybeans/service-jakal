package com.jakal.storage;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfig extends DriverManagerDataSource{
	@Value("${db.user}") String username; 
	@Value("${db.password}") String password;

	DataSource jakalDb;
	JdbcTemplate jdbcTemplate;
	
	//DataSource dataSource
	public DbConfig() {
		super();
		
    	this.setPassword(password);
    	this.setPassword(password);
    	this.setUrl("jdbc:mysql://localhost/jakal");
	}
}
