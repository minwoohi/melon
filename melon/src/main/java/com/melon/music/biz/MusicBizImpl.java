package com.melon.music.biz;

import java.util.ArrayList;
import java.util.List;

import com.melon.common.web.pager.Pager;
import com.melon.music.dao.MusicDao;
import com.melon.music.dao.MusicDaoImpl;
import com.melon.music.vo.MusicSearchVO;
import com.melon.music.vo.MusicVO;

public class MusicBizImpl implements MusicBiz {

	private MusicDao musicDao;
	
	public MusicBizImpl(){
		musicDao = new MusicDaoImpl();
	}
	
	@Override
	public boolean addNewMusic(MusicVO music) {
		return musicDao.insertNewMusic(music) > 0;
	}

	@Override
	public List<MusicVO> getAllMusic(MusicSearchVO musicSearchVO) {
		
		int musicCount = musicDao.selectAllMusicCount(musicSearchVO);
		
		Pager pager = musicSearchVO.getPager();
		pager.setTotalArticleCount(musicCount);
		
		return musicDao.selectAllMusic(musicSearchVO);
	}

	@Override
	public MusicVO getOneMusic(String musicId) {
		return musicDao.selectOneMusic(musicId);
	}

	@Override
	public boolean deleteOneMusic(String musicId) {
		return musicDao.deleteOneMusic(musicId) > 0;
	}
	
	@Override
	public boolean renewOneMusicCount(String musicId, int likeCount) {
		return musicDao.updateOneMusicCount(musicId, likeCount) > 0;
	}
	
}
