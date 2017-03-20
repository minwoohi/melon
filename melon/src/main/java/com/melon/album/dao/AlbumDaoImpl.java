package com.melon.album.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melon.album.vo.AlbumSearchVO;
import com.melon.album.vo.AlbumVO;
import com.melon.artist.vo.ArtistVO;

public class AlbumDaoImpl implements AlbumDao{

	private final String DRIVER_URL = "oracle.jdbc.driver.OracleDriver";
	private final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String SCHEMA = "MELON";
	private final String PASSWORD = "melon";
	
	@Override
	public int insertNewAlbum(AlbumVO albumVO) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, SCHEMA, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" INSERT INTO ALBM (                                                                                ");
			query.append(" 				ALBM_ID                                                                              ");
			query.append(" 				, ARTST_ID                                                                           ");
			query.append(" 				, RLS_DT                                                                             ");
			query.append(" 				, PBLSHR                                                                             ");
			query.append(" 				, ENTMNT                                                                             ");
			query.append(" 				, GNR                                                                                ");
			query.append(" 				, PST                                                                                ");
			query.append(" 				, ALBM_NM                                                                                ");
			query.append(" 				)                                                                                    ");
			query.append(" VALUES (		'AL-' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24')||'-' || LPAD(ARTST_ID_SEQ.NEXTVAL, 6, '0') ");
			query.append(" 				, ?                                                                                  ");
			query.append(" 				, TO_DATE( ?, 'YYYY-MM-DD')                                                                                  ");
			query.append(" 				, ?                                                                                  ");
			query.append(" 				, ?                                                                                  ");
			query.append(" 				, ?                                                                                  ");
			query.append(" 				, ?                                                                                  ");
			query.append(" 				, ?                                                                                  ");
			query.append(" 		)                                                                                            ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, albumVO.getArtistId());
			stmt.setString(2, albumVO.getReleaseDate());
			stmt.setString(3, albumVO.getPublisher());
			stmt.setString(4, albumVO.getEntertainment());
			stmt.setString(5, albumVO.getGenre());
			stmt.setString(6, albumVO.getPost());
			stmt.setString(7, albumVO.getAlbumName());
			
			return stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {}
		}
		
	}

	private void loadOracleDriver() {
		try {
			Class.forName(DRIVER_URL);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public int selectAllAlbumsCount(AlbumSearchVO albumSearchVO) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, SCHEMA, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	COUNT(ALBM_ID) CNT ");
			query.append(" FROM		ALBM AL                   ");
			query.append(" 			, ARTST AR                   ");
			query.append(" WHERE	AL.ARTST_ID = AR.ARTST_ID                   ");
			query.append(" AND		AR.ARTST_ID = ?                   ");
			
			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, albumSearchVO.getArtistId());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return rs.getInt("CNT");
			}
			return 0;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			clostInstances(conn, stmt, rs);
		}
	}

	private void clostInstances(Connection conn, PreparedStatement stmt, ResultSet rs) {
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
	public List<AlbumVO> selectAllAlbums(AlbumSearchVO albumSearchVO) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, SCHEMA, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	*                                           ");
			query.append(" FROM	(                                               ");
			query.append(" 			SELECT	ROWNUM RNUM                         ");
			query.append(" 					, RST.*                               ");
			query.append(" 			FROM	(                                   ");
			query.append(" 								SELECT	AL.ALBM_ID         ");
			query.append(" 										, AL.ARTST_ID AL_ARTST_ID      ");                                                                      
			query.append(" 										, AL.RLS_DT        ");                                                                      
			query.append(" 										, AL.PBLSHR        ");                                                                      
			query.append(" 										, AL.ENTMNT        ");                                                                      
			query.append(" 										, AL.GNR           ");                                                                      
			query.append(" 										, AL.PST           ");
			query.append(" 										, AL.ALBM_NM           ");
			query.append(" 										, AR.ARTST_ID AR_ARTST_ID           ");
			query.append(" 										, AR.MBR           ");
			query.append(" 										, AR.DEBUT_DT           ");
			query.append(" 										, AR.DEBUT_TTL           ");
			query.append(" 								FROM	ALBM AL            ");
			query.append(" 										, ARTST AR            ");
			query.append("								WHERE	AL.ARTST_ID = AR.ARTST_ID ");
			query.append("								AND		AR.ARTST_ID = ? ");
			query.append(" 								ORDER	BY ALBM_ID DESC ");
			query.append(" 					) RST                                 ");
			query.append(" 			WHERE	ROWNUM <= ? )                        ");
			query.append(" WHERE	RNUM >= ?                                       ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, albumSearchVO.getArtistId());
			stmt.setInt(2, albumSearchVO.getPager().getEndArticleNumber());
			stmt.setInt(3, albumSearchVO.getPager().getStartArticleNumber());
			
			rs = stmt.executeQuery();
			
			List<AlbumVO> albumList = new ArrayList<AlbumVO>();
			AlbumVO album = null;
			
			while(rs.next()){
				album = new AlbumVO();
				
				album.setAlbumId(rs.getString("ALBM_ID"));
				album.setArtistId(rs.getString("AL_ARTST_ID"));
				album.setEntertainment(rs.getString("ENTMNT"));
				album.setReleaseDate(rs.getString("RLS_DT"));
				album.setGenre(rs.getString("GNR"));
				album.setPost(rs.getString("PST"));
				album.setPublisher(rs.getString("PBLSHR"));
				album.setAlbumName(rs.getString("ALBM_NM"));
				
				album.getArtistVO().setArtistId(rs.getString("AR_ARTST_ID"));
				album.getArtistVO().setDebutDate(rs.getString("DEBUT_DT"));
				album.getArtistVO().setDebutTitle(rs.getString("DEBUT_TTL"));
				album.getArtistVO().setMember(rs.getString("MBR"));
				
				albumList.add(album);
			}
			
			return albumList;
					
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			clostInstances(conn, stmt, rs);
		}
	}

	@Override
	public AlbumVO selectOneAlbum(String albumId) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, SCHEMA, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	AL.ALBM_ID         ");
			query.append("			, AL.ARTST_ID AL_ARTST_ID      ");                                                                      
			query.append("			, AL.RLS_DT        ");                                                                      
			query.append("			, AL.PBLSHR        ");                                                                      
			query.append("			, AL.ENTMNT        ");                                                                      
			query.append("			, AL.GNR           ");                                                                      
			query.append("			, AL.PST           ");
			query.append("			, AL.ALBM_NM           ");
			query.append("			, AR.ARTST_ID AR_ARTST_ID           ");
			query.append("			, AR.MBR           ");
			query.append("			, AR.DEBUT_DT           ");
			query.append("			, AR.DEBUT_TTL           ");
			query.append(" FROM		ALBM AL            ");
			query.append("			, ARTST AR            ");
			query.append(" WHERE	AL.ARTST_ID = AR.ARTST_ID ");
			query.append(" AND		AL.ALBM_ID = ? ");
			query.append(" ORDER	BY AL.ARTST_ID DESC");
			
			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, albumId);
			
			rs = stmt.executeQuery();
			
			AlbumVO album = null;
			
			if(rs.next()){
				album = new AlbumVO();
				
				album.setAlbumId(rs.getString("ALBM_ID"));
				album.setArtistId(rs.getString("AL_ARTST_ID"));
				album.setEntertainment(rs.getString("ENTMNT"));
				album.setGenre(rs.getString("GNR"));
				album.setPost(rs.getString("PST"));
				album.setPublisher(rs.getString("PBLSHR"));
				album.setReleaseDate(rs.getString("RLS_DT"));
				album.setAlbumName(rs.getString("ALBM_NM"));
				
				
				album.getArtistVO().setArtistId(rs.getString("AR_ARTST_ID"));
				album.getArtistVO().setDebutDate(rs.getString("DEBUT_DT"));
				album.getArtistVO().setDebutTitle(rs.getString("DEBUT_TTL"));
				album.getArtistVO().setMember(rs.getString("MBR"));
			}
			
			return album;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			clostInstances(conn, stmt, rs);
		}
	}

	@Override
	public int deleteOneAlbum(String albumId) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, SCHEMA, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" DELETE           ");
			query.append(" FROM		ALBM       ");
			query.append(" WHERE	ALBM_ID = ? ");
			
			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, albumId);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			clostInstances(conn, stmt, rs);
		}
	}
}
