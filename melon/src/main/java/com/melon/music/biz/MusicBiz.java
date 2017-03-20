package com.melon.music.biz;

import java.util.List;

import com.melon.music.vo.MusicSearchVO;
import com.melon.music.vo.MusicVO;

public interface MusicBiz {
	
	public boolean addNewMusic(MusicVO music);
	
	public List<MusicVO> getAllMusic(MusicSearchVO musicSearchVO);

	public MusicVO getOneMusic(String musicId);
	
	public boolean deleteOneMusic(String musicId);
	
	public boolean renewOneMusicCount(String musicId, int likeCount);
	
}
