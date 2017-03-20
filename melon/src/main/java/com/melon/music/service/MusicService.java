package com.melon.music.service;

import java.util.List;

import com.melon.music.vo.MusicSearchVO;
import com.melon.music.vo.MusicVO;
import com.melon.user.vo.UserVO;

public interface MusicService {

	public boolean addNewMusic(MusicVO music);

	public List<MusicVO> getAllMusic(MusicSearchVO musicSearchVO);

	public MusicVO getOneMusic(String musicId, UserVO user);

	public boolean deleteOneMusic(String musicId);

	public boolean updateMusicCount(String musicId, int likeCount);

}
