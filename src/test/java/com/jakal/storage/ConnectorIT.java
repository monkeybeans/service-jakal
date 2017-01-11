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
	Connector connHandler;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
 		String propFile = "/Users/mac/development/service-jakal/src/main/config/run.properties";
		InputStream input = new FileInputStream(propFile);
		Properties prop = new Properties();
		prop.load(input);
		
		this.connHandler = new Connector(prop.getProperty("db.user"), prop.getProperty("db.password"));
		
		input.close();
	}
	
	@After
	public void takeDown() {
		this.connHandler = null;
	}

	@Test
	public void initialiseConnection() {
		Connection conn = connHandler.getConnection();
		
		assertNotNull(conn);
	}
	
	@Test
	public void useConnection() throws SQLException {
		Connection conn = connHandler.getConnection();
		
		Statement stm = conn.createStatement();
		stm.execute("show tables");
		ResultSet rs = stm.getResultSet();
		
		
		assertEquals(rs.next(), true);
	}

}
