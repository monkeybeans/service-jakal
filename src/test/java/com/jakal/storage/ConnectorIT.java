package com.jakal.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectorIT {
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
 		String propFile = "/Users/mac/development/service-jakal/src/main/config/run.properties";
		InputStream input = new FileInputStream(propFile);
		Properties prop = new Properties();
		prop.load(input);
		
		prop.getProperty("db.user");
		prop.getProperty("db.password");
	}
	
	@After
	public void takeDown() {
	}

	@Test
	public void initialiseConnection() {
	}
	
	@Test
	public void useConnection() throws SQLException {
		//stm.execute("show tables");
		
		//assertEquals(rs.next(), true);
	}

}
