package com.jakal.storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jakal.models.Suggestion;

@Component
public class Connector {
	private String connectionStr; 

	@Autowired
	public Connector(
			@Value("${db.user}") String dbUsername, 
			@Value("${db.password}") String dbPassword) {
		
		this.connectionStr = 
				"jdbc:mysql://localhost/jakal?" +
				"user=" + dbUsername + "&" +
				"password=" + dbPassword;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectionStr);
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("Could not establish a database connection");
			//prints username and password System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		
		return conn;
	}
	
	public void close(Connection conn, Statement stm) {
		try {
			if (stm != null) stm.close();
			if (conn != null) conn.close();			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());			
		}
	}
	
	public int getUserId(String username) throws SQLException {
		Connection conn = getConnection();
		int userId;
		PreparedStatement stm = conn.prepareStatement("select id from users where name = ?");
		stm.setString(1, username);
		ResultSet rs = stm.executeQuery();
				
		userId = rs.next() ? rs.getInt("id") : 0;
		
		close(conn, stm);
		return  userId;
	}
	

	public void addSuggestion(String name, String description) throws SQLException {
		Connection conn = getConnection();
		
		int userId = getUserId(null);
		Date now = new Date(Calendar.getInstance().getTime().getTime());
		PreparedStatement stm;
		
		if (userId > 0 ) {
			stm = conn.prepareStatement("insert into suggestions (name, description, submitted, user_id) values (?, ?, ?, ?)");
			stm.setString(1,name);
			stm.setString(2,description);
			stm.setDate(3, now);
			stm.setInt(4, userId);			
		} else {
			stm = conn.prepareStatement("insert into suggestions (name, description, submitted) values (?, ?, ?)");
			stm.setString(1,name);
			stm.setString(2,description);
			stm.setDate(3, now);
		}
		
		stm.executeUpdate();
		
		close(conn, stm);
	}
	

	public ResultSet getSuggestions() throws SQLException {
		return getSuggestions(null);
	}
	
	public ResultSet getSuggestions(Date fromDate) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stm = conn.prepareStatement("select name, description from suggestions where submitted < ?");
		stm.setDate(1, fromDate);
		
		return stm.executeQuery();
	}
	
	public Suggestion getSuggestion(int suggestionId) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stm = conn.prepareStatement("select name, description from suggestions where id = ?");
		stm.setInt(1, suggestionId);
		
		
		stm.executeQuery();

		close(conn, stm);

		throw new SQLException("Not implemented");
	}

	public List<Suggestion> getFreshSuggestions() throws SQLException {
		Connection conn = getConnection();
		ResultSet rs;
		Statement stm = conn.createStatement();
		List<Suggestion> suggestions = new ArrayList<Suggestion>();

		rs = stm.executeQuery("select id, name, description, (select count(*) from votes v where v.suggestion_id = s.id) numVotes " +
								"from suggestions  s where fresh = 1");
		
		while (rs.next()) {
			suggestions.add(new Suggestion(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
					rs.getInt("numVotes")));
		}

		close(conn, stm);

		return suggestions;
	}
	
	public void addVoteToSuggestion(int id) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stm = conn.prepareStatement("insert into votes (suggestion_id) values (?)");
		stm.setInt(1, id);
		
		stm.executeUpdate();

		close(conn, stm);
	}

	public List<Suggestion> getSuggestionWinners(int limit) throws SQLException {
		Connection conn = getConnection();
		ResultSet rs;
		PreparedStatement stm = conn.prepareStatement("select name from suggestions where winner = 1 limit ?");
		stm.setInt(1, limit);
		rs = stm.executeQuery();

		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		while (rs.next()) {
			suggestions.add(new Suggestion(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
					rs.getInt("numVotes")));
		}

		close(conn, stm);

		return suggestions;
	}
	
}
