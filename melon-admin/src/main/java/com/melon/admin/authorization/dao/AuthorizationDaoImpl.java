package com.melon.admin.authorization.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.authorization.vo.AuthorizationVO;
import com.melon.admin.common.dao.JdbcDaoSupport;

public class AuthorizationDaoImpl implements AuthorizationDao {

	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "MELON";
	private final String PASS = "melon";
	
	@Override
	public String generateNewAuthorizationId() {
		
		JdbcDaoSupport dao = new JdbcDaoSupport(){

			@Override
			public String query() {
				
				StringBuffer query = new StringBuffer();
				query.append(" SELECT	'AT-' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24')||'-' || LPAD(ATHRZTN_ID_SEQ.NEXTVAL, 6, '0') SEQ ");
				query.append(" FROM	DUAL                                                                                           ");
				
				return query.toString();
			}

			@Override
			public Object bindData(ResultSet rs) throws SQLException {
				return rs.getString("SEQ");
			}

			@Override
			public void mappingParams(PreparedStatement stmt) throws SQLException {
			}
			
		};
		
		return (String) dao.selectOne();
	}
	
	@Override
	public int insertNewAuthorization(AuthorizationVO authorization) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" INSERT	INTO	ATHRZTN	(                                                 ");
			query.append(" 							ATHRZTN_ID                                        ");
			query.append(" 							, ATHRZTN_NM                                      ");
			query.append(" 							, PRNT_ATHRZTN_ID                                 ");
			query.append(" 						)                                                     ");
			query.append(" VALUES					(                                                 ");
			query.append(" 							?                                                 ");
			query.append(" 							, ?                                               ");
			query.append(" 							, ?                                               ");
			query.append(" 						)                                                     ");
			
			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, authorization.getAuthorizationId());
			stmt.setString(2, authorization.getAuthorizationName());
			stmt.setString(3, authorization.getParentAuthorizationId());
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt);
		}
	}

	@Override
	public int selectAllAuthorizationCount(AuthorizationSearchVO authorizationSearch) {
		
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	COUNT(1) CNT ");
			query.append(" FROM	ATHRZTN          ");
			
			stmt = conn.prepareStatement(query.toString());
			
			rs = stmt.executeQuery();
			
			AuthorizationVO autho = null;
			
			if(rs.next()){
				return rs.getInt("CNT");
			}
			
			return 0;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			close(conn, stmt);
		}
	}

	@Override
	public List<AuthorizationVO> selectAllAuthorization(AuthorizationSearchVO authorizationSearch) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	*                                          ");
			query.append(" FROM	(                                              ");
			query.append(" 			SELECT	ROWNUM AS RNUM                     ");
			query.append(" 					, RST.*                            ");
			query.append(" 			FROM	(                                  ");
			query.append(" 						SELECT	ATHRZTN_ID             ");
			query.append(" 								, ATHRZTN_NM           ");
			query.append(" 								, PRNT_ATHRZTN_ID      ");
			query.append(" 						FROM	ATHRZTN                ");
			query.append(" 						ORDER	BY	ATHRZTN_ID DESC    ");
			query.append(" 					) RST                              ");
			query.append(" 			WHERE	ROWNUM <= ?                        ");
			query.append(" 		)                                              ");
			query.append(" WHERE	RNUM >= ?                                  ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setInt(1, authorizationSearch.getPager().getEndArticleNumber());
			stmt.setInt(2, authorizationSearch.getPager().getStartArticleNumber());
			
			rs = stmt.executeQuery();
			
			List<AuthorizationVO> authoList = new ArrayList<AuthorizationVO>();
			AuthorizationVO autho = null;
			
			while(rs.next()){
				autho = new AuthorizationVO();
				
				autho.setAuthorizationId(rs.getString("ATHRZTN_ID"));
				autho.setAuthorizationName(rs.getString("ATHRZTN_NM"));
				autho.setParentAuthorizationId(rs.getString("PRNT_ATHRZTN_ID"));
				
				authoList.add(autho);
			}
			
			return authoList;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			close(conn, stmt);
		}
	}

	@Override
	public int updateAuthorizationInfo(AuthorizationVO authorization) {
		loadOracleDriver();
		
		Connection conn  = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" UPDATE	ATHRZTN               ");
			query.append(" SET		ATHRZTN_NM = ?        ");
			query.append(" 			, PRNT_ATHRZTN_ID = ? ");
			query.append(" WHERE	ATHRZTN_ID = ?	      ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, authorization.getAuthorizationName());
			stmt.setString(2, authorization.getParentAuthorizationId());
			stmt.setString(3, authorization.getAuthorizationId());
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt);
		}
	}

	@Override
	public int deleteOneAuthorization(String authorizationId) {
		loadOracleDriver();
		
		Connection conn  = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" DELETE	               ");
			query.append(" FROM		ATHRZTN        ");
			query.append(" WHERE	ATHRZTN_ID = ? ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, authorizationId);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt);
		}
	}

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
