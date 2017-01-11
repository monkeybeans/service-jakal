package com.jakal.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class Suggestion {
	Connector conn;
	
	@Autowired
	public Suggestion(Connector conn) {
		this.conn = conn;
	}

	public void resetFreshSuggestions() {
		List<Integer> freshIds = new ArrayList<Integer>();
		Connection handler = conn.getConnection();
		Statement stm = null;
		PreparedStatement ptm = null;

		try {
			
			stm = handler.createStatement();
			stm.execute("select id from suggestions where fresh = 1");
			
			ResultSet rs = stm.getResultSet();
			while(rs.next()) {
				freshIds.add(rs.getInt(1));
			}
			
			String questionMarkTemplate = freshIds.stream().map((i) -> "?").collect(Collectors.joining(","));
			ptm = handler.prepareStatement("update suggestions set fresh = 0 where id in (" +
			questionMarkTemplate
			+ ")");
			ptm.equals(freshIds);
			
			
		} catch (SQLException e) {
			System.out.println("Could not fetch addresses: " + e.getMessage());
		} finally {
			//@TODO: close the ptm also...
			conn.close(handler, stm);			
		}
	}

}
