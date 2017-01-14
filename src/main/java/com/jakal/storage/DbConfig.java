package com.jakal.storage;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfig extends DriverManagerDataSource{
	Logger log = LoggerFactory.getLogger(this.getClass());

	DataSource jakalDb;
	JdbcTemplate jdbcTemplate;
	
	//DataSource dataSource
	@Autowired
	public DbConfig(@Value("${db.user}") String username, @Value("${db.password}") String password) {
		super();
		log.info("setting up db connection");
		
    	super.setUsername(username);
    	super.setPassword(password);
    	super.setUrl("jdbc:mysql://localhost/jakal");
	}
}
