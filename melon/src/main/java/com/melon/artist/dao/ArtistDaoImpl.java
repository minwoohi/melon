package com.melon.artist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melon.artist.vo.ArtistSearchVO;
import com.melon.artist.vo.ArtistVO;

public class ArtistDaoImpl implements ArtistDao {
	
	private final String DRIVER_URL = "oracle.jdbc.driver.OracleDriver";
	private final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "MELON";
	private final String PASSWORD = "melon";
	
	@Override
	public int insertNewArtist(ArtistVO artist) {
		
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" INSERT INTO ARTST ( "                 );
			query.append("   				ARTST_ID "           );
			query.append( "					, MBR "          );
			query.append( "					, DEBUT_DT "     );
			query.append( "					, DEBUT_TTL ) " );
			query.append(" VALUES ( "                            );
			query.append( "			'AR-' || "         );
			query.append( "				TO_CHAR(SYSDATE, 'YYYYMMDDHH24')||'-' ");
			query.append( "			|| LPAD(ARTST_ID_SEQ.NEXTVAL, 6, '0') "  );
			query.append( "			, ? "            );
			query.append( "			, TO_DATE(?, 'YYYY-MM-DD') "          );
			query.append( "			, ? " );
			query.append( "		  )			");
			
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, artist.getMember());
			stmt.setString(2, artist.getDebutDate());
			stmt.setString(3, artist.getDebutTitle());
			
			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
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

	private void loadOracleDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<ArtistVO> selectAllArtists(ArtistSearchVO artistSearchVO) {
		try {
			Class.forName(DRIVER_URL);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT	* ");
			query.append(" FROM	( ");
			query.append(" 			SELECT	ROWNUM RNUM ");
			query.append(" 					, A.* ");
			query.append(" 			FROM	( ");
			
			query.append("			 			SELECT 	ARTST_ID "   );
			query.append("		  						, MBR "       );
			query.append("					  			, DEBUT_DT "  );
			query.append("		  						, DEBUT_TTL "); 
			query.append(" 						FROM	ARTST "      );
			query.append(" 						ORDER  BY ARTST_ID DESC ");
			
			query.append("					) A ");
			query.append(" 			WHERE	ROWNUM <= ? ");
			query.append("		 ) ");
			query.append(" WHERE RNUM >= ? ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setInt(1, artistSearchVO.getPager().getEndArticleNumber());
			stmt.setInt(2, artistSearchVO.getPager().getStartArticleNumber());
			rs = stmt.executeQuery();
			
			List<ArtistVO> artistList = new ArrayList<ArtistVO>();
			ArtistVO artist = null;
			
			while(rs.next()){
				
				artist = new ArtistVO();
				
				artist.setArtistId(rs.getString("ARTST_ID"));
				artist.setMember(rs.getString("MBR"));
				String debutDate = rs.getString("DEBUT_DT").substring(0, 10);
				artist.setDebutDate(debutDate);
				artist.setDebutTitle(rs.getString("DEBUT_TTL"));

				artistList.add(artist);
				
			}
			
			return artistList;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	@Override
	public ArtistVO selectOneArtist(String artistId) {
		try {
			Class.forName(DRIVER_URL);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
				query.append("							SELECT	ARTST_ID         ");
				query.append("									, MBR            ");
				query.append("									, DEBUT_DT       ");
				query.append("									, DEBUT_TTL      ");
				query.append("							FROM	ARTST            ");
				query.append("							WHERE	ARTST_ID = ?     ");
			
				stmt = conn.prepareStatement(query.toString());
				
				stmt.setString(1, artistId);

				ArtistVO artist = null;
				
				if(rs.next()){
					artist = new ArtistVO();
					
					artist.setArtistId(rs.getString("ARTST_ID"));
					artist.setMember(rs.getString("MBR"));
					artist.setDebutDate(rs.getString("DEBUT_DT"));
					artist.setDebutTitle(rs.getString("DEBUT_TTL"));
				}
				
				return artist;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			closeInstances(conn, stmt, rs);
		}
	}

	@Override
	public int deleteOneArtist(String artistId) {
		try {
			Class.forName(DRIVER_URL);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
				query.append(" DELETE               " );
				query.append(" FROM		ARTST           " );
				query.append(" WHERE	ARTST_ID = ?" );
			
				stmt = conn.prepareStatement(query.toString());
				
				stmt.setString(1, artistId);
				
				return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			closeInstances(conn, stmt, rs);
		}
	}

	private void closeInstances(Connection conn, PreparedStatement stmt, ResultSet rs) {
		if(rs != null)
			try {
				rs.close();
			} catch (SQLException e) {}
		if(stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {}
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {}
	}

	@Override
	public int selectAllArtistsCount(ArtistSearchVO artistSearchVO) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	COUNT(ARTST_ID) CNT ");
			query.append(" FROM		ARTST A ");
						
			stmt = conn.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return rs.getInt("CNT");
			}
			return 0;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			closeInstances(conn, stmt, rs);
		}
	}
	
}
