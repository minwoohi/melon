package com.melon.music.service;

import java.util.List;

import com.melon.music.biz.MusicBiz;
import com.melon.music.biz.MusicBizImpl;
import com.melon.music.vo.MusicSearchVO;
import com.melon.music.vo.MusicVO;
import com.melon.user.biz.UserBiz;
import com.melon.user.biz.UserBizImpl;
import com.melon.user.vo.UserVO;

public class MusicServiceImpl implements MusicService { // 흩어져 있는 업무를 하나로 뭉쳐주는 역할

	private MusicBiz musicBiz;
	private UserBiz userBiz;
	
	public MusicServiceImpl() {
		musicBiz = new MusicBizImpl();
		userBiz = new UserBizImpl();
	}
	
	@Override
	public boolean addNewMusic(MusicVO music) {
		return musicBiz.addNewMusic(music);
	}

	@Override
	public List<MusicVO> getAllMusic(MusicSearchVO musicSearchVO) {
		return musicBiz.getAllMusic(musicSearchVO);
	}

	@Override
	public MusicVO getOneMusic(String musicId, UserVO user) {
		
		MusicVO music = musicBiz.getOneMusic(musicId);
		if(music != null){
			userBiz.managePoint(user.getUserId(), -5); // 5포인트 차감 (DB)
			int userPoint = user.getUserPoint();
			user.setUserPoint(userPoint - 5); // 차감된 포인트 적용된 유저 반환( 세션에 적용시키기 위해 ) 
		}
		
		return music;
	}

	@Override
	public boolean deleteOneMusic(String musicId) {
		return musicBiz.deleteOneMusic(musicId);
	} 
	
	@Override
	public boolean updateMusicCount(String musicId, int likeCount) {
		return musicBiz.renewOneMusicCount(musicId, likeCount);
	}
	
}
