package com.melon.admin.music.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melon.admin.music.vo.MusicVO;

public class MusicDaoImpl implements MusicDao {
	
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "MELON";
	private final String PASSWORD = "melon";
			
	@Override
	public List<MusicVO> selectTopTenMusic() {
		loadOracleDriver();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuffer query = new StringBuffer();
			
			query.append(" SELECT	MSC_ID     ");
			query.append(" 		    , ALBM_ID  ");
			query.append(" 		    , TTL      ");
			query.append(" 		    , LK_CNT   ");
			query.append(" 		    , MP3_FL   ");
			query.append(" 		    , MSCN     ");
			query.append(" 		    , DRTR     ");
			query.append(" 		    , LRCS     ");
			query.append(" FROM		MSC        ");
			query.append(" WHERE	ROWNUM <= 10 ");
			query.append(" ORDER	BY	LK_CNT DESC ");
			
			stmt = conn.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			
			List<MusicVO> musicList = new ArrayList<MusicVO>();
			MusicVO music = null;
			
			while(rs.next()){
				music = new MusicVO();
				
				music.setMusicId(rs.getString("MSC_ID"));
				music.setAlbumId(rs.getString("ALBM_ID"));
				music.setTitle(rs.getString("TTL"));
				music.setLikeCount(rs.getInt("LK_CNT"));
				music.setMp3File(rs.getString("MP3_FL"));
				music.setMusician(rs.getString("MSCN"));
				music.setDirector(rs.getString("DRTR"));
				music.setLyrics(rs.getString("LRCS"));
				
				musicList.add(music);
			}
			
			return musicList;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			close(conn, stmt);
		}
	}

	private void close(Connection conn, PreparedStatement stmt) {
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {}
		}
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {}
		}
	}

	private void loadOracleDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}
