package com.melon.admin.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.melon.admin.common.dao.JdbcDaoSupport;
import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public class UserDaoImpl implements UserDao{

	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "MELON";
	private final String PASS = "melon";
	
	@Override
	public int insertNewUser(UserVO newUserVO) {
		loadOracleDriver();
		
		Connection conn  = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" INSERT	INTO	USR (                                                 ");
			query.append(" 						USR_ID                                            ");
			query.append(" 						, USR_NM                                          ");
			query.append(" 						, USR_PWD                                         ");
			query.append(" 						, USR_PNT                                         ");
			query.append(" 						, ATHRZTN_ID                                      ");
			query.append(" 					)                                                     ");
			query.append(" VALUES				(                                                 ");
			query.append(" 						'US-' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24')||'-' || ");
			query.append(" 						LPAD(USR_ID_SEQ.NEXTVAL, 6, '0')                ");
			query.append(" 						, ?                                               ");
			query.append(" 						, ?                                               ");
			query.append(" 						, ?                                               ");
			query.append(" 						, ?                                               ");
			query.append(" 					)                                                     ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, newUserVO.getUserName());
			stmt.setString(2, newUserVO.getUserPassword());
			stmt.setInt(3, newUserVO.getUserPoint());
			stmt.setString(4, newUserVO.getAuthorizationId());
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			close(conn, stmt);
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

	private void loadOracleDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public int selectAllUserCount(UserSearchVO userSearch) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	COUNT(1) CNT          ");
			query.append(" FROM		USR U                    ");
			query.append(" 			, ATHRZTN A                    ");
			query.append(" WHERE	U.ATHRZTN_ID = A.ATHRZTN_ID(+) ");
			
			
			stmt = conn.prepareStatement(query.toString());
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("CNT");
			}
					
			return 0;
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				if(rs != null){
					try {
						rs.close();
					} catch (SQLException e) {	}
				}
				close(conn, stmt);
		}
	}
	
	@Override
	public List<UserVO> selectAllUser(UserSearchVO userSearch) {
		loadOracleDriver();
		
		Connection conn  = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	*                                                  ");
			query.append(" FROM	(                                                      ");
			query.append(" 			SELECT	ROWNUM RNUM                                ");
			query.append(" 					, RST.*                                    ");
			query.append(" 			FROM	(                                          ");
			query.append(" 						SELECT	U.USR_ID                       ");
			query.append(" 								, U.USR_NM                     ");
			query.append(" 								, U.USR_PWD                    ");
			query.append(" 								, U.USR_PNT                    ");
			query.append(" 								, U.ATHRZTN_ID U_ATHRZTN_ID    ");
			query.append(" 								, A.ATHRZTN_ID                 ");
			query.append(" 								, A.ATHRZTN_NM                 ");
			query.append(" 								, A.PRNT_ATHRZTN_ID            ");
			query.append(" 						FROM	USR U                          ");
			query.append(" 								, ATHRZTN A                    ");
			query.append(" 						WHERE	U.ATHRZTN_ID = A.ATHRZTN_ID(+) ");
			query.append(" 					) RST                                      ");
			query.append(" 			WHERE	ROWNUM <= ?                                ");
			query.append(" 		)                                                      ");
			query.append(" WHERE	RNUM >= ?                                          ");
			
			List<UserVO> userList = new ArrayList<UserVO>();
			UserVO user = null;
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setInt(1, userSearch.getPager().getEndArticleNumber());
			stmt.setInt(2, userSearch.getPager().getStartArticleNumber());
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				user = new UserVO();
				
				user.setIndex(rs.getInt("RNUM"));
				user.setUserId(rs.getString("USR_ID"));
				user.setUserName(rs.getString("USR_NM"));
				user.setUserPassword(rs.getString("USR_PWD"));
				user.setUserPoint(rs.getInt("USR_PNT"));
				user.setAuthorizationId(rs.getString("U_ATHRZTN_ID"));
				
				user.getAuthorizationVO().setAuthorizationId(rs.getString("ATHRZTN_ID"));
				user.getAuthorizationVO().setAuthorizationName(rs.getString("ATHRZTN_NM"));
				user.getAuthorizationVO().setParentAuthorizationId(rs.getString("PRNT_ATHRZTN_ID"));
				
				userList.add(user);
				
			}
			
			return userList;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {}
				close(conn, stmt);
			}
		}
	}

	@Override
	public UserVO selectOneUser(String userId) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" 						SELECT	U.USR_ID                       ");
			query.append(" 								, U.USR_NM                     ");
			query.append(" 								, U.USR_PWD                    ");
			query.append(" 								, U.USR_PNT                    ");
			query.append(" 								, U.ATHRZTN_ID U_ATHRZTN_ID    ");
			query.append(" 								, A.ATHRZTN_ID                 ");
			query.append(" 								, A.ATHRZTN_NM                 ");
			query.append(" 								, A.PRNT_ATHRZTN_ID            ");
			query.append(" 						FROM	USR U                          ");
			query.append(" 								, ATHRZTN A                    ");
			query.append("						WHERE	U.ATHRZTN_ID = A.ATHRZTN_ID(+) ");
			query.append(" 						AND		USR_ID = ?                     ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, userId);
			
			rs = stmt.executeQuery();
			
			UserVO user = null;
			
			if(rs.next()){
				user = new UserVO();
				
				user.setUserId(rs.getString("USR_ID"));
				user.setUserName(rs.getString("USR_NM"));
				user.setUserPassword(rs.getString("USR_PWD"));
				user.setUserPoint(rs.getInt("USR_PNT"));
				user.setAuthorizationId(rs.getString("ATHRZTN_ID"));
				
				user.getAuthorizationVO().setAuthorizationId(rs.getString("ATHRZTN_ID"));
				user.getAuthorizationVO().setAuthorizationName(rs.getString("ATHRZTN_NM"));
				user.getAuthorizationVO().setParentAuthorizationId(rs.getString("PRNT_ATHRZTN_ID"));
			}
			
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {	}
				close(conn, stmt);
			}
		}
	}

	@Override
	public UserVO selectOneUser(UserVO user) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" 						SELECT	U.USR_ID                       ");
			query.append(" 								, U.USR_NM                     ");
			query.append(" 								, U.USR_PWD                    ");
			query.append(" 								, U.USR_PNT                    ");
			query.append(" 								, U.ATHRZTN_ID U_ATHRZTN_ID    ");
			query.append(" 								, A.ATHRZTN_ID                 ");
			query.append(" 								, A.ATHRZTN_NM                 ");
			query.append(" 								, A.PRNT_ATHRZTN_ID            ");
			query.append(" 						FROM	USR U                          ");
			query.append(" 								, ATHRZTN A                    ");
			query.append("						WHERE	U.ATHRZTN_ID = A.ATHRZTN_ID(+) ");
			query.append("						AND		USR_ID = ?                     ");
			query.append("						AND		USR_PWD = ?                    ");
			
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getUserPassword());
			
			rs = stmt.executeQuery();
			
			UserVO loginUser = null;
			
			if(rs.next()){
				loginUser = new UserVO();
				
				loginUser.setUserId(rs.getString("USR_ID"));
				loginUser.setUserName(rs.getString("USR_NM"));
				loginUser.setUserPassword(rs.getString("USR_PWD"));
				loginUser.setUserPoint(rs.getInt("USR_PNT"));
				loginUser.setAuthorizationId(rs.getString("ATHRZTN_ID"));
				
				loginUser.setAuthorizationId(rs.getString("ATHRZTN_ID"));
				
				loginUser.getAuthorizationVO().setAuthorizationId(rs.getString("ATHRZTN_ID"));
				loginUser.getAuthorizationVO().setAuthorizationName(rs.getString("ATHRZTN_NM"));
				loginUser.getAuthorizationVO().setParentAuthorizationId(rs.getString("PRNT_ATHRZTN_ID"));
			}
			
			return loginUser;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {	}
				close(conn, stmt);
			}
		}
	}

	@Override
	public int updateUserInfo(UserVO user) {
		
		loadOracleDriver();
		
		Connection conn  = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" UPDATE	USR          ");
			query.append(" SET		USR_NM = ?   ");
			query.append(" 			, USR_PNT = ?    ");
			query.append(" 			, USR_PWD = ?    ");
			query.append(" 			, ATHRZTN_ID = ? ");
			query.append(" WHERE	USR_ID = ?	 ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, user.getUserName());
			stmt.setInt(2, user.getUserPoint());
			stmt.setString(3, user.getUserPassword());
			stmt.setString(4, user.getAuthorizationId());
			stmt.setString(5, user.getUserId());
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt);
		}
		
	}

	@Override
	public int deleteOneUser(String userId) {
		loadOracleDriver();
		
		Connection conn  = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" DELETE              ");
			query.append(" FROM	USR            ");
			query.append(" WHERE	USR_ID = ? ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, userId);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			close(conn, stmt);
		}
	}
	
	@Override
	public int updateAllAuth(String beforeAuthId, String afterAuthId) {
		
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" UPDATE	USR            ");
				query.append(" SET	ATHRZTN_ID = ? "); // 아니면 그냥
			if( beforeAuthId.equals("") ){  // 권한없음이면
				query.append(" WHERE	ATHRZTN_ID IS NULL ");
			} else {
				query.append(" WHERE	ATHRZTN_ID = ? "); // 아니면 그냥
			}
			
			System.out.println("query : " + query.toString());
			stmt = conn.prepareStatement(query.toString());
			
			if(afterAuthId.equals("")){
				stmt.setNull(1, Types.VARCHAR);
			}else {
				stmt.setString(1, afterAuthId);
			}
			
			if(!beforeAuthId.equals("")){ 
				stmt.setString(2, beforeAuthId);
			} else {
				
			}
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt);
		}
	}
}
