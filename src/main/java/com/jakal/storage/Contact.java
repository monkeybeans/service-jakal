package com.jakal.storage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Contact {
	Connector conn;
	
	@Autowired
	public Contact(Connector conn) {
		this.conn = conn;
	}
	
	public List<String> getAllAddresses() {
		List<String> addresses = new ArrayList<String>();
		
		Connection handler = conn.getConnection();
		Statement stm = null;
		
		try {
			stm = handler.createStatement();
			stm.execute("select email from users where email is not null order by email limit 30");
			
			ResultSet rs = stm.getResultSet();
			String address;
			while(rs.next()) {
				address = rs.getString(1);
				addresses.add(address);					
			}
		} catch (SQLException e) {
			System.out.println("Could not fetch addresses: " + e.getMessage());
		} finally {
			conn.close(handler, stm);			
		}
		

		
		return addresses;
	}

}
