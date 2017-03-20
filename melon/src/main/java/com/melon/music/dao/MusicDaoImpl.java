package com.melon.music.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melon.music.vo.MusicSearchVO;
import com.melon.music.vo.MusicVO;

public class MusicDaoImpl implements MusicDao{

	private final String DRIVER_URL = "oracle.jdbc.driver.OracleDriver";
	private final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "MELON";
	private final String PASSWORD = "melon";
	
	@Override
	public int insertNewMusic(MusicVO music) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" INSERT INTO		MSC (                                                                        ");
			query.append("						MSC_ID                                                                   ");
			query.append("						, ALBM_ID                                                                ");
			query.append("						, TTL                                                                    ");
			query.append("						, MP3_FL                                                                 ");
			query.append("						, MSCN                                                                   ");
			query.append("						, DRTR                                                                   ");
			query.append("						, LRCS                                                                   ");
			query.append("					)                                                                            ");
			query.append(" VALUES				(                                                                        ");
			query.append("						'MS-' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24')||'-' || LPAD(MSC_ID_SEQ.NEXTVAL, 6, '0')");
			query.append("						, ?                                                                      ");
			query.append("						, ?                                                                      ");
			query.append("						, ?                                                                      ");
			query.append("						, ?                                                                      ");
			query.append("						, ?                                                                      ");
			query.append("						, ?                                                                      ");
			query.append("					)                                                                            ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, music.getAlbumId());
			stmt.setString(2, music.getTitle());
			stmt.setString(3, music.getMp3File());
			stmt.setString(4, music.getMusician());
			stmt.setString(5, music.getDirector());
			stmt.setString(6, music.getLyrics());
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			closeInstances(conn, stmt);
		}
		
	}

	private void closeInstances(Connection conn, PreparedStatement stmt) {
		if(stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {	}
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {	}
	}

	private void loadOracleDriver() {
		try {
			Class.forName(DRIVER_URL);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public int selectAllMusicCount(MusicSearchVO musicSearchVO) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	COUNT(1) CNT          ");
			query.append(" FROM		ALBM AL                    ");
			query.append(" 			, ARTST AR                 ");
			query.append(" 			, MSC M                    ");
			query.append(" WHERE	AL.ARTST_ID = AR.ARTST_ID  ");
			query.append(" AND		M.ALBM_ID = AL.ALBM_ID     ");
			query.append(" AND		AL.ALBM_ID = ?             ");
			query.append(" AND		AR.ARTST_ID = ?            ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, musicSearchVO.getAlbumId());
			stmt.setString(2, musicSearchVO.getArtistId());
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("CNT");
			}
					
			return 0;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {	}
			closeInstances(conn, stmt);
		}
	}

	@Override
	public List<MusicVO> selectAllMusic(MusicSearchVO musicSearchVO) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	*                                                                 ");
			query.append(" FROM	(                                                                     ");
			query.append(" 			SELECT	ROWNUM RNUM                                               ");
			query.append(" 					, RST.*                                                   ");
			query.append(" 			FROM	(                                                         ");
			query.append(" 						SELECT	M.MSC_ID                                      ");
			query.append(" 								, M.ALBM_ID M_ALBM_ID                         ");
			query.append(" 								, M.TTL                                       ");
			query.append(" 								, M.LK_CNT                                    ");
			query.append(" 								, M.MP3_FL                                    ");
			query.append(" 								, M.MSCN                                      ");
			query.append(" 								, M.DRTR                                      ");
			query.append(" 								, M.LRCS                                      ");
			query.append(" 								, AL.ALBM_ID AL_ALBM_ID                       ");
			query.append(" 								, AL.ARTST_ID AL_ARTST_ID                     ");
			query.append(" 								, AL.RLS_DT                                   ");
			query.append(" 								, AL.PBLSHR                                   ");
			query.append(" 								, AL.ENTMNT                                   ");
			query.append(" 								, AL.GNR                                      ");
			query.append(" 								, AL.PST                                      ");
			query.append(" 								, AL.ALBM_NM                                  ");
			query.append(" 								, AR.ARTST_ID AR_ARTST_ID                     ");
			query.append(" 								, AR.MBR                                      ");
			query.append(" 								, TO_CHAR(AR.DEBUT_DT, 'YYYY-MM-DD') DEBUT_DT ");
			query.append(" 								, AR.DEBUT_TTL                                ");
			query.append(" 						FROM	MSC M                                         ");
			query.append(" 								, ALBM AL                                     ");
			query.append(" 								, ARTST AR                                    ");
			query.append(" 						WHERE	M.ALBM_ID = AL.ALBM_ID                        ");
			query.append(" 						AND		AL.ARTST_ID = AR.ARTST_ID                     ");
			query.append("						AND		AL.ALBM_ID = ?                                ");
			query.append("						AND		AR.ARTST_ID = ?                               ");
			query.append("						ORDER	BY MSC_ID ASC                                 ");
			query.append(" 					) RST                                                     ");
			query.append(" 			WHERE	ROWNUM <= ?                                               ");
			query.append(" 		)                                                                     ");
			query.append(" WHERE	RNUM >= ?                                                         ");
			
			stmt = conn.prepareStatement(query.toString());

			stmt.setString(1, musicSearchVO.getAlbumId());
			stmt.setString(2, musicSearchVO.getArtistId());
			stmt.setInt(3,  musicSearchVO.getPager().getEndArticleNumber());
			stmt.setInt(4, musicSearchVO.getPager().getStartArticleNumber());
			
			rs = stmt.executeQuery();
			
			List<MusicVO> musicList = new ArrayList<MusicVO>();
			MusicVO music = null;
			
			while(rs.next()){
				music = new MusicVO();
				
				music.setMusicId(rs.getString("MSC_ID"));
				music.setAlbumId(rs.getString("M_ALBM_ID"));
				music.setTitle(rs.getString("TTL"));
				music.setLikeCount(rs.getInt("LK_CNT"));
				music.setMp3File(rs.getString("MP3_FL"));
				music.setMusician(rs.getString("MSCN"));
				music.setLyrics(rs.getString("LRCS"));
				music.setDirector(rs.getString("DRTR"));
				
				music.getAlbumVO().setAlbumId(rs.getString("AL_ALBM_ID"));
				music.getAlbumVO().setArtistId(rs.getString("AL_ARTST_ID"));
				music.getAlbumVO().setReleaseDate(rs.getString("RLS_DT"));
				music.getAlbumVO().setPublisher(rs.getString("PBLSHR"));
				music.getAlbumVO().setEntertainment(rs.getString("ENTMNT"));
				music.getAlbumVO().setGenre(rs.getString("GNR"));
				music.getAlbumVO().setPost(rs.getString("PST"));
				music.getAlbumVO().setAlbumName(rs.getString("ALBM_NM"));
				
				music.getAlbumVO().getArtistVO().setArtistId(rs.getString("AR_ARTST_ID"));
				music.getAlbumVO().getArtistVO().setMember(rs.getString("MBR"));
				music.getAlbumVO().getArtistVO().setDebutDate(rs.getString("DEBUT_DT"));
				music.getAlbumVO().getArtistVO().setDebutTitle(rs.getString("DEBUT_TTL"));
				
				musicList.add(music);
				
			}
			
			return musicList;
		
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			closeInstances(conn, stmt);
		}
	}

	@Override
	public MusicVO selectOneMusic(String musicId) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" 						SELECT	M.MSC_ID                                      ");
			query.append(" 								, M.ALBM_ID M_ALBM_ID                         ");
			query.append(" 								, M.TTL                                       ");
			query.append(" 								, M.LK_CNT                                    ");
			query.append(" 								, M.MP3_FL                                    ");
			query.append(" 								, M.MSCN                                      ");
			query.append(" 								, M.DRTR                                      ");
			query.append(" 								, M.LRCS                                      ");
			query.append(" 								, AL.ALBM_ID AL_ALBM_ID                       ");
			query.append(" 								, AL.ARTST_ID AL_ARTST_ID                     ");
			query.append(" 								, AL.RLS_DT                                   ");
			query.append(" 								, AL.PBLSHR                                   ");
			query.append(" 								, AL.ENTMNT                                   ");
			query.append(" 								, AL.GNR                                      ");
			query.append(" 								, AL.PST                                      ");
			query.append(" 								, AL.ALBM_NM                                  ");
			query.append(" 								, AR.ARTST_ID AR_ARTST_ID                     ");
			query.append(" 								, AR.MBR                                      ");
			query.append(" 								, TO_CHAR(AR.DEBUT_DT, 'YYYY-MM-DD') DEBUT_DT ");
			query.append(" 								, AR.DEBUT_TTL                                ");
			query.append(" 						FROM	MSC M                                         ");
			query.append(" 								, ALBM AL                                     ");
			query.append(" 								, ARTST AR                                    ");
			query.append(" 						WHERE	M.ALBM_ID = AL.ALBM_ID                        ");
			query.append(" 						AND		AL.ARTST_ID = AR.ARTST_ID                     ");
			// 조인시킨 뒤
			query.append(" 						AND		MSC_ID = ?                               ");
			// musicId에 맞는 녀석만 가져온다.
			query.append("						ORDER	BY MSC_ID ASC                                 ");
			
			stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, musicId);
			
			rs = stmt.executeQuery();
			
			MusicVO music = null;
			
			if(rs.next()){
				music = new MusicVO();
				
				music.setMusicId(rs.getString("MSC_ID"));
				music.setAlbumId(rs.getString("M_ALBM_ID"));
				music.setTitle(rs.getString("TTL"));
				music.setLikeCount(rs.getInt("LK_CNT"));
				music.setMp3File(rs.getString("MP3_FL"));
				music.setMusician(rs.getString("MSCN"));
				music.setLyrics(rs.getString("LRCS"));
				music.setDirector(rs.getString("DRTR"));
				
				music.getAlbumVO().setAlbumId(rs.getString("AL_ALBM_ID"));
				music.getAlbumVO().setArtistId(rs.getString("AL_ARTST_ID"));
				music.getAlbumVO().setReleaseDate(rs.getString("RLS_DT"));
				music.getAlbumVO().setPublisher(rs.getString("PBLSHR"));
				music.getAlbumVO().setEntertainment(rs.getString("ENTMNT"));
				music.getAlbumVO().setGenre(rs.getString("GNR"));
				music.getAlbumVO().setPost(rs.getString("PST"));
				music.getAlbumVO().setAlbumName(rs.getString("ALBM_NM"));
				music.getAlbumVO().getArtistVO().setArtistId(rs.getString("AR_ARTST_ID"));
				music.getAlbumVO().getArtistVO().setMember(rs.getString("MBR"));
				music.getAlbumVO().getArtistVO().setDebutDate(rs.getString("DEBUT_DT"));
				music.getAlbumVO().getArtistVO().setDebutTitle(rs.getString("DEBUT_TTL"));
			}
			
			return music;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			closeInstances(conn, stmt);
		}
	}

	@Override
	public int deleteOneMusic(String musicId) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" DELETE           ");
			query.append(" FROM		MSC       ");
			query.append(" WHERE	MSC_ID = ? ");
			
			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, musicId);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {	}
			closeInstances(conn, stmt);
		}
	}
	
	@Override
	public int updateOneMusicCount(String musicId, int likeCount) {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(ORACLE_URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" UPDATE	MSC "           );
			query.append(" SET		 "      );
			//query.append("			,TTL = ? "    );
			query.append("			 LK_CNT = ? + 1 "   );
			/*query.append("			, MP3_FL = ? "   );
			query.append("			, MSCN = ? "   );
			query.append("			, DRTR = ? "   );
			query.append("			, LRCS = ? "   );*/
			query.append(" WHERE	MSC_ID = ? ");
			
			stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, likeCount);
			stmt.setString(2, musicId);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}
