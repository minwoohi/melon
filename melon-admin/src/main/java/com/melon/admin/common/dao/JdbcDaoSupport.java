package com.melon.admin.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JdbcDaoSupport {

	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "MELON";
	private final String PASS = "melon";
	
	public Object selectOne(){
		
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			String query = query();

			mappingParams(stmt);
			
			stmt = conn.prepareStatement(query);
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return bindData(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}
	
	public abstract String query();
	
	public abstract Object bindData(ResultSet rs) throws SQLException;
	
	public abstract void mappingParams(PreparedStatement stmt) throws SQLException ;
	
	private void loadOracleDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private void close(Connection conn, PreparedStatement stmt) {

		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {	}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {	}
		}
	}
	
}
